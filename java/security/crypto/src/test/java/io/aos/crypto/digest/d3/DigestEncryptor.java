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

public class DigestEncryptor {

  public static void main(String... args) 
   throws IOException, GeneralSecurityException {

    if (args.length != 2) {
      System.err.println("Usage: java DigestEncryptor filename password");
      return;
    }

    String filename = args[0];
    String password = args[1];

    if (password.length() < 8 ) {
      System.err.println("Password must be at least eight characters long");
    }
    
    FileInputStream fin = new FileInputStream(filename);
    FileOutputStream fout = new FileOutputStream(filename +".des");
    FileOutputStream digest = new FileOutputStream(filename + ".des.digest");

    // Create the key.
    byte[] desKeyData = password.getBytes();
    DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey desKey = keyFactory.generateSecret(desKeySpec);

    // Use Data Encryption Standard.
    Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");
    des.init(Cipher.ENCRYPT_MODE, desKey);
    CipherInputStream cin = new CipherInputStream(fin, des);

    // Use SHA digest algorithm.
    MessageDigest sha = MessageDigest.getInstance("SHA");
    DigestInputStream din = new DigestInputStream(cin, sha);

    byte[] input = new byte[64];
    while (true) {
      int bytesRead = din.read(input);
      if (bytesRead == -1) break;
      fout.write(input, 0, bytesRead);
    }
      
    digest.write(sha.digest());
    digest.close();
    din.close();
    fout.flush();
    fout.close();
  }
}
