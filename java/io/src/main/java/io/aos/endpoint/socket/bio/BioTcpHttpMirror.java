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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTcpHttpMirror {

    public static void main(String... args) {
        try {
            int port = Integer.parseInt(args[0]); // The port to listen on
            ServerSocket ss = new ServerSocket(port); // Create a socket to
                                                      // listen
            for (;;) { // Loop forever
                Socket client = ss.accept(); // Wait for a connection
                ClientThread t = new ClientThread(client); // A thread to handle
                                                           // it
                t.start(); // Start the thread running
            } // Loop again
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Usage: java HttpMirror <port>;");
        }
    }

    static class ClientThread extends Thread {
        Socket client;

        ClientThread(Socket client) {
            this.client = client;
        }

        public void run() {
            try {
                // Get streams to talk to the client
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter outputStream = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                // Send an HTTP response header to the client
                outputStream.print("HTTP/1.0 200\r\nContent-Type: text/plain\r\n\r\n");

                // Read the HTTP request from the client and send it right back
                // Stop when we read the blank line from the client that marks
                // the end of the request and its headers.
                String line;
                while ((line = inputStream.readLine()) != null) {
                    if (line.length() == 0)
                        break;
                    outputStream.println(line);
                }

                outputStream.close();
                inputStream.close();
                client.close();
            }
            catch (IOException e) { /* Ignore exceptions */
            }
        }
    }
}
