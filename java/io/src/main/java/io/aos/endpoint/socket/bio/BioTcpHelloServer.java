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
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTcpHelloServer {

    public static void main(String... args) throws IOException {

        int port = 2345;
        ServerSocket ss = new ServerSocket(port);
        while (true) {
            try {
                Socket s = ss.accept();

                String response = "Hello " + s.getInetAddress() + " on port "
                        + s.getPort() + "\r\n";
                response += "This is " + s.getLocalAddress() + " on port "
                        + s.getLocalPort() + "\r\n";
                OutputStream outputStream = s.getOutputStream();
                outputStream.write(response.getBytes("US-ASCII"));
                outputStream.flush();
                s.close();
            } catch (IOException ex) {
                // This is an error on one connection. Maybe the client crashed.
                // Maybe it broke the connection prematurely. Whatever happened,
                // it’s not worth shutting down the server for.
            }
        }
    }

}
