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
package io.aos.buffer.nio;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;

public class BufferAllocBenchmarkTest {
    
    @Test
    public void test() throws IOException {
    
        long[] nonDirect;
        long[] direct;
    
        System.out.println("Pre-running");
        doLoop(2, false);
        doLoop(2, true);
    
        System.gc();
        System.out.println("allocating non-direct buffers");
        nonDirect = doLoop(25, false);
    
        System.gc();
        System.out.println("allocating direct buffers");
        direct = doLoop(25, true);
    
        System.gc();
        System.out.println("done");
        printResults(nonDirect, direct);
    }

    public static long doAlloc(int size, int count, boolean direct) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            ByteBuffer buf;
            if (direct) {
                buf = ByteBuffer.allocateDirect(size * 1024);
            }
            else {
                buf = ByteBuffer.allocate(size * 1024);
            }
        }
        return (System.currentTimeMillis() - start);
    }

    public static long[] doLoop(int count, boolean direct) {
        long[] times = new long[count];
        for (int i = 0; i < times.length; i++) {
            times[i] = doAlloc(i * 8, 100, direct);
            // System.gc();
        }
        return (times);
    }

    public static void printResults(long[] nonDirect, long[] direct) {
        System.out.println("Size\talloc\tallocDirect");
        for (int i = 0; i < nonDirect.length; i++) {
            System.out.println("" + ((i + 1) * 8) + "\t" + nonDirect[i] + "\t" + direct[i]);
        }
    }

}
