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

public class ThreadExample1 implements Runnable {

    int i;

    public static void main(String a[]) {

        try {

            ThreadExample1 tt1 = new ThreadExample1(50);
            ThreadExample1 tt2 = new ThreadExample1(75);
            Thread t1 = new Thread(tt1, "Test thread 1");
            Thread t2 = new Thread(tt2, "Test thread 2");
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println("Main FINISHED");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    ThreadExample1(int i) {

        super();
        this.i = i;

    }

    public void run() {

        for (int j = 0; j < i; j++) {
            System.out.println(Thread.currentThread().getName() + " : " + j);
        }

        System.out.println(Thread.currentThread().getName() + " : FINISHED");

    }

}
