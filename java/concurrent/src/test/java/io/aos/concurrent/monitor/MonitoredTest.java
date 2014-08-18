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
package io.aos.concurrent.monitor;

import io.aos.concurrent.monitor.Monitored;

import org.junit.Assert;
import org.junit.Test;

public class MonitoredTest {
    private final Monitored m = new Monitored();
    
    @Test
    public void testWithSynchronized() throws InterruptedException {
        final Monitored m = new Monitored();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    m.takeMonitorWithSynchronized();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Assert.fail(e.getMessage());
                }
            }
        }).start();
        m.releaseMonitorWithSynchronized();
    }

    @Test(expected = IllegalMonitorStateException.class)
    public void testWithoutSynchronized() throws InterruptedException {
        m.releaseMonitorWithoutSynchronized();
    }

}
