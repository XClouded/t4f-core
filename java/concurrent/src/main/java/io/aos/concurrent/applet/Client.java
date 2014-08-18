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
package io.aos.concurrent.applet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public static final int DEFAULT_PORT = 6789;
    Socket socket;
    Thread reader, writer;

    // Create the client by creating its reader and writer threads
    // and starting them.
    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            // Create reader and writer sockets
            reader = new Reader(this);
            writer = new Writer(this);
            // Give the reader a higher priority to work around
            // a problem with shared access to the console.
            reader.setPriority(6);
            writer.setPriority(5);
            // Start the threads
            reader.start();
            writer.start();
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void usage() {
        System.out.println("Usage: java Client <hostname> [<port>]");
        System.exit(0);
    }

    public static void main(String... args) {
        int port = DEFAULT_PORT;
        Socket s = null;

        // Parse the port specification
        if ((args.length != 1) && (args.length != 2))
            usage();
        if (args.length == 1)
            port = DEFAULT_PORT;
        else {
            try {
                port = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                usage();
            }
        }

        new Client(args[0], port);
    }
}

// This thread reads data from the server and prints it on the console
// As usual, the run() method does the interesting stuff.
class Reader extends Thread {
    Client client;

    public Reader(Client c) {
        super("Client Reader");
        this.client = c;
    }

    @Override
    public void run() {
        DataInputStream in = null;
        String line;
        try {
            in = new DataInputStream(client.socket.getInputStream());
            while (true) {
                line = in.readLine();
                if (line == null) {
                    System.out.println("Server closed connection.");
                    break;
                }
                System.out.println(line);
            }
        }
        catch (IOException e) {
            System.out.println("Reader: " + e);
        }
        finally {
            try {
                if (in != null)
                    in.close();
            }
            catch (IOException e) {
                ;
            }
        }
        System.exit(0);
    }
}

// This thread reads user input from the console and sends it to the server.
class Writer extends Thread {
    Client client;

    public Writer(Client c) {
        super("Client Writer");
        client = c;
    }

    @Override
    public void run() {
        DataInputStream in = null;
        PrintStream out = null;
        try {
            String line;
            in = new DataInputStream(System.in);
            out = new PrintStream(client.socket.getOutputStream());
            while (true) {
                line = in.readLine();
                if (line == null)
                    break;
                out.println(line);
            }
        }
        catch (IOException e) {
            System.err.println("Writer: " + e);
        }
        finally {
            if (out != null)
                out.close();
        }
        System.exit(0);
    }
}
