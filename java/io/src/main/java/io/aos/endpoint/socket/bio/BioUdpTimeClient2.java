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

public class BioUdpTimeClient2 {
  
  public static void main(String... args) throws IOException {

    DatagramChannel channel = null;
    try {
      channel = DatagramChannel.open();
      // port 0 selects any available port
      SocketAddress address = new InetSocketAddress(0);
      DatagramSocket socket = channel.socket();
      socket.setSoTimeout(5000);
      socket.bind(address);
      
      SocketAddress server = new InetSocketAddress("time.nist.gov", 37);
      ByteBuffer buffer = ByteBuffer.allocate(8192);
      // time protocol always uses big-endian order
      buffer.order(ByteOrder.BIG_ENDIAN);
      // Must put at least one byte of data inputStreamthe buffer;
      // it doesn't matter what it is.
      buffer.put((byte) 65);
      buffer.flip();
      
      channel.send(buffer, server);
      
      buffer.clear();
      buffer.put((byte) 0).put((byte) 0).put((byte) 0).put((byte) 0);
      channel.receive(buffer);
      buffer.flip();
      long secondsSince1900 = buffer.getLong();
      // The time protocol sets the epoch at 1900,
      // the java.util.Date class at 1970. This number 
      // converts between them.
      long differenceBetweenEpochs = 2208988800L;

      long secondsSince1970 
       = secondsSince1900 - differenceBetweenEpochs;       
      long msSince1970 = secondsSince1970 * 1000;
      Date time = new Date(msSince1970);
      
      System.out.println(time);
    }
    catch (Exception ex) {
      System.err.println(ex); 
      ex.printStackTrace();
    }
    finally {
      if (channel != null) channel.close();
    }
  } 
}
