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
package io.aos.concurrent.spl0.cs12.example4;

import java.io.*;
import java.net.*;

public class CalcClient extends Thread {

    long n;
    Socket sock;

    public CalcClient(long n, String host, int port) throws UnknownHostException, IOException {
        this.n = n;
	sock = new Socket(host, port);
    }

    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
            dos.writeLong(n);
            DataInputStream dis = new DataInputStream(sock.getInputStream());
            System.out.println("Received answer " + dis.readLong());
        } catch (IOException ioe) {
            System.out.println("Socket error " + ioe);
        }
    }

    public static void main(String... args) throws Exception {
        int nThreads = Integer.parseInt(args[0]);
        long n = Long.parseLong(args[1]);
        int port = Integer.parseInt(args[3]);
        CalcClient[] clients = new CalcClient[nThreads];
        for (int i = 0; i < nThreads; i++) {
            clients[i] = new CalcClient(n, args[2], port);
            clients[i].start();
        }
        for (int i = 0; i < nThreads; i++) {
            clients[i].join();
        }
    }
}
