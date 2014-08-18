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
package io.aos.concurrent.spl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TaskTest {

    @Test
    public void test() {

        int nThreads = 10;
        long n = 10;

        Thread t[] = new Thread[nThreads];

        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Task(n, "Task " + i));
            t[i].start();
        }

        for (int i = 0; i < t.length; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ie) {
            }
        }

    }

    private static class Task implements Runnable {
        long n;
        String id;

        private long fib(long n) {
            if (n == 0)
                return 0L;
            if (n == 1)
                return 1L;
            return fib(n - 1) + fib(n - 2);
        }

        public Task(long n, String id) {
            this.n = n;
            this.id = id;
        }

        public void run() {
            Date d = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
            long startTime = System.currentTimeMillis();
            d.setTime(startTime);
            System.out.println("Starting task " + id + " at " + df.format(d));
            fib(n);
            long endTime = System.currentTimeMillis();
            d.setTime(endTime);
            System.out.println("Ending task " + id + " at " + df.format(d)
                    + " after " + (endTime - startTime) + " milliseconds");
        }
        
    }

}
