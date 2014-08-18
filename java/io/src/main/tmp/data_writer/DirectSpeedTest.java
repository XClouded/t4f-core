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

import java.nio.ByteBuffer;
import java.util.Random;


/**
 *  Compares the performance of in-heap and out-of-heap buffers, using
 *  random reads and writes.
 */
public class DirectSpeedTest
{
    public static void main(String... argv)
    throws Exception
    {
        // source of randomness: replace the ctor param with a known
        // value if you want to ensure the same blocks are read/written
        // in the same order
        Random rnd = new Random(System.currentTimeMillis());

        // change "reps" to get a reasonable runtime on your system
        // change "writePct" to change the percentage of writes vs reads
        int size = 100 * 1024 * 1024;
        int reps = 100000000;

        // pick one of the following
        ByteBuffer buf = ByteBuffer.allocate(size);
        // ByteBuffer buf = ByteBuffer.allocateDirect(size);

        long start = System.currentTimeMillis();
        for (int ii = 0 ; ii < reps ; ii++)
        {
            int offset = rnd.nextInt(size - 4);
            buf.getInt(offset);
        }
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;

        System.out.println(
                reps + " reps = "
                + elapsed + " milliseconds");
    }
}
