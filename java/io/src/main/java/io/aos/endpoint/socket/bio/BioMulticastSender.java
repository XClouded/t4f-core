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

public class BioMulticastSender {

  public static void main(String... args) {
  
    InetAddress ia = null;
    int port = 0;
    byte ttl = (byte) 1;
  
    // read the address from the command line
    try {
      ia = InetAddress.getByName(args[0]);
      port = Integer.parseInt(args[1]);
      if (args.length > 2) ttl = (byte) Integer.parseInt(args[2]);
    }
    catch (Exception e)  {
      System.err.println(e);
      System.err.println(
       "Usage: java MulticastSender multicast_address port ttl");
      System.exit(1);
    }
  
    byte[] data = "Here's some multicast data\r\n".getBytes();
    DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
  
    try {
      MulticastSocket ms = new MulticastSocket();
      for (int i = 1; i < 10; i++) {
        ms.send(dp, ttl);
      }
      ms.close();
    }
    catch (SocketException se) {
      System.err.println(se);
    }
    catch (IOException ie) {
      System.err.println(ie);
    }  
  
  }

}
