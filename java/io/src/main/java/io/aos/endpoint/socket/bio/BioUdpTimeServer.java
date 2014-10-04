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

public class BioUdpTimeServer {

    public final static int DEFAULT_PORT = 37;

    public static void main(String... args) throws IOException {

        int port = 37;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[1]);
                if (port <= 0 || port > 65535)
                    port = DEFAULT_PORT;
                ;
            }
            catch (Exception ex) {
            }
        }

        ByteBuffer inputStream = ByteBuffer.allocate(8192);
        ByteBuffer outputStream = ByteBuffer.allocate(8);
        outputStream.order(ByteOrder.BIG_ENDIAN);
        SocketAddress address = new InetSocketAddress(port);
        DatagramChannel channel = DatagramChannel.open();
        DatagramSocket socket = channel.socket();
        socket.bind(address);
        System.err.println("bound to " + address);
        while (true) {
            try {
                inputStream.clear();
                SocketAddress client = channel.receive(inputStream);
                System.err.println(client);
                long secondsSince1900 = getTime();
                outputStream.clear();
                outputStream.putLong(secondsSince1900);
                outputStream.flip();
                // skip over the first four bytes to make this an unsigned int
                outputStream.position(4);
                channel.send(outputStream, client);
            }
            catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    private static long getTime() {
        long differenceBetweenEpochs = 2208988800L;
        Date now = new Date();
        long secondsSince1970 = now.getTime() / 1000;
        long secondsSince1900 = secondsSince1970 + differenceBetweenEpochs;
        return secondsSince1900;
    }
}
