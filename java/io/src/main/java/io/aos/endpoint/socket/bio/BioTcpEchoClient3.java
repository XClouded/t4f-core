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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BioTcpEchoClient3 {

    public static void main(String... args) throws IOException {

        Scanner s = new Scanner(System.in);
        String serverHostname;
        System.out.println("Enter an IP value: ");
        serverHostname = s.next();

        // String serverHostname = new String ("127.0.0.1");

        if (args.length > 0)
            serverHostname = args[0];
        System.out.println("Attemping to connect to host " + serverHostname
                + " on port 10007.");

        Socket echoSocket = null;
        PrintWriter outputStream = null;
        BufferedReader inputStream= null;

        try {
            echoSocket = new Socket(serverHostname, 10007);
            outputStream = new PrintWriter(echoSocket.getOutputStream(), true);
            inputStream= new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: "
                    + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                System.in));
        String userInput;

        System.out.print("input: ");
        while ((userInput = stdIn.readLine()) != null) {
            outputStream.println(userInput);
            System.out.println("echo: " + inputStream.readLine());
            System.out.print("input: ");
        }

        outputStream.close();
        inputStream.close();
        stdIn.close();
        echoSocket.close();
    }

}
