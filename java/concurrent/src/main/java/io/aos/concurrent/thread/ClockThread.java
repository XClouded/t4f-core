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
package io.aos.concurrent.thread;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ClockThread extends Thread {
    // This field is volatile because two different threads may access it
    volatile boolean keepRunning = true;

    public ClockThread() {
        setDaemon(true);
    }

    @Override
    public void run() {
        while (keepRunning) {
            long now = System.currentTimeMillis();
            System.out.printf("%tr%n", now);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                return;
            }
        }
    }

    public void pleaseStop() {
        keepRunning = false;
    }

    public static void main(String... args) {
        ClockThread c = new ClockThread(); // Create a Clock thread
        c.start();
        try {
            SECONDS.sleep(10);
        }
        catch (InterruptedException ignore) {
        }
        c.pleaseStop();
    }
}
