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
import java.io.*;

public class BioTcpEchoClient1a {

    public static void main(String... args) {

        String hostname = "localhost";

        if (args.length > 0) {
            hostname = args[0];
        }

        PrintWriter outputStream = null;
        BufferedReader networkIn = null;
        try {
            Socket theSocket = new Socket(hostname, 7);
            networkIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            outputStream = new PrintWriter(theSocket.getOutputStream());
            System.out.println("Connected to echo server");

            while (true) {
                String theLine = userIn.readLine();
                if (theLine.equals("."))
                    break;
                outputStream.println(theLine);
                outputStream.flush();
                System.out.println(networkIn.readLine());
            }

        } // end try
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                if (networkIn != null)
                    networkIn.close();
                if (outputStream != null)
                    outputStream.close();
            }
            catch (IOException e) {
            }
        }

    }

} // end EchoClient
