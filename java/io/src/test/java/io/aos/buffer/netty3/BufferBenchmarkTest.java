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
package io.aos.buffer.netty3;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.Random;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.internal.LinkedTransferQueue;
import org.junit.Test;

public class BufferBenchmarkTest {
    private static final int CAPACITY = 10485760;
    private static final int ITERATION = 500;

    private static final int POOLED_CAPACITY = 8192;

    private final Random rnd = new Random();

    @Test
    public void simpleAccess() throws Exception {
        ByteBuffer b1 = ByteBuffer.allocate(CAPACITY);
        ByteBuffer b3 = ByteBuffer.allocateDirect(CAPACITY);
        ChannelBuffer b4 = ChannelBuffers.buffer(CAPACITY);
        ChannelBuffer b41 = ChannelBuffers.buffer(CAPACITY + 16).slice(8, CAPACITY);
        ChannelBuffer b42 = ChannelBuffers.buffer(CAPACITY + 8).slice(0, CAPACITY);
        ChannelBuffer b5 = ChannelBuffers.dynamicBuffer(CAPACITY);

        for (;;) {
            simpleAccess0("        NIO Heap Buffer", b1);
            simpleAccess0("      NIO Direct Buffer", b3);
            simpleAccess0("      Netty Heap Buffer", b4);
            simpleAccess0("     Netty SHeap Buffer", b41);
            simpleAccess0("     Netty THeap Buffer", b42);
            simpleAccess0("   Netty Dynamic Buffer", b4);
        }
    }

    @Test
    public void concurrentAllocation() {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 16384; i++) {
                    run0(new byte[POOLED_CAPACITY]);
                }
            }
    
            private void run0(byte[] data) {
                for (int i = 0; i < data.length; i++) {
                    data[i] = (byte) i;
                }
            }
        };
    
        final Queue<byte[]>[] pool = new Queue[8];
        for (int i = 0; i < pool.length; i++) {
            pool[i] = new LinkedTransferQueue<byte[]>();
        }
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 16384; i++) {
                    byte[] data = acquire();
                    run0(data);
                    release(data);
                }
            }
    
            private void run0(byte[] data) {
                for (int i = 0; i < data.length; i++) {
                    data[i] = (byte) i;
                }
            }
    
            private byte[] acquire() {
                byte[] data = pool[Thread.currentThread().hashCode() & (pool.length - 1)].poll();
                if (data == null) {
                    data = new byte[POOLED_CAPACITY];
                }
                return data;
            }
    
            private void release(byte[] data) {
                pool[Thread.currentThread().hashCode() & (pool.length - 1)].add(data);
            }
        };
    
        for (;;) {
            concurrentAllocation0(" JVM", task1);
            concurrentAllocation0("Pool", task2);
            for (int i = 0; i < pool.length; i++) {
                System.out.print(pool[i].size());
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    private void simpleAccess0(String name, ChannelBuffer buf) throws Exception {
        int checksum;
        int capacity = buf.capacity();

        for (int i = 0; i < capacity; i++) {
            buf.setByte(i, rnd.nextInt(256));
        }

        long start = System.nanoTime();
        checksum = simpleAccess1(buf);
        long end = System.nanoTime();

        System.out.format("%s: %d ms, checksum: %d%n", name, (end - start) / 1000000, checksum & 0xff);
    }

    private int simpleAccess1(ChannelBuffer buf) {
        int checksum = 0;
        int capacity = buf.capacity();
        for (int i = 0; i < ITERATION; i++) {
            for (int j = 0; j < capacity;) {
                checksum += buf.getByte(j);
                j += 1;
                checksum += buf.getByte(j);
                j += 1;
                checksum += buf.getByte(j);
                j += 1;
                checksum += buf.getByte(j);
                j += 1;
            }
        }
        return checksum;
    }

    private void simpleAccess0(String name, ByteBuffer buf) throws Exception {
        int checksum = 0;
        int capacity = buf.capacity();

        for (int i = 0; i < capacity; i++) {
            buf.put(i, (byte) rnd.nextInt(256));
        }

        long start = System.nanoTime();
        checksum = simpleAccess1(buf);
        long end = System.nanoTime();

        System.out.format("%s: %d ms, checksum: %d%n", name, (end - start) / 1000000, checksum & 0xff);
    }

    private int simpleAccess1(ByteBuffer buf) {
        int checksum = 0;
        int capacity = buf.capacity();
        for (int i = 0; i < ITERATION; i++) {
            for (int j = 0; j < capacity;) {
                checksum += buf.get(j);
                j += 1;
                checksum += buf.get(j);
                j += 1;
                checksum += buf.get(j);
                j += 1;
                checksum += buf.get(j);
                j += 1;
            }
        }
        return checksum;
    }

    private void concurrentAllocation0(String name, Runnable task) {
        Thread[] threads = new Thread[256];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task);
        }

        long start = System.nanoTime();
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        loop: for (;;) {
            for (int i = 0; i < threads.length; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < threads.length; i++) {
                if (threads[i].isAlive()) {
                    continue loop;
                }
            }
            break;
        }

        long end = System.nanoTime();

        System.out.format("%s: %d ms%n", name, (end - start) / 1000000);

    }
    
}
