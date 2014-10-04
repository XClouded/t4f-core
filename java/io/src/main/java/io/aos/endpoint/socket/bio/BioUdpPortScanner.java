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
import java.io.IOException;


public class BioUdpPortScanner {

  public static void main(String... args) {

    // first test to see whether or not we can bind to ports
    // below 1024
    boolean rootaccess = false;
    for (int port = 1; port < 1024; port += 50) {
      try {
        ServerSocket ss = new ServerSocket(port);
        // if successful
        rootaccess = true;
        ss.close();
        break;
      }
      catch (IOException ex) {
      }
    }
    
    int startport = 1;
    if (!rootaccess) startport = 1024;
    int stopport = 65535;
    
    System.out.println ("Checking UDP ports " + startport +
         " to " + stopport );

    for (int port = startport; port <= stopport; port++) {
      try {
        DatagramSocket ds = new DatagramSocket(port);
        ds.close();
      }
      catch (IOException ex) {
        System.out.println("UDP Port " + port + " is occupied.");
      }
    
    }

  }

}
