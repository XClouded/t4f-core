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
package io.aos.endpoint.socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Test non-blocking DatagramChannel bug. Sends one datagram to the "time"
 * service on port 37 with a non-blocking DatagramChannel. Then loops for a
 * while receiving datagrams. The first to arrive is the expected reply.
 * Thereafter, the same address will be returned forever (or until another real
 * datagram arrives) but no data will be transferred into the buffer. The
 * channel behaves the same way if placed inputStreamnon-blocking before the initial
 * datagram is sent or after.
 * 
 */
public class NioDgNonBlockBug {
    public static void main(String... argv) throws Exception {
        String target = "time.nist.gov";

        if (argv.length > 0) {
            target = argv[0];
        }

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.flip(); // make it empty (RFC 868)

        DatagramChannel channel = DatagramChannel.open();
        ;
        channel.configureBlocking(false);

        channel.send(buffer, new InetSocketAddress(target, 37));

        System.out.println("Sent one Datagram to " + target);

        // would potentially loop forever
        for (int i = 0; i < 10; i++) {
            buffer.clear();
            buffer.putInt(0, 0);

            SocketAddress sa = channel.receive(buffer);

            if (sa == null) {
                System.out.println("no datagram ready, " + "sleeping one second");
                Thread.sleep(1000);
            } else {
                int value = buffer.getInt(0);
                buffer.flip();
                System.out.println("Received datagram from " + sa + ": " + buffer + ", value="
                        + Integer.toHexString(value));
            }
        }
    }
}
