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

public class BioTcpEchoServer4 {

    ServerSocket m_ServerSocket;

    public BioTcpEchoServer4() {
        try {
            // Create the server socket.
            m_ServerSocket = new ServerSocket(12111);
        } catch (IOException ioe) {
            System.out
            .println("Could not create server socket at 12111. Quitting.");
            System.exit(-1);
        }

        System.out.println("Listening for clients on 12111...");

        // Successfully created Server Socket. Now wait for connections.
        int id = 0;
        while (true) {
            try {
                // Accept incoming connections.
                Socket clientSocket = m_ServerSocket.accept();

                // accept() will block until a client connects to the server.
                // If execution reaches this point, then it means that a client
                // socket has been accepted.

                // For each client, we will start a service thread to
                // service the client requests. This is to demonstrate a
                // multithreaded server, although not required for such a
                // trivial application. Starting a thread also lets our
                // EchoServer accept multiple connections simultaneously.

                // Start a service thread

                ClientServiceThread cliThread = new ClientServiceThread(
                        clientSocket, id++);
                cliThread.start();
            } catch (IOException ioe) {
                System.out
                .println("Exception encountered on accept. Ignoring. Stack Trace :");
                ioe.printStackTrace();
            }
        }
    }

    public static void main(String... args) {
        new BioTcpEchoServer4();
    }

    class ClientServiceThread extends Thread {
        Socket m_clientSocket;
        int m_clientID = -1;
        boolean m_bRunThread = true;

        ClientServiceThread(Socket s, int clientID) {
            m_clientSocket = s;
            m_clientID = clientID;
        }

        public void run() {
            // ObtainputStreamthe input stream and the output stream for the socket
            // A good practice is to encapsulate them with a BufferedReader
            // and a PrintWriter as shown below.
            BufferedReader inputStream= null;
            PrintWriter outputStream = null;

            // Print outputStream details of this connection
            System.out.println("Accepted Client : ID - " + m_clientID
                    + " : Address - "
                    + m_clientSocket.getInetAddress().getHostName());

            try {
                inputStream= new BufferedReader(new InputStreamReader(
                        m_clientSocket.getInputStream()));
                outputStream = new PrintWriter(new OutputStreamWriter(
                        m_clientSocket.getOutputStream()));

                // At this point, we can read for input and reply with
                // appropriate output.

                // Run inputStreama loop until m_bRunThread is set to false
                while (m_bRunThread) {
                    // read incoming stream
                    String clientCommand = inputStream.readLine();

                    System.out.println("Client Says :" + clientCommand);

                    if (clientCommand.equalsIgnoreCase("quit")) {
                        // Special command. Quit this thread
                        m_bRunThread = false;
                        System.out.print("Stopping client thread for client : "
                                + m_clientID);
                    } else {
                        // Echo it back to the client.
                        outputStream.println(clientCommand);
                        outputStream.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Clean up
                try {
                    inputStream.close();
                    outputStream.close();
                    m_clientSocket.close();
                    System.out.println("...Stopped");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

}
