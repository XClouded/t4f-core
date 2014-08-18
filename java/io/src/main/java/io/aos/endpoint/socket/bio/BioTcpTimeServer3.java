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
 
public class BioTcpTimeServer3 {
 
  public final static int DEFAULT_PORT = 37;

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
     catch (NumberFormatException e) {}  
   }     

   // The time protocol sets the epoch at 1900,
   // the java Date class at 1970. This number 
   // converts between them.
    
   long differenceBetweenEpochs = 2208988800L;
    
   try {
     ServerSocket server = new ServerSocket(port);
       while (true) {
         Socket connection = null;
         try {
           connection = server.accept();
           OutputStream outputStream = connection.getOutputStream();
           Date now = new Date();
           long msSince1970 = now.getTime();
           long secondsSince1970 = msSince1970/1000;
           long secondsSince1900 = secondsSince1970 
            + differenceBetweenEpochs;
           byte[] time = new byte[4];
           time[0] 
            = (byte) ((secondsSince1900 & 0x00000000FF000000L) >> 24);
           time[1] 
            = (byte) ((secondsSince1900 & 0x0000000000FF0000L) >> 16);
           time[2] 
            = (byte) ((secondsSince1900 & 0x000000000000FF00L) >> 8);
           time[3] = (byte) (secondsSince1900 & 0x00000000000000FFL);
           outputStream.write(time);
           outputStream.flush();      
         } // end try
         catch (IOException e) {
         } // end catch
         finally {
           if (connection != null) connection.close(); 
         }
       }  // end while
   }  // end try
   catch (IOException e) {
     System.err.println(e);
   } // end catch

  }

} // end TimeServer
