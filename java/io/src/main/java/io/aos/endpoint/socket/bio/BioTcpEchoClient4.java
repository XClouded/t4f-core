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
import java.net.Socket;
import java.net.UnknownHostException;

public class BioTcpEchoClient4 {

    public static void main(String... args) {
        // First parameter has to be machine name
        if (args.length == 0) {
            System.out.println("Usage : EchoClient <serverName>");
            return;
        }

        Socket s = null;

        // Create the socket connection to the EchoServer.
        try {
            s = new Socket(args[0], 12111);
        } catch (UnknownHostException uhe) {
            // Host unreachable
            System.out.println("Unknown Host :" + args[0]);
            s = null;
        } catch (IOException ioe) {
            // Cannot connect to port on given host
            System.out
                    .println("Cant connect to server at 12111. Make sure it is running.");
            s = null;
        }

        if (s == null)
            System.exit(-1);

        BufferedReader inputStream= null;
        PrintWriter outputStream = null;

        try {
            // Create the streams to send and receive information
            inputStream= new BufferedReader(new InputStreamReader(s.getInputStream()));
            outputStream = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

            // Since this is the client, we will initiate the talking.
            // Send a string.
            outputStream.println("Hello");
            outputStream.flush();
            // receive the reply.
            System.out.println("Server Says : " + inputStream.readLine());

            // Send a string.
            outputStream.println("This");
            outputStream.flush();
            // receive a reply.
            System.out.println("Server Says : " + inputStream.readLine());

            // Send a string.
            outputStream.println("is");
            outputStream.flush();
            // receive a reply.
            System.out.println("Server Says : " + inputStream.readLine());

            // Send a string.
            outputStream.println("a");
            outputStream.flush();
            // receive a reply.
            System.out.println("Server Says : " + inputStream.readLine());

            // Send a string.
            outputStream.println("Test");
            outputStream.flush();
            // receive a reply.
            System.out.println("Server Says : " + inputStream.readLine());

            // Send the special string to tell server to quit.
            outputStream.println("Quit");
            outputStream.flush();
        } catch (IOException ioe) {
            System.out
                    .println("Exception during communication. Server probably closed connection.");
        } finally {
            try {
                // Close the streams
                outputStream.close();
                inputStream.close();
                // Close the socket before quitting
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
