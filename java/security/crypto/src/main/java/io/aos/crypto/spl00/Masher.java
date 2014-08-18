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
package io.aos.crypto.spl00;
import java.io.*;
import java.security.*;

import sun.misc.*;

public class Masher {
  public static void main(String... args) throws Exception {
    // Check arguments.
    if (args.length != 1) {
      System.out.println("Usage: Masher filename");
      return;
    }

    // Obtain a message digest object.
    MessageDigest md = MessageDigest.getInstance("MD5");

    // Calculate the digest for the given file.
    FileInputStream in = new FileInputStream(args[0]);
    byte[] buffer = new byte[8192];
    int length;
    while ((length = in.read(buffer)) != -1)
      md.update(buffer, 0, length);
    byte[] raw = md.digest();

    // Print out the digest in base64.
    BASE64Encoder encoder = new BASE64Encoder();
    String base64 = encoder.encode(raw);
    System.out.println(base64);
  }
}
