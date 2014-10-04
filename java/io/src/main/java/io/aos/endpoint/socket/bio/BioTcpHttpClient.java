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

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class BioTcpHttpClient {

    public static void main(String... args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: java HTTPGrab url filename");
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
        if (port <= 0)
            port = 80;

        SocketAddress remote = new InetSocketAddress(host, port);
        SocketChannel channel = SocketChannel.open(remote);
        FileOutputStream outputStream = new FileOutputStream(args[1]);
        FileChannel localFile = outputStream.getChannel();

        String request = "GET " + file + " HTTP/1.1\r\n" + "User-Agent: HTTPGrab\r\n" + "Accept: text/*\r\n"
                + "Connection: close\r\n" + "Host: " + host + "\r\n" + "\r\n";

        ByteBuffer header = ByteBuffer.wrap(request.getBytes("US-ASCII"));
        channel.write(header);

        ByteBuffer buffer = ByteBuffer.allocate(8192);
        while (channel.read(buffer) != -1) {
            buffer.flip();
            localFile.write(buffer);
            buffer.clear();
        }
        
        localFile.close();
        channel.close();
        outputStream.close();
        
    }
}
