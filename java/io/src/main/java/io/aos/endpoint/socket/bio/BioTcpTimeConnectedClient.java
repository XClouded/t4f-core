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

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Date;

public class BioTcpTimeConnectedClient {
  
  public static void main(String... args) throws IOException {

    DatagramChannel channel = DatagramChannel.open();
    SocketAddress address = new InetSocketAddress(0);
    DatagramSocket socket = channel.socket();
    socket.bind(address);
    
    SocketAddress server = new InetSocketAddress("time-a.nist.gov", 37);
    channel.connect(server);
    
    ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.order(ByteOrder.BIG_ENDIAN);
    // send a byte of data to the server
    buffer.put((byte) 0);
    buffer.flip();
    channel.write(buffer);
    
    // get the buffer ready to receive data
    buffer.clear();
    // fill the first four bytes with zeros
    buffer.putInt(0);
    channel.read(buffer);
    buffer.flip();
    
    // convert seconds since 1900 to a java.util.Date
    long secondsSince1900 = buffer.getLong();
    long differenceBetweenEpochs = 2208988800L;
    long secondsSince1970 
     = secondsSince1900 - differenceBetweenEpochs;       
    long msSince1970 = secondsSince1970 * 1000;
    Date time = new Date(msSince1970);
    
    System.out.println(time);
  } 
}
