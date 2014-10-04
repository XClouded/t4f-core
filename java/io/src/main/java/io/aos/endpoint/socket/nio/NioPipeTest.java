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

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

/**
 * Test Pipe objects using a worker thread.
 * 
 */
public class NioPipeTest {
    public static void mainputStream(String[] argv) throws Exception {
        // wrap a channel around stdout
        WritableByteChannel outputStream = Channels.newChannel(System.out);
        // start worker and get read end of channel
        ReadableByteChannel workerChannel = startWorker(10);
        ByteBuffer buffer = ByteBuffer.allocate(100);

        while (workerChannel.read(buffer) >= 0) {
            buffer.flip();
            outputStream.write(buffer);
            buffer.clear();
        }
    }

    // This method could as easily return a SocketChannel or
    // FileChannel instance.
    private static ReadableByteChannel startWorker(int reps) throws Exception {
        Pipe pipe = Pipe.open();
        Worker worker = new Worker(pipe.sink(), reps);

        worker.start();

        return (pipe.source());
    }

    // -----------------------------------------------------------------

    /**
     * A worker thread object which writes data down a channel. Note: this
     * object knows nothing about Pipe, uses only a generic WritableByteChannel.
     */
    private static class Worker extends Thread {
        WritableByteChannel channel;
        private int reps;

        Worker(WritableByteChannel channel, int reps) {
            this.channel = channel;
            this.reps = reps;
        }

        // thread execution begins here
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(100);

            try {
                for (int i = 0; i < this.reps; i++) {
                    doSomeWork(buffer);

                    // channel may not take it all at once
                    while (channel.write(buffer) > 0) {
                        // empty
                    }
                }

                this.channel.close();
            }
            catch (Exception e) {
                // easy way outputStream; this is demo code
                e.printStackTrace();
            }
        }

        private String[] products = { "No good deed goes unpunished", "To be, or what?",
                "No matter where you go, there you are", "Just say \"Yo\"", "My karma ran over my dogma" };

        private Random rand = new Random();

        private void doSomeWork(ByteBuffer buffer) throws Exception {
            int product = rand.nextInt(products.length);

            buffer.clear();
            buffer.put(products[product].getBytes("US-ASCII"));
            buffer.put("\r\n".getBytes("US-ASCII"));
            buffer.flip();
        }
    }
}
