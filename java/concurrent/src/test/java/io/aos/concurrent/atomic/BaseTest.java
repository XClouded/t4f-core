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

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static class Timestamp {

        private long startTime;
        private long stopTime;
        private boolean stopped = false;
        private TimeUnit ts;

        public Timestamp() {
            this(TimeUnit.NANOSECONDS);
        }

        public Timestamp(TimeUnit ts) {
            this.ts = ts;
            start();
        }

        public void start() {
            startTime = System.nanoTime();
            stopped = false;
        }

        public void stop() {
            stopTime = System.nanoTime();
            stopped = true;
        }

        public long elapsedTime() {
            if (!stopped)
                throw new IllegalStateException("Timestamp not stopped");
            return ts.convert(stopTime - startTime, TimeUnit.NANOSECONDS);
        }

        public String toString() {
            try {
                return elapsedTime() + " " + ts;
            } catch (IllegalStateException ise) {
                return "Timestamp (not stopped)";
            }
        }

        public String units() {
            return ts.toString();
        }
    }

}
