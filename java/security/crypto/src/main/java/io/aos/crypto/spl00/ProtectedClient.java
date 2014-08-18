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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class ProtectedClient {
  public void sendAuthentication(String user, String password,
      OutputStream outStream) throws IOException, NoSuchAlgorithmException {
    DataOutputStream out = new DataOutputStream(outStream);
    long t1 = (new Date()).getTime();
    double q1 = Math.random();
    byte[] protected1 = Protection.makeDigest(user, password, t1, q1);

    out.writeUTF(user);
    out.writeLong(t1);
    out.writeDouble(q1);
    out.writeInt(protected1.length);
    out.write(protected1);
    out.flush();
  }

  public static void main(String... args) throws Exception {
    String host = args[0];
    int port = 7999;
    String user = "Jonathan";
    String password = "buendia";
    Socket s = new Socket(host, port);

    ProtectedClient client = new ProtectedClient();
    client.sendAuthentication(user, password, s.getOutputStream());

    s.close();
  }
}
