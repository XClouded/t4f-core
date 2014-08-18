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

public class BioUdpPoke {

  private int bufferSize; // inputStreambytes
  private DatagramSocket ds;
  private DatagramPacket outgoing;
   
  public BioUdpPoke(InetAddress host, int port, int bufferSize, 
   int timeout) throws SocketException {
    outgoing = new DatagramPacket(new byte[1], 1, host, port);
    this.bufferSize = bufferSize;
    ds = new DatagramSocket(0);
    ds.connect(host, port); // requires Java 2
    ds.setSoTimeout(timeout);
  }
  
  public BioUdpPoke(InetAddress host, int port, int bufferSize) 
   throws SocketException {
    this(host, port, bufferSize, 30000);
  }
  
  public BioUdpPoke(InetAddress host, int port) 
   throws SocketException {
    this(host, port, 8192, 30000);
  }
  
  public byte[] poke() throws IOException {
      
    byte[] response = null;
    try {
      ds.send(outgoing);
      DatagramPacket incoming 
       = new DatagramPacket(new byte[bufferSize], bufferSize);
      // next line blocks until the response is received
      ds.receive(incoming);
      int numBytes = incoming.getLength();
      response = new byte[numBytes];
      System.arraycopy(incoming.getData(), 0, response, 0, numBytes); 
    }
    catch (IOException e) {
        // response will be null
    } 

    // may return null 
    return response;      
  }

  public static void main(String... args) {

    InetAddress host;
    int port = 0;

    try {
      host = InetAddress.getByName(args[0]); 
      port = Integer.parseInt(args[1]);
      if (port < 1 || port > 65535) throw new Exception();
    }
    catch (Exception e) {
      System.out.println("Usage: java UDPPoke host port");
      return;
    }

    try {
      BioUdpPoke poker = new BioUdpPoke(host, port);
      byte[] response = poker.poke();
      if (response == null) {
          System.out.println("No response withinputStreamallotted time");
          return;
      }
      String result = "";
      try {
        result = new String(response, "ASCII");
      }
      catch (UnsupportedEncodingException e) {
          // try a different encoding
          result = new String(response, "8859_1");
      }
      System.out.println(result);
    }
    catch (Exception e) {
      System.err.println(e);    
      e.printStackTrace();
    }

  } 

}
