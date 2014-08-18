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

// This program waits to receive datagrams sent to port 6010.  
// When it receives one, it displays the sending host and port, 
// and prints the contents of the datagram as a string.
public class BioUdpSimpleServer {
    private static final int port = 6010;

    public static void main(String... args) throws Exception {
        byte[] buffer = new byte[1024];
        String s;
        
        // Create a socket to listen on the port.
        DatagramSocket socket = new DatagramSocket(port);

        for (;;) {
            // Create a packet with an empty buffer to receive data
            // Bug workaround: create a new packet each time through the loop.
            // If we create the packet outside of this loop, then it seems to
            // loose track of its buffer size, and incoming packets are
            // truncated.
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            // Wait to receive a datagram
            socket.receive(packet);
            // Convert the contents to a string
            s = new String(buffer, 0, 0, packet.getLength());
            // And display them
            System.out.println("UDPReceive: received from " + packet.getAddress().getHostName() + ":"
                    + packet.getPort() + ": " + s);
        }
    }

}
