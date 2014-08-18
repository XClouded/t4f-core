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
import java.net.*;
import java.io.*;
import java.security.*;

public class FileDigest {

  public static void main(String... args)
   throws IOException, NoSuchAlgorithmException {

    if (args.length != 2) {
      System.err.println("Usage: java FileDigest url filename");
      return;
    }

    URL u = new URL(args[0]);
    FileOutputStream out = new FileOutputStream(args[1]);
    copyFileWithDigest(u.openStream(), out);
    out.close();
  }

  public static void copyFileWithDigest(InputStream in, OutputStream out) 
   throws IOException, NoSuchAlgorithmException {
    
     MessageDigest sha = MessageDigest.getInstance("SHA-512");
     DigestOutputStream dout = new DigestOutputStream(out, sha);
     byte[] data = new byte[128];
     while (true) {
       int bytesRead = in.read(data);
       if (bytesRead < 0) break;
       dout.write(data, 0, bytesRead);
     }
     dout.flush();
     byte[] result = dout.getMessageDigest().digest();
     for (int i = 0; i < result.length; i++) {
       System.out.print(result[i] + " ");
     }
     System.out.println();
  }
}
