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

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest extends BaseTest {
    static int nLoops;
    static int nThreads;

    public static void main(String... args) {

	nLoops = 10000;
	nThreads = 1;
        doTest(new AtomicRunnable());
        doTest(new SyncRunnable());
	nLoops = Integer.parseInt(args[0]);
	nThreads = Integer.parseInt(args[1]);

        System.out.println("Starting atomic test");
	cleanGC();
	Timestamp atomicTS = new Timestamp();
        doTest(new AtomicRunnable());
	atomicTS.stop();
        System.out.println("Atomic took " + atomicTS);

        System.out.println("Starting sync test");
	cleanGC();
	Timestamp syncTS = new Timestamp();
        doTest(new SyncRunnable());
	syncTS.stop();
        System.out.println("Local sync took " + syncTS);

	double d = ((double) (syncTS.elapsedTime() - atomicTS.elapsedTime())) /
		(nLoops * nThreads);
	System.out.println("Atomic operation saves " + d + " " + syncTS.units() + " per call");
    }

    static void cleanGC() {
        System.gc();
        System.runFinalization();
        System.gc();
    }

    static class AtomicRunnable implements Runnable {
        AtomicInteger ai = new AtomicInteger(1);
        public void run() {
            for (int i = 0; i < nLoops; i++)
	        ai.incrementAndGet();
        }
    }

    static class SyncRunnable implements Runnable {
        int testVar;

        synchronized void incrVar() {
            testVar++;
        }

        public void run() {
            for (int i = 0; i < nLoops; i++)
	        incrVar();
        }
    }

    static void doTest(Runnable r) {
	Thread threads[] = new Thread[nThreads];
	for (int i = 0; i < nThreads; i++) {
	    threads[i] = new Thread(r);
	}
	for (int i = 0; i < nThreads; i++) {
	    threads[i].start();
	}
	for (int i = 0; i < nThreads; i++) {
	    try {
	        threads[i].join();
	    } catch (InterruptedException ie) {}
	}
    }    
}
