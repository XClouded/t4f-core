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
package io.aos.endpoint.socket.bio;
import java.net.*;
import java.io.*;


public class BioUdpEchoClient {

  public final static int DEFAULT_PORT = 7;

  public static void main(String... args) {

    String hostname = "localhost";
    int port = DEFAULT_PORT;

    if (args.length > 0) {
      hostname = args[0];
    }
    if (args.length > 1) {
      try {
        port = Integer.parseInt(args[1]);
      }
      catch (Exception e) {}
    }

    try {
      InetAddress ia = InetAddress.getByName(hostname);
      BioUdpSenderThread sender = new BioUdpSenderThread(ia, DEFAULT_PORT);
      sender.start();
      BioUdpReceiverThread receiver = new BioUdpReceiverThread(sender.getSocket());
      receiver.start();
    }
    catch (UnknownHostException e) {
      System.err.println(e);
    }
    catch (SocketException se) {
      System.err.println(se);
    }

  } 

}
