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

public class Protection {
  public static byte[] makeDigest(byte[] mush, long t2, double q2)
      throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA");
    md.update(mush);
    md.update(makeBytes(t2, q2));
    return md.digest();
  }
  public static byte[] makeDigest(String user, String password,
      long t1, double q1) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA");
    md.update(user.getBytes());
    md.update(password.getBytes());
    md.update(makeBytes(t1, q1));
    return md.digest();
  }

  public static byte[] makeBytes(long t, double q) {
    try {
      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      DataOutputStream dataOut = new DataOutputStream(byteOut);
      dataOut.writeLong(t);
      dataOut.writeDouble(q);
      return byteOut.toByteArray();
    }
    catch (IOException e) {
      return new byte[0];
    }
  }
}
