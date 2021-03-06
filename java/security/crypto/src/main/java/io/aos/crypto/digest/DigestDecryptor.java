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

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DigestDecryptor {

    public static void main(String... args) throws IOException, GeneralSecurityException {

        if (args.length != 3) {
            System.err.println("Usage: java DigestDecryptor infile outfile password");
            return;
        }

        String infile = args[0];
        String outfile = args[1];
        String password = args[2];

        if (password.length() < 8) {
            System.err.println("Password must be at least eight characters long");
        }

        FileInputStream fis = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);

        // Get the digest.
        FileInputStream digestIn = new FileInputStream(infile + ".digest");
        DataInputStream dataIn = new DataInputStream(digestIn);
        // SHA digests are always 20 bytes long .
        byte[] oldDigest = new byte[20];
        dataIn.readFully(oldDigest);
        dataIn.close();

        // Create a key.
        byte[] desKeyData = password.getBytes();
        DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = keyFactory.generateSecret(desKeySpec);

        // Use Data Encryption Standard.
        Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");
        des.init(Cipher.DECRYPT_MODE, desKey);
        CipherOutputStream cout = new CipherOutputStream(fout, des);

        // Use SHA digest algorithm.
        MessageDigest sha = MessageDigest.getInstance("SHA");
        DigestInputStream dinputStream = new DigestInputStream(fis, sha);

        byte[] input = new byte[64];
        while (true) {
            int bytesRead = dinputStream.read(input);
            if (bytesRead == -1)
                break;
            cout.write(input, 0, bytesRead);
        }

        byte[] newDigest = sha.digest();
        if (!MessageDigest.isEqual(newDigest, oldDigest)) {
            System.out.println("Input file appears to be corrupt!");
        }

        dinputStream.close();
        cout.flush();
        cout.close();
    }
}
