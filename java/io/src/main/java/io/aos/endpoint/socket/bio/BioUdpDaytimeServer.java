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
import java.util.*;


public class BioUdpDaytimeServer extends BioUdpServer {

  public final static int DEFAULT_PORT = 13;

  public BioUdpDaytimeServer() throws SocketException {
    super(DEFAULT_PORT); 
  }

  public void respond(DatagramPacket packet) {

    try {
      Date now = new Date();
      String response = now.toString() + "\r\n";
      byte[] data = response.getBytes("ASCII");
      DatagramPacket outgoing = new DatagramPacket(data, 
       data.length, packet.getAddress(), packet.getPort());
      ds.send(outgoing);
    }
    catch (IOException e) {
      System.err.println(e);
    }
    
  }

  public static void main(String... args) {
 
   try {
     BioUdpDaytimeServer server = new BioUdpDaytimeServer();
     server.start();
   }
   catch (SocketException e) {
     System.err.println(e);
   }
 
  }

}
