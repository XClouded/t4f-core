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
package io.aos.concurrent.spl3;

class Counter1 implements Runnable {

    private int currentValue;

    private Thread worker;

    public Counter1(String threadName) {
        currentValue = 0;
        worker = new Thread(this, threadName);
        System.out.println(worker);
        worker.start();
    }

    public void run() {
        try {
            while (currentValue < 5) {
                System.out.println(worker.getName() + (currentValue++));
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
        }
    }

    public int getCurrentValue() {
        return currentValue;
    }
}

public class ThreadExample2 {

    public static void main(String... args) {

        Counter1 counterA = new Counter1("CounterA");

        try {
            int val;
            do {
                val = counterA.getCurrentValue();
                System.out.println("Main thread:" + val);
                Thread.sleep(1000);
            } while (val < 5);
        }

        catch (InterruptedException e) {
            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
        }

        System.out.println("Exit");
    }
}
