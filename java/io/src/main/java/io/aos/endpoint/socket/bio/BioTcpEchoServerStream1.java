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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class BioTcpEchoServerStream1 {
    protected final static int PORT_NUMBER = 7777;

    public static void main(String... args) {
        
        Socket connection = null;
    
        try {
        
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER, 50, getLocalhost());
            int character;

            while (true) {
            
                connection = serverSocket.accept();

                BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
                InputStreamReader isr = new InputStreamReader(is);
                StringBuffer process = new StringBuffer();

                while ((character = isr.read()) != 13) {
                    process.append((char) character);
                }
                System.out.println(process + "\n");
                
                BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(process.toString());
                osw.flush();
                
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    protected static InetAddress getLocalhost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

}
