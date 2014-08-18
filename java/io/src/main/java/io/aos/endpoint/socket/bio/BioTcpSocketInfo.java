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

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class BioTcpSocketInfo {

    public static void info(String... hosts) throws UnknownHostException, IOException {

        for (int i = 0; i < hosts.length; i++) {
            Socket socket = new Socket(hosts[i], 80);
            System.out.println("Connected to " + socket.getInetAddress() + " on port " + socket.getPort()
                    + " from port " + socket.getLocalPort() + " of " + socket.getLocalAddress());
            socket.close();
        }

    }

}
