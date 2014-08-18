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
package io.aos.netty3.echo;

import io.aos.netty3.echo.EchoClient;
import io.aos.netty3.echo.EchoServer;

import org.junit.Ignore;
import org.junit.Test;

public class EchoTest {
    private static final String PORT = "8080";
    private static final String[] SERVER_ARGS = new String[] { PORT };
    private static final String[] CLIENT_ARGS = new String[] { "localhost", PORT };
    private static final int NUMBER_OF_CALLS = 100;

    @Test
    @Ignore
    public void test() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EchoServer.main(SERVER_ARGS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        for (int i = 0; i < NUMBER_OF_CALLS; i++) {
            EchoClient.main(CLIENT_ARGS);
        }
    }

}
