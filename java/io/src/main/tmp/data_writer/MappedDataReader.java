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
// Copyright (c) Keith D Gregory, all rights reserved
package com.kdgregory.example.bytebuffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * Reads the file written by <code>data_writer.c</code>, mapping it into the
 * process' virtual address space. This is a trivial example, but contains all
 * the code you need to work with a non-trivial example.
 */
public class MappedDataReader {
    public static void main(String... argv) throws Exception {
        File file = new File("/tmp/example.dat");
        FileChannel channel = new RandomAccessFile(file, "r").getChannel();

        MappedByteBuffer buf = channel.map(MapMode.READ_ONLY, 0L, file.length());
        buf.order(ByteOrder.LITTLE_ENDIAN);

        System.out.println(String.format("data = %x", buf.getInt(0)));

        // this next call will throw unless you open the file with "rw"
        buf.putInt(0, 0x87654321);
    }

}
