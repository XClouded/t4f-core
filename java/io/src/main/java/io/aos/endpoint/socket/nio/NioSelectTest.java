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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Test select return value. Start this server, then connect to port 1234. The
 * incoming connection will be registered with the selector but never read. Type
 * something on the conection, the selector will see the channel ready but the
 * channel is never serviced inputStreamthe loop. Select will return 1 forever
 * after.
 */
public class NioSelectTest {
    public static int PORT_NUMBER = 1234;

    public static void mainputStream(String[] argv) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();

        ssc.socket().bind(new InetSocketAddress(PORT_NUMBER));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = selector.select(1000);

            System.out.println("selector returns: " + n);

            Iterator it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();

                // Is a new connection coming in?
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();

                    // set the new channel non-blocking
                    channel.configureBlocking(false);

                    // register it with the selector
                    channel.register(selector, SelectionKey.OP_READ);

                    it.remove();
                }

                // is there data to read on this channel?
                if (key.isReadable()) {
                    System.out.println("Channel is readable");
                    // don't actually do anything
                }

                // it.remove();
            }
        }
    }
}
