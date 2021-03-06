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

public class BioUdpFastLoggingDiscardServer extends BioUdpServer {

  public final static int DEFAULT_PORT = 9999;
  
  public BioUdpFastLoggingDiscardServer() throws SocketException {
    super(DEFAULT_PORT);
  }
  
  public void respond(DatagramPacket packet) {
    
    byte[] data = new byte[packet.getLength()];
    System.arraycopy(packet.getData(), 0, data, 0, packet.getLength());
    try {
      String s = new String(data, "ASCII");
      System.out.println(packet.getAddress() + " at port " 
       + packet.getPort() + " says " + s);
    }
    catch (java.io.UnsupportedEncodingException e) {
    }
  
  }

  public static void main(String... args) {
 
   try {
     BioUdpFastLoggingDiscardServer server = new BioUdpFastLoggingDiscardServer();
     server.start();
   }
   catch (SocketException e) {
     System.err.println(e);
   }
 
  }

}
