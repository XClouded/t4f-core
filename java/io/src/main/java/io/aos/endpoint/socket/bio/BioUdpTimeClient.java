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
import java.util.*;

public class BioUdpTimeClient {
  
  public final static int DEFAULT_PORT = 37;
  public final static String DEFAULT_HOST = "tock.usno.navy.mil";
  
  public static void main(String... args) {
    
    InetAddress host;
    int port = DEFAULT_PORT;

    try {
      if (args.length > 0) {
        host = InetAddress.getByName(args[0]);
      }
      else {
        host = InetAddress.getByName(DEFAULT_HOST);
      } 
    }
    catch (Exception e) {
      System.out.println("Usage: java UDPTimeClient host port");
      return;
    }

    if (args.length > 1) {
      try {
        port = Integer.parseInt(args[1]);
        if (port <= 0 || port > 65535) port = DEFAULT_PORT;;
      }
      catch (Exception e){
      }
    }

    try {
      BioUdpPoke poker = new BioUdpPoke(host, port);
      byte[] response = poker.poke();
      if (response == null) {
          System.out.println("No response withinputStreamallotted time");
          return;
      }
      else if (response.length != 4) {
          System.out.println("Unrecognized response format");
          return;         
      }
   
      
      // The time protocol sets the epoch at 1900,
      // the java Date class at 1970. This number 
      // converts between them.
    
      long differenceBetweenEpochs = 2208988800L;

      long secondsSince1900 = 0;
      for (int i = 0; i < 4; i++) {
        secondsSince1900 = (secondsSince1900 << 8) | (response[i] & 0x000000FF);
      }

      long secondsSince1970 
       = secondsSince1900 - differenceBetweenEpochs;       
      long msSince1970 = secondsSince1970 * 1000;
      Date time = new Date(msSince1970);
      
      System.out.println(time);
    }
    catch (Exception e) {
      System.err.println(e);    
      e.printStackTrace();
    }     
    
  } 
  
}
