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
package io.aos.pipe.nio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Class which encapsulates System.in as a selectable channel.
 * 
 * Instantiate this class, call start() on it to run the background draining thread, then call
 * getStdinChannel() to get a SelectableChannel object which can be used with a
 * Selector object.
 * 
 */
public class NioChannelPipe2 {
    private Pipe pipe;
    private CopyThread copyThread;

    public NioChannelPipe2(InputStream inputStream) throws IOException {
        pipe = Pipe.open();
        copyThread = new CopyThread(inputStream, pipe.sink());
    }

    public NioChannelPipe2() throws IOException {
        this(System.in);
    }

    public void start() {
        copyThread.start();
    }

    public SelectableChannel getStdinChannel() throws IOException {
        SelectableChannel channel = pipe.source();
        channel.configureBlocking(false);
        return channel;
    }

    protected void finalize() {
        copyThread.shutdown();
    }

    public static class CopyThread extends Thread {
        boolean keepRunning = true;
        byte[] bytes = new byte[128];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        InputStream inputStream;
        WritableByteChannel outputStream;

        CopyThread(InputStream inputStream, WritableByteChannel outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            this.setDaemon(true);
        }

        public void shutdown() {
            keepRunning = false;
            this.interrupt();
        }

        public void run() {
            // this could be improved
            try {
                while (keepRunning) {
                    int count = inputStream.read(bytes);
                    if (count < 0) {
                        break;
                    }
                    buffer.clear().limit(count);
                    outputStream.write(buffer);
                }
                outputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

}
