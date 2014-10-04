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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// This class sends the specified text as a datagram to port 6010 of the 
// specified host.
public class BioUdpSimpleClient2b {
    
    static final int port = 6010;
    public static void main(String... args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java UDPSend <hostname> <message>");
            System.exit(0);
        }
        
        // Get the internet address of the specified host
        InetAddress address = InetAddress.getByName(args[0]);
        // Convert the message to an array of bytes
        int msglen = args[1].length();
        byte[] message = new byte[msglen];
        args[1].getBytes(0, msglen, message, 0);
        // Initilize the packet with data and address
        DatagramPacket packet = new DatagramPacket(message, msglen, 
                               address, port);
        // Create a socket, and send the packet through it.
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
    }
}
