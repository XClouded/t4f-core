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
package io.aos.concurrent.spl0.cs12.example4;


import io.aos.concurrent.spl0.cs12.*;

import java.util.concurrent.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class CalcServer extends TCPNIOServer {

    static ThreadPoolExecutor pool;

    class FibClass implements Runnable {
        long n;
        SocketChannel clientChannel;
        ByteBuffer buffer = ByteBuffer.allocateDirect(8);

        FibClass(long n, SocketChannel sc) {
            this.n = n;
            clientChannel = sc;
        }

        private long fib(long n) {
            if (n == 0)
                return 0L;
            if (n == 1)
                return 1L;
            return fib(n - 1) + fib(n - 2);
        }

        public void run() {
            try {
                long answer = fib(n);
                buffer.putLong(answer);
                buffer.flip();
                clientChannel.write(buffer);
                if (buffer.remaining() > 0) {
                    Selector s = Selector.open();
                    clientChannel.register(s, SelectionKey.OP_WRITE);
                    while (buffer.remaining() > 0) {
                        s.select();
                        clientChannel.write(buffer);
                    }
                    s.close();
                }
            } catch (IOException ioe) {
                System.out.println("Client error " + ioe);
            }
        }
    }

    protected void handleClient(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(8);
        sc.read(buffer);
        buffer.flip();
        long n = buffer.getLong();
        FibClass fc = new FibClass(n, sc);
        pool.execute(fc);
    }

    protected void registeredClient(SocketChannel sc) {
    }

    public static void main(String... args) throws Exception {
        CalcServer cs = new CalcServer();
        cs.port = Integer.parseInt(args[0]);
        int tpSize = Integer.parseInt(args[1]);
        pool = new ThreadPoolExecutor(
                        tpSize, tpSize, 50000L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
        cs.run();
        System.out.println("Calc server waiting for requests...");
    }
}
