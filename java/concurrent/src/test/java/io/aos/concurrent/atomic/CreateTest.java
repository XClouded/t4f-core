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
package io.aos.concurrent.atomic;


import io.aos.concurrent.atomic.BaseTest.Timestamp;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CreateTest extends Thread {
    static AtomicInteger nCalls;
    static int target = 0;
    static boolean done = false;
    static Object lock = new Object();

    @Test
    public void test() {

        target = 10000;

        doTestCreate();
        doTestPool(8);
        doTestLoop();

        cleanGC();
        Timestamp createTS = new Timestamp(TimeUnit.MICROSECONDS);
        doTestCreate();
        createTS.stop();
        System.out.println("Create thread test took " + createTS);

        cleanGC();
        Timestamp pool8TS = new Timestamp(TimeUnit.MICROSECONDS);
        doTestPool(8);
        pool8TS.stop();
        System.out.println("Pool test (8 threads) took " + pool8TS);

        cleanGC();
        Timestamp poolTS = new Timestamp(TimeUnit.MICROSECONDS);
        doTestPool(1);
        poolTS.stop();
        System.out.println("Pool test (1 thread) took " + poolTS);

        cleanGC();
        Timestamp loopTS = new Timestamp(TimeUnit.MICROSECONDS);
        doTestLoop();
        loopTS.stop();
        System.out.println("Loop test took " + loopTS);

        double d = ((double) (createTS.elapsedTime() - loopTS.elapsedTime()))
                / target;
        System.out.println("Creating a thread took " + d + " " + loopTS.units()
                + " per thread");

        d = ((double) (createTS.elapsedTime() - poolTS.elapsedTime())) / target;
        System.out.println("Using a thread pool (1 thread) saved  " + d + " "
                + loopTS.units() + " per task");

        d = ((double) (createTS.elapsedTime() - pool8TS.elapsedTime()))
                / target;
        System.out.println("Using a thread pool (8 threads) saved  " + d + " "
                + loopTS.units() + " per task");

        d = ((double) (poolTS.elapsedTime() - loopTS.elapsedTime())) / target;
        System.out.println("Thread pool overhead (1 thread) is " + d + " "
                + loopTS.units() + " per task");

        d = ((double) (pool8TS.elapsedTime() - loopTS.elapsedTime())) / target;
        System.out.println("Thread pool overhead (8 threads) is " + d + " "
                + loopTS.units() + " per task");
    }

    static void doTestLoop() {
        nCalls = new AtomicInteger(0);
        done = false;
        for (int i = 0; i < target; i++)
            work();
        synchronized (lock) {
            while (!done)
                try {
                    lock.wait();
                } catch (Exception e) {
                }
        }
    }

    static void doTestCreate() {
        done = false;
        nCalls = new AtomicInteger(0);
        for (int i = 0; i < target; i++) {
            Thread t = new CreateTest();
            t.start();
        }
        synchronized (lock) {
            while (!done)
                try {
                    lock.wait();
                } catch (Exception e) {
                }
        }
    }

    static void doTestPool(int nThreads) {
        done = false;
        nCalls = new AtomicInteger(0);
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(nThreads, nThreads,
                50000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        Runnable r = new CreateTest();
        for (int i = 0; i < target; i++) {
            tpe.execute(r);
        }
        tpe.shutdown();
        try {
            tpe.awaitTermination(10000000L, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    public void run() {
        work();
    }

    public static void work() {
        int n = nCalls.incrementAndGet();
        if (n == target) {
            synchronized (lock) {
                done = true;
                lock.notify();
            }
        }
    }

    public static void cleanGC() {
        System.gc();
        System.runFinalization();
        System.gc();
    }
}
