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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTcpSimpleServer extends Thread {
    public final static int DEFAULT_PORT = 6789;
    protected int port;
    protected ServerSocket listen_socket;

    // Exit with an error message, when an exception occurs.
    public static void fail(Exception e, String msg) {
        System.err.println(msg + ": " + e);
        System.exit(1);
    }

    // Create a ServerSocket to listen for connections on; start the thread.
    public BioTcpSimpleServer(int port) {
        if (port == 0)
            port = DEFAULT_PORT;
        this.port = port;
        try {
            listen_socket = new ServerSocket(port);
        }
        catch (IOException e) {
            fail(e, "Exception creating server socket");
        }
        System.out.println("Server: listening on port " + port);
        this.start();
    }

    // The body of the server thread. Loop forever, listening for and
    // accepting connections from clients. For each connection,
    // create a Connection object to handle communication through the
    // new Socket.
    public void run() {
        try {
            while (true) {
                Socket client_socket = listen_socket.accept();
                Connection c = new Connection(client_socket);
            }
        }
        catch (IOException e) {
            fail(e, "Exception while listening for connections");
        }
    }

    // Start the server up, listening on an optionally specified port
    public static void main(String... args) {
        int port = 0;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                port = 0;
            }
        }
        new BioTcpSimpleServer(port);
    }
}

// This class is the thread that handles all communication with a client
class Connection extends Thread {
    protected Socket client;
    protected DataInputStream inputStream;
    protected PrintStream outputStream;

    // Initialize the streams and start the thread
    public Connection(Socket client_socket) {
        client = client_socket;
        try {
            inputStream = new DataInputStream(client.getInputStream());
            outputStream = new PrintStream(client.getOutputStream());
        }
        catch (IOException e) {
            try {
                client.close();
            }
            catch (IOException e2) {
                ;
            }
            System.err.println("Exception while getting socket streams: " + e);
            return;
        }
        this.start();
    }

    // Provide the service.
    // Read a line, reverse it, send it back.
    public void run() {
        String line;
        StringBuffer revline;
        int len;
        try {
            for (;;) {
                // read inputStreama line
                line = inputStream.readLine();
                if (line == null)
                    break;
                // reverse it
                len = line.length();
                revline = new StringBuffer(len);
                for (int i = len - 1; i >= 0; i--)
                    revline.insert(len - 1 - i, line.charAt(i));
                // and write outputStream the reversed line
                outputStream.println(revline);
            }
        }
        catch (IOException e) {
            ;
        }
        finally {
            try {
                client.close();
            }
            catch (IOException e2) {
                ;
            }
        }
    }
}
