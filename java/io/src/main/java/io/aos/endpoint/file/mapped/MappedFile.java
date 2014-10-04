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
package io.aos.endpoint.file.mapped;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Test behavior of Memory mapped buffer types. Create a file, write some data
 * to it, then create three different types of mappings to it. Observe the
 * effects of changes through the buffer APIs and updating the file directly.
 * The data spans page boundaries to illustrate the page-oriented nature of
 * Copy-On-Write mappings.
 */
public class MappedFile {
    
    public void map0() throws Exception {
        
        RandomAccessFile raf = new RandomAccessFile("pom.xml", "r");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

        buffer.clear();
        buffer.flip();

        System.out.println("hasArray=" + buffer.hasArray());
        System.out.println(buffer.toString());

        System.out.flush();
        
        raf.close();

    }

    public void map() throws Exception {

        // create a temp file and get a channel connected to it
        File tempFile = File.createTempFile("mmaptest", null);
        RandomAccessFile file = new RandomAccessFile(tempFile, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer temp = ByteBuffer.allocate(100);

        // put something inputStreamthe file, starting at location 0
        temp.put("This is the file content".getBytes());
        temp.flip();
        channel.write(temp, 0);

        // Put something else inputStreamthe file, starting at location 8192
        // 8192 is 8k, almost certainly a different memory/FS page.
        // This may cause a file hole, depending on the
        // filesystem page size.
        temp.clear();
        temp.put("This is more file content".getBytes());
        temp.flip();
        channel.write(temp, 8192);

        // create three types of mappings to the same file
        MappedByteBuffer ro = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        MappedByteBuffer rw = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        MappedByteBuffer cow = channel.map(FileChannel.MapMode.PRIVATE, 0, channel.size());

        // The buffer states before any modifications
        System.out.println("Begin");
        showBuffers(ro, rw, cow);

        // modify the Copy-On-Write buffer
        cow.position(8);
        cow.put("COW".getBytes());

        System.out.println("Change to COW buffer");
        showBuffers(ro, rw, cow);

        // Modify the Read/Write buffer
        rw.position(9);
        rw.put(" R/W ".getBytes());
        rw.position(8194);
        rw.put(" R/W ".getBytes());
        rw.force();

        System.out.println("Change to R/W buffer");
        showBuffers(ro, rw, cow);

        // Write to the file through the channel, hit both pages
        temp.clear();
        temp.put("Channel write ".getBytes());
        temp.flip();
        channel.write(temp, 0);
        temp.rewind();
        channel.write(temp, 8202);

        System.out.println("Write on channel");
        showBuffers(ro, rw, cow);

        // modify the Copy-On-Write buffer again
        cow.position(8207);
        cow.put(" COW2 ".getBytes());

        System.out.println("Second change to COW buffer");
        showBuffers(ro, rw, cow);

        // Modify the Read/Write buffer
        rw.position(0);
        rw.put(" R/W2 ".getBytes());
        rw.position(8210);
        rw.put(" R/W2 ".getBytes());
        rw.force();

        System.out.println("Second change to R/W buffer");
        showBuffers(ro, rw, cow);

        // cleanup
        channel.close();
        file.close();
        tempFile.delete();
    }

    // show the current content of the three buffers
    public static void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer cow) throws Exception {
        dumpBuffer("R/O", ro);
        dumpBuffer("R/W", rw);
        dumpBuffer("COW", cow);
        System.out.println("");
    }

    // dump buffer content, counting and skipping nulls
    public static void dumpBuffer(String prefix, ByteBuffer buffer) throws Exception {
        System.out.print(prefix + ": '");

        int nulls = 0;
        int limit = buffer.limit();

        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get(i);

            if (c == '\u0000') {
                nulls++;
                continue;
            }

            if (nulls != 0) {
                System.out.print("|[" + nulls + " nulls]|");
                nulls = 0;
            }

            System.out.print(c);
        }

        System.out.println("'");
    }
}
