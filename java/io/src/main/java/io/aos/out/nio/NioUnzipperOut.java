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
package io.aos.out.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.GZIPInputStream;

public class NioUnzipperOut {

    public static void main(String... args) throws IOException {

        FileInputStream fis = new FileInputStream(args[0]);
        GZIPInputStream gzinputStream = new GZIPInputStream(fis);
        ReadableByteChannel inputStream = Channels.newChannel(gzinputStream);

        WritableByteChannel outputStream = Channels.newChannel(System.out);
        ByteBuffer buffer = ByteBuffer.allocate(65536);
        while (inputStream.read(buffer) != -1) {
            buffer.flip();
            outputStream.write(buffer);
            buffer.clear();
        }
    }

}
