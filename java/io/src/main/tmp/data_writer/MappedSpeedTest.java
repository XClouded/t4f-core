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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.Random;


/**
 *  Compares the performance of a <code>RandomAccessFile</code> and a
 *  <code>MappedByteBuffer</code> by making random reads and writes in
 *  a large file.
 */
public class MappedSpeedTest
{
    public static void main(String... argv)
    throws Exception
    {
        // source of randomness: replace the ctor param with a known
        // value if you want to ensure the same blocks are read/written
        // in the same order
        Random rnd = new Random(System.currentTimeMillis());

        String filename = "/tmp/bigfile.dat";
        int size = 10 * 1024 * 1024;

        // change "reps" to get a reasonable runtime on your system
        // change "writePct" to change the percentage of writes vs reads
        final int reps = 100000;
        final int writePercent = 25;

        // this only needs to be run once, but you'll need to manually
        // delete the file
        // initializeFile(filename, size, rnd);
        // System.exit(0);

        // uncomment one of these lines
        // RAFTest tester = new RAFTest(filename, size);
        BufTest tester = new BufTest(filename, size);

        long start = System.currentTimeMillis();
        for (int ii = 0 ; ii < reps ; ii++)
        {
            tester.accessFile(rnd, writePercent);
        }
        tester.flush();
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;

        System.out.println(
                tester.getClass().getSimpleName()
                + ": time for " + reps + " accesses, with "
                + writePercent + "% writes = "
                + elapsed + " milliseconds ("
                + (elapsed / reps) + " ms per access)");
    }


    /**
     *  We need to start with a file that's full of data. Java allows you to
     *  create a file of N bytes simply by seeking to the Nth position. However,
     *  this produces a "sparse" file that doesn't actually exist on disk; reads
     *  from such a file are very fast but completely meaningless.
     */
    private static void initializeFile(String filename, int size, Random rnd)
    throws IOException
    {
        System.out.println("starting file init");
        FileOutputStream outputStream = new FileOutputStream(filename);
        try
        {
            byte[] data = new byte[65536];
            rnd.nextBytes(data);

            while (size > 0)
            {
                int bytesToWrite = Math.min(size, data.length);
                outputStream.write(data, 0, bytesToWrite);
                size -= bytesToWrite;
            }
        }
        finally
        {
            // note: production code would log/ignore any exception thrown
            // by this close  (usually with a "closeQuietly()" method
            outputStream.close();
        }
        System.out.println("finished file init");
    }


    /**
     *  Test code using a <code>RandomAccessFile</code>. Note that the file
     *  size is specified as an <code>int</code>, because that's the limit
     *  on byte buffers
     */
    private static class RAFTest
    {
        protected int _fileSize;
        protected RandomAccessFile _raf;

        public RAFTest(String filename, int size)
        throws IOException
        {
            _fileSize = size;
            _raf = new RandomAccessFile(filename, "rw");
        }

        public void flush()
        throws IOException
        {
            _raf.getFD().sync();
        }

        public void accessFile(Random rnd, int writePercent)
        throws IOException
        {
            int offset = calculateOffset(rnd);
            _raf.seek(offset);

            if (rnd.nextInt(100) < writePercent)
                _raf.writeInt(rnd.nextInt());
            else
                _raf.readInt();
        }

        protected int calculateOffset(Random rnd)
        {
            // common code: ensure that we have space to write an Integer
            return rnd.nextInt(_fileSize - 4);
        }
    }


    /**
     *  Test code using a <code>MappedByteBuffer</code>.
     */
    private static class BufTest
    extends RAFTest
    {
        MappedByteBuffer _buf;

        public BufTest(String filename, int size)
        throws IOException
        {
            super(filename, size);
            _buf = _raf.getChannel().map(MapMode.READ_WRITE, 0L, _fileSize);
        }

        @Override
        public void accessFile(Random rnd, int writePercent)
        throws IOException
        {
            int offset = calculateOffset(rnd);

            if (rnd.nextInt(100) < writePercent)
                _buf.putInt(offset, writePercent);
            else
                _buf.getInt(offset);
        }
    }
}
