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
import java.util.Date;
 
public class BioTcpDaytimeServer {
 
  public final static int DEFAULT_PORT = 13;

  public static void main(String... args) {

   int port = DEFAULT_PORT;     
   if (args.length > 0) {
     try {
        port = Integer.parseInt(args[0]);
        if (port < 0 || port >= 65536) {
          System.out.println("Port must between 0 and 65535");
          return;      
        }
     }   
     catch (NumberFormatException e) {
       // use default port
     }  

   }     

   try {
    
     ServerSocket server = new ServerSocket(port);
      
     Socket connection = null;
     while (true) {
        
       try {
         connection = server.accept();
         OutputStreamWriter outputStream 
          = new OutputStreamWriter(connection.getOutputStream());
         Date now = new Date();
         outputStream.write(now.toString() +"\r\n");
         outputStream.flush();      
         connection.close();
       }
       catch (IOException e) {}
       finally {
         try {
           if (connection != null) connection.close();
         }
         catch (IOException e) {}          
       }
         
     }  // end while
       
   }  // end try
   catch (IOException e) {
     System.err.println(e);
   } // end catch

  }

} // end DaytimeServer
