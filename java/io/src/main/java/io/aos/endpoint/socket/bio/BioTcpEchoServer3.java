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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTcpEchoServer3 {

    public static void main(String... args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10007);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10007.");
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println("Connection successful");

        PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);

        BufferedReader inputStream= new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));

        String cAddress = "";
        String inputLine;

        cAddress = clientSocket.getInetAddress().toString();

        while ((inputLine = inputStream.readLine()) != null) {
            System.out.println("Server: " + inputLine + "  " + cAddress + " ");
            outputStream.println(inputLine + " " + cAddress);

            if (inputLine.equals("bye"))
                break;
        }

        outputStream.close();
        inputStream.close();
        clientSocket.close();
        serverSocket.close();
    }

}
