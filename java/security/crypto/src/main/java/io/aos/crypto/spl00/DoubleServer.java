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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DoubleServer {
  public boolean authenticate(InputStream inStream)
      throws IOException, NoSuchAlgorithmException {
    DataInputStream in = new DataInputStream(inStream);

    String user = in.readUTF();
    long t1 = in.readLong();
    double q1 = in.readDouble();
    long t2 = in.readLong();
    double q2 = in.readDouble();
    int length = in.readInt();
    byte[] protected2 = new byte[length];
    in.readFully(protected2);

    String password = lookupPassword(user);
    byte[] local1 = Protection.makeDigest(user, password, t1, q1);
    byte[] local2 = Protection.makeDigest(local1, t2, q2);
    return MessageDigest.isEqual(protected2, local2);
  }

  protected String lookupPassword(String user) { return "buendia"; }

  public static void main(String... args) throws Exception {
    int port = 7999;
    ServerSocket s = new ServerSocket(port);
    Socket client = s.accept();

    DoubleServer server = new DoubleServer();
    if (server.authenticate(client.getInputStream()))
      System.out.println("Client logged in.");
    else
      System.out.println("Client failed to log in.");

    s.close();
  }
}
