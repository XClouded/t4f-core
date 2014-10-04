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
import java.net.ServerSocket;
import java.net.Socket;

public class BioTcpSocketServerMultiple implements Runnable {
    private Socket connection;
    private String TimeStamp;
    private int ID;

    public static void main(String... args) {
        int port = 19999;
        int count = 0;
        try {
            ServerSocket socket1 = new ServerSocket(port);
            
            System.out.println("MultipleSocketServer Initialized");
            
            while (true) {
                Socket connection = socket1.accept();
                Runnable runnable = new BioTcpSocketServerMultiple(connection, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        
        try {

            BufferedInputStream is = new BufferedInputStream(connection.getInputStream());

            InputStreamReader isr = new InputStreamReader(is);
            int character;
            StringBuffer process = new StringBuffer();
            
            while ((character = isr.read()) != 13) {
                process.append((char) character);
            }
            
            System.out.println(process);
            // need to wait 10 seconds to pretend that we're processing
            // something
            
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            TimeStamp = new java.util.Date().toString();

            String returnCode = "MultipleSocketServer responded at " + TimeStamp + (char) 13;
            BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
            osw.write(returnCode);
            osw.flush();
            
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
        finally {
            try {
                connection.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }

    private BioTcpSocketServerMultiple(Socket s, int i) {
        this.connection = s;
        this.ID = i;
    }

}
