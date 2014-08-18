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
package io.aos.concurrent.spl0.cs12.example5;


import io.aos.concurrent.spl0.cs12.*;

import java.net.*;
import java.io.*;

public class InterruptibleClient extends InterruptibleReader {

    public void processData(byte[] b, int n) {
        System.out.println("Got data " + new String(b, 0, n));
    }

    public InterruptibleClient(InputStream is) {
        super(is);
    }

    public static void main(String... args) throws Exception {
        Socket s = new Socket(args[0], Integer.parseInt(args[1]));
        InputStream is = s.getInputStream();
        InterruptibleClient c = new InterruptibleClient(is);
        c.start();
        System.out.println("Main thread sleeping");
        Thread.sleep(10000);
        System.out.println("Main thread woke up");
        c.interrupt();
        System.out.println("Main thread called interrupt");
    }
}
