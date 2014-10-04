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

import io.aos.in.bio.SafeBufferedCharIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class BioTcpClientServer {

    public static void main(String... args) {

        int port;

        try {
            port = Integer.parseInt(args[0]);
        }
        catch (Exception e) {
            port = 0;
        }

        try {
            ServerSocket server = new ServerSocket(port, 1);
            System.out.println("Listening for connections on port " + server.getLocalPort());

            while (true) {
                Socket connection = server.accept();
                try {
                    System.out.println("Connection established with " + connection);
                    Thread input = new InputThread(connection.getInputStream());
                    input.start();
                    Thread output = new OutputThread(connection.getOutputStream());
                    output.start();
                    // wait for output and input to finish
                    try {
                        input.join();
                        output.join();
                    }
                    catch (InterruptedException e) {
                    }
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                finally {
                    try {
                        if (connection != null)
                            connection.close();
                    }
                    catch (IOException e) {
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class InputThread extends Thread {

    InputStream inputStream;

    public InputThread(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void run() {

        try {
            while (true) {
                int i = inputStream.read();
                if (i == -1)
                    break;
                System.out.write(i);
            }
        }
        catch (SocketException e) {
            // output thread closed the socket
        }
        catch (IOException e) {
            System.err.println(e);
        }
        try {
            inputStream.close();
        }
        catch (IOException e) {
        }

    }

}

class OutputThread extends Thread {

    Writer outputStream;

    public OutputThread(OutputStream outputStream) {
        this.outputStream = new OutputStreamWriter(outputStream);
    }

    public void run() {

        String line;
        BufferedReader inputStream = new SafeBufferedCharIn(new InputStreamReader(System.in));
        try {
            while (true) {
                line = inputStream.readLine();
                if (line.equals("."))
                    break;
                outputStream.write(line + "\r\n");
                outputStream.flush();
            }
        }
        catch (IOException e) {
        }
        try {
            outputStream.close();
        }
        catch (IOException e) {
        }

    }

}
