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
package io.aos.concurrent.priority;

import org.junit.Test;

public class ThreadPriorityTest {

    @Test
    public void test() throws Exception {

        ThreadPriority thread1 = new ThreadPriority();
        ThreadPriority thread2 = new ThreadPriority();
        ThreadPriority thread3 = new ThreadPriority();

        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.setPriority(Thread.NORM_PRIORITY);
        thread3.setPriority(Thread.MIN_PRIORITY);

        thread1.start();
        thread2.start();
        thread3.start();

        Thread.sleep(5000);

        System.out.println("Thread 1 MAX_PRIORITY: counter: "
                + thread1.getCounter());
        System.out.println("Thread 2 NORM_PRIORITY: counter: "
                + thread2.getCounter());
        System.out.println("Thread 3 MIN_PRIORITY: counter: "
                + thread3.getCounter());

    }
    
    private static class ThreadPriority extends Thread {
        private int counter = 0;

        public void run() {
            while (counter < Integer.MAX_VALUE) {
                counter++;
            }
        }

        public int getCounter() {
            return this.counter;
        }
    }

}
