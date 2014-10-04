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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class BioTcpSimpleHttpServer {
    
    public static void mainputStream(String args[]) throws Exception {
        String requestMessageLine;
        String fileName;

        // check if a port number is given as the first command line argument
        // if not argument is given, use port number 6789
        int myPort = 6789;
        if (args.length > 0) {
            try {
                myPort = Integer.parseInt(args[0]);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Need port number as argument");
                System.exit(-1);
            }
            catch (NumberFormatException e) {
                System.out.println("Please give port number as integer.");
                System.exit(-1);
            }
        }

        // set up connection socket
        ServerSocket listenSocket = new ServerSocket(myPort);

        // listen (i.e. wait) for connection request
        System.out.println("Web server waiting for request on port " + myPort);
        Socket connectionSocket = listenSocket.accept();

        // set up the read and write end of the communication socket
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        // retrieve first line of request and set up for parsing
        requestMessageLine = inFromClient.readLine();
        System.out.println("Request: " + requestMessageLine);
        StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);

        // check for GET request
        if (tokenizedLine.nextToken().equals("GET")) {
            fileName = tokenizedLine.nextToken();

            // remove leading slash from line if exists
            if (fileName.startsWith("/") == true)
                fileName = fileName.substring(1);

            // access the requested file
            File file = new File(fileName);

            // convert file to a byte array
            int numOfBytes = (int) file.length();
            FileInputStream fis = new FileInputStream(fileName);
            byte[] fileInBytes = new byte[numOfBytes];
            fis.read(fileInBytes);

            // Send reply
            outToClient.writeBytes("HTTP/1.0 200 Document Follows\r\n");
            if (fileName.endsWith(".jpg"))
                outToClient.writeBytes("Content-Type: image/jpeg\r\n");
            if (fileName.endsWith(".gif"))
                outToClient.writeBytes("Content-Type: image/gif\r\n");
            outToClient.writeBytes("Content-Length: " + numOfBytes + "\r\n");
            outToClient.writeBytes("\r\n");
            outToClient.write(fileInBytes, 0, numOfBytes);

            // read and print outputStream the rest of the request
            requestMessageLine = inFromClient.readLine();
            while (requestMessageLine.length() >= 5) {
                System.out.println("Request: " + requestMessageLine);
                requestMessageLine = inFromClient.readLine();
            }
            System.out.println("Request: " + requestMessageLine);

            connectionSocket.close();
        }
        else {
            System.out.println("Bad Request Message");
        }
    }
}
