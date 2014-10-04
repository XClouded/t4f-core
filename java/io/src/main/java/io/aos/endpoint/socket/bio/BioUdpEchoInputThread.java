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
package io.aos.endpoint.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class BioUdpEchoInputThread extends Thread {

    private InetAddress server;
    private DatagramSocket socket;
    private BioUdpEchoOutputThread output;
    private boolean stopped = false;
    private int port;

    public BioUdpEchoInputThread(InetAddress ia, int port) throws SocketException {
        this.server = ia;
        this.socket = new DatagramSocket();
        this.port = port;
    }

    public void halt() {
        this.stopped = true;
    }

    public DatagramSocket getSocket() {
        return this.socket;
    }

    public void run() {

        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (stopped)
                    return;
                String theLine = userInput.readLine();
                if (theLine.equals("."))
                    break;
                byte[] data = theLine.getBytes();
                DatagramPacket theOutput = new DatagramPacket(data, data.length, server, port);
                socket.send(theOutput);
                Thread.yield();
            }
        } // end try
        catch (IOException e) {
            System.err.println(e);
        }

    } // end run

}
