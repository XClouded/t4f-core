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

import io.aos.in.bio.BioByteIn;

import java.net.ServerSocket;
import java.net.Socket;

public class BioTcpEchoServer1 {

    public static void main(String... args) throws Exception {

        int port = 4444;
        ServerSocket serverSocket = new ServerSocket(port);

        // Repeatedly wait for connections, and process.
        while (true) {

            // A "blocking" call which waits until a connection is requested.
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            // Open up IO streams
            BioByteIn  inputStream = new BioByteIn(clientSocket);
            BioTcpEchoOut outputStream = new BioTcpEchoOut(clientSocket);

            // Waits for data and reads it inputStreamuntil connection dies
            // readLine() blocks until the server receives a new line from client
            String s;
            while ((s = inputStream.readLine()) != null) {
                outputStream.println(s);
            }

            // CSlose IO streams, then socket.
            System.err.println("Closing connection with client");
            outputStream.close();
            inputStream.close();
            clientSocket.close();

        }
    
    }
    
}
