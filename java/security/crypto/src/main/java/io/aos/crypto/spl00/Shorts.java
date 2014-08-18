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
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class Shorts {
  public void md(byte[] inputData) throws Exception {
		// Define byte[] inputData first.
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(inputData);
		byte[] digest = md.digest();
  }
  
  public void fromMasher(String[] args) throws Exception{
    // Obtain a message digest object.
    MessageDigest md = MessageDigest.getInstance("MD5");

    // Calculate the digest for the given file.
    FileInputStream in = new FileInputStream(args[0]);
    byte[] buffer = new byte[8192];
    int length;
    while ((length = in.read(buffer)) != -1)
        md.update(buffer, 0, length);
    byte[] raw = md.digest();
  }
  
  public void withStream(String[] args) throws Exception {
    // Obtain a message digest object.
    MessageDigest md = MessageDigest.getInstance("MD5");

    // Calculate the digest for the given file.
    DigestInputStream in = new DigestInputStream(
        new FileInputStream(args[0]), md);
    byte[] buffer = new byte[8192];
    while (in.read(buffer) != -1)
      ;
    byte[] raw = md.digest();
  }
}
