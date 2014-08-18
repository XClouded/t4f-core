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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

public class BioTcpSocketTyper {

    public static void main(String... args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java SocketTyper url1");
            return;
        }

        URL u = new URL(args[0]);
        if (!u.getProtocol().equalsIgnoreCase("http")) {
            System.err.println("Sorry, " + u.getProtocol() + " is not supported");
            return;
        }

        String host = u.getHost();
        int port = u.getPort();
        String file = u.getFile();
        if (file == null)
            file = "/";
        // default port
        if (port <= 0)
            port = 80;

        Socket s = null;
        try {
            s = new Socket(host, port);
            String request = "GET " + file + " HTTP/1.1\r\n" + "User-Agent: SocketTyper\r\n" + "Accept: text/*\r\n"
                    + "Host: " + host + "\r\n" + "\r\n";
            byte[] b = request.getBytes("US-ASCII");

            OutputStream outputStream = s.getOutputStream();
            InputStream inputStream = s.getInputStream();
            outputStream.write(b);
            outputStream.flush();

            for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
                System.out.write(c);
            }
        }
        finally {
            if (s != null && s.isConnected())
                s.close();
        }
    }
}
