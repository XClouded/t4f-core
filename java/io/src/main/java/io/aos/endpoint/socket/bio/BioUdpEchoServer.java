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

public class BioUdpEchoServer extends BioUdpServer {

  public final static int DEFAULT_PORT = 7;

  public BioUdpEchoServer() throws SocketException {
    this(DEFAULT_PORT); 
  }

  public BioUdpEchoServer(int port) throws SocketException {
    super(port); 
    ds.setReceiveBufferSize(32768);
  }

  public void respond(DatagramPacket incoming) {

    try {
      DatagramPacket outgoing = new DatagramPacket(incoming.getData(), 
       incoming.getLength(), incoming.getAddress(), incoming.getPort());
      ds.send(outgoing);
      System.out.println(incoming.getLength());
    }
    catch (IOException e) {
      System.err.println(e);
    }
    
  }

  public static void main(String... args) {
 
   try {
     if (args.length > 0) {
       int port = Integer.parseInt(args[0]);
       BioUdpEchoServer server = new BioUdpEchoServer(port);
       server.start();       
     }
     else {
       BioUdpEchoServer server = new BioUdpEchoServer();
       server.start();
     }
   }
   catch (SocketException e) {
     System.err.println(e);
   }
 
  }

}
