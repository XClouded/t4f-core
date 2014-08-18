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
package io.aos.endpoint.file.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * Test file pointer manipulation between FileChannel and RandomAccessFile
 * objects.
 */
public class NioFile {

    public static void write() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(".target/text.txt", "r");
        FileChannel fileChannel = randomAccessFile.getChannel();
        randomAccessFile.seek(1000);
        // This will print "1000"
        System.out.println("file pos: " + fileChannel.position());
        randomAccessFile.seek(500);
        // This will print "500"
        System.out.println("file pos: " + fileChannel.position());
        fileChannel.position(200);
        // This will print "200"
        System.out.println("file pos: " + randomAccessFile.getFilePointer());
        randomAccessFile.close();
    }

    public static void read() throws IOException {
        FileChannel fc = new FileInputStream(new File("temp.tmp")).getChannel();
        IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
        while (ib.hasRemaining()) {
            ib.get();
        }
        fc.close();
    }

    public static void writeRead() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("target/test.txt", "rw");
        raf.writeUTF("Hello World");
        raf.close();
        raf = new RandomAccessFile("target/test.txt", "rw");
        raf.seek(0);
        System.out.println(raf.readUTF());
        raf.seek(0);
        System.out.println(raf.read());
        raf.seek(4);
        System.out.println(raf.read());
        raf.close();
    }

}
