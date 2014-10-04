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

public class BioTcpSerialServer 
{ 
 public static void main(String... args) throws IOException 
   { 
    ServerSocket serverSocket = null; 

    try { 
         serverSocket = new ServerSocket(10007); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10007."); 
         System.exit(1); 
        } 

    Socket clientSocket = null; 

    try { 
         System.out.println ("Waiting for Client");
         clientSocket = serverSocket.accept(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Accept failed."); 
         System.exit(1); 
        } 

    ObjectOutputStream outputStream = new ObjectOutputStream(
                                     clientSocket.getOutputStream()); 
    ObjectInputStream inputStream = new ObjectInputStream( 
                                     clientSocket.getInputStream()); 

    BioTcpSerialPoint3d pt3 = null;
    BioTcpSerialPoint3d pt4 = null;

    try {
         pt3 = (BioTcpSerialPoint3d) inputStream.readObject();
        }
    catch (Exception ex)
        {
         System.out.println (ex.getMessage());
        }


    System.out.println ("Server recieved point: " + pt3 + " from Client");

    pt4 = new BioTcpSerialPoint3d (-24, -23, -22);
    System.out.println ("Server sending point: " + pt4 + " to Client");
    outputStream.writeObject(pt4); 
    outputStream.flush();


    outputStream.close(); 
    inputStream.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
   } 
} 
