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
package io.aos.crypto.digest.d3;
import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class FileDecryptor {

  public static void main(String... args) {

    if (args.length != 3) {
      System.err.println("Usage: java FileDecryptor infile outfile password");
      return;
    }

    String infile = args[0];
    String outfile = args[1];
    String password = args[2];

    if (password.length() < 8 ) {
      System.err.println("Password must be at least eight characters long");
    }
 
    try {
      FileInputStream fin = new FileInputStream(infile);
      FileOutputStream fout = new FileOutputStream(outfile);

      // Create a key.
      byte[] desKeyData = password.getBytes();
      DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey desKey = keyFactory.generateSecret(desKeySpec);

      // Read the initialization vector.
      DataInputStream din = new DataInputStream(fin);
      int ivSize = din.readInt();
      byte[] iv = new byte[ivSize];
      din.readFully(iv);
      IvParameterSpec ivps = new IvParameterSpec(iv);

      // Use Data Encryption Standard.
      Cipher des = Cipher.getInstance("DES/CBC/PKCS5Padding");
      des.init(Cipher.DECRYPT_MODE, desKey, ivps);

      byte[] input = new byte[64];
      while (true) {
        int bytesRead = fin.read(input);
        if (bytesRead == -1) break;
        byte[] output = des.update(input, 0, bytesRead);
        if (output != null) fout.write(output);
      }
      
      byte[] output = des.doFinal();
      if (output != null) fout.write(output);
      fin.close();
      fout.flush();
      fout.close();
    }
    catch (GeneralSecurityException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex) {System.err.println(ex);}
  }
}
