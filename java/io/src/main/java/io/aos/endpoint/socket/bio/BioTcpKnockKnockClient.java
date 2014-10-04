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

public class BioTcpKnockKnockClient {
    
    public static void main(String... args) throws IOException {

        Socket kkSocket = null;
        PrintWriter outputStream = null;
        BufferedReader inputStream= null;

        try {
            kkSocket = new Socket("localhost", 4444);
            outputStream = new PrintWriter(kkSocket.getOutputStream(), true);
            inputStream= new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromServer = inputStream.readLine()) != null) {

            System.out.println("Server: " + fromServer);
            
            if (fromServer.equals("Bye.")) {
                break;
            }
            
            fromUser = stdIn.readLine();
            
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                outputStream.println(fromUser);
            }

        }

        outputStream.close();
        inputStream.close();
        stdIn.close();
        kkSocket.close();
   
    }
    
}

