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

public class BioTcpEchoServer2b extends Thread
{ 
 protected static boolean serverContinue = true;
 protected Socket clientSocket;

 public static void main(String... args) throws IOException 
   { 
    ServerSocket serverSocket = null; 

    try { 
         serverSocket = new ServerSocket(10008); 
         System.out.println ("Connection Socket Created");
         try { 
              while (serverContinue)
                 {
                  System.out.println ("Waiting for Connection");
                  new BioTcpEchoServer2b (serverSocket.accept()); 
                 }
             } 
         catch (IOException e) 
             { 
              System.err.println("Accept failed."); 
              System.exit(1); 
             } 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10008."); 
         System.exit(1); 
        } 
    finally
        {
         try {
              serverSocket.close(); 
             }
         catch (IOException e)
             { 
              System.err.println("Could not close port: 10008."); 
              System.exit(1); 
             } 
        }
   }

 private BioTcpEchoServer2b (Socket clientSoc)
   {
    clientSocket = clientSoc;
    start();
   }

 public void run()
   {
    System.out.println ("New Communication Thread Started");

    try { 
         PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), 
                                      true); 
         BufferedReader inputStream= new BufferedReader( 
                 new InputStreamReader( clientSocket.getInputStream())); 

         String inputLine; 

         while ((inputLine = inputStream.readLine()) != null) 
             { 
              System.out.println ("Server: " + inputLine); 
              outputStream.println(inputLine); 

              if (inputLine.equals("Bye.")) 
                  break; 

              if (inputLine.equals("End Server.")) 
                  serverContinue = false; 
             } 

         outputStream.close(); 
         inputStream.close(); 
         clientSocket.close(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Problem with Communication Server");
         System.exit(1); 
        } 
    }
} 
