/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package io.aos.crypto.digest;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class FileEncryptor {

    public static void main(String... args) {

        if (args.length != 2) {
            System.err.println("Usage: java FileEncryptor filename password");
            return;
        }

        String filename = args[0];
        String password = args[1];

        if (password.length() < 8) {
            System.err.println("Password must be at least eight characters long");
        }

        try {
            FileInputStream fis= new FileInputStream(args[0]);
            FileOutputStream fout = new FileOutputStream(args[0] + ".des");

            // Create a key.
            byte[] desKeyData = password.getBytes();
            DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey desKey = keyFactory.generateSecret(desKeySpec);

            // Use Data Encryption Standard.
            Cipher des = Cipher.getInstance("DES/CBC/PKCS5Padding");
            des.init(Cipher.ENCRYPT_MODE, desKey);

            // Write the initialization vector onto the output.
            byte[] iv = des.getIV();
            DataOutputStream dout = new DataOutputStream(fout);
            dout.writeInt(iv.length);
            dout.write(iv);

            byte[] input = new byte[64];
            while (true) {
                int bytesRead = fis.read(input);
                if (bytesRead == -1)
                    break;
                byte[] output = des.update(input, 0, bytesRead);
                if (output != null)
                    dout.write(output);
            }

            byte[] output = des.doFinal();
            if (output != null)
                dout.write(output);
            fis.close();
            dout.flush();
            dout.close();

        } catch (InvalidKeySpecException ex) {
            System.err.println(ex);
        } catch (InvalidKeyException ex) {
            System.err.println(ex);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        } catch (NoSuchPaddingException ex) {
            System.err.println(ex);
        } catch (BadPaddingException ex) {
            System.err.println(ex);
        } catch (IllegalBlockSizeException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
