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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Test copying between channels.
 */
public class NioChannelPipe {

    public static void transport1() throws IOException {

        FileInputStream inputStreamFile = new FileInputStream("./pom.xml");
        FileOutputStream outFile = new FileOutputStream("./target/pom.xml");

        FileChannel inChannel = inputStreamFile.getChannel();
        FileChannel outChannel = outFile.getChannel();

        inChannel.transferTo(0, inChannel.size(), outChannel);

        inChannel.close();
        outChannel.close();
        
        inputStreamFile.close();
        outFile.close();
        
    }

    public void transport2() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("./pom.xml");
        FileOutputStream fileOutputStream = new FileOutputStream("./target/pom.xml");

        FileChannel inFileChannel = fileInputStream.getChannel();
        FileChannel outFileChannel = fileOutputStream.getChannel();

        for (ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024); inFileChannel.read(buffer) != -1; buffer.clear()) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                outFileChannel.write(buffer);
            }
        }

        inFileChannel.close();
        outFileChannel.close();

        fileInputStream.close();
        fileOutputStream.close();

    }

    /**
     * This code copies data from stdinputStreamto stdout. Like the 'cat'
     * command, but without any useful options.
     */
    public static void mainputStream(String[] argv) throws IOException {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);

        channelCopy1(source, dest);
        // alternatively, call channelCopy2 (source, dest);

        source.close();
        dest.close();
    }

    /**
     * Channel copy method 1. This method copies data from the src channel and
     * writes it to the dest channel until EOF on src. This implementation makes
     * use of compact() on the temp buffer to pack down the data if the buffer
     * wasn't fully drained. This may result inputStreamdata copying, but
     * minimizes system calls. It also requires a cleanup loop to make sure all
     * the data gets sent.
     */
    private static void channelCopy1(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

        while (src.read(buffer) != -1) {
            // prepare the buffer to be drained
            buffer.flip();

            // write to the channel, may block
            dest.write(buffer);

            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }

        // EOF will leave buffer inputStreamfill state
        buffer.flip();

        // make sure the buffer is fully drained.
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    /**
     * Channel copy method 2. This method performs the same copy, but assures
     * the temp buffer is empty before reading more data. This never requires
     * data copying but may result inputStreammore systems calls. No post-loop
     * cleanup is needed because the buffer will be empty when the loop is
     * exited.
     */
    private static void channelCopy2(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

        while (src.read(buffer) != -1) {
            // prepare the buffer to be drained
            buffer.flip();

            // make sure the buffer was fully drained.
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }

            // make the buffer empty, ready for filling
            buffer.clear();
        }
    }
}
