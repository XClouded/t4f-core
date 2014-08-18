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
import java.io.*; 
import java.net.*; 
import java.util.*;
  
class BioUdpSimpleClient2
{ 
 private InetAddress IPAddress;
 boolean done;
 boolean keepGoing;

 public BioUdpSimpleClient2(String sHostName)
  {
   String s1;
   ArrayList lines = new ArrayList();
   int size;
   BufferedReader br;

   try {
        IPAddress = InetAddress.getByName(sHostName); 
        System.out.println ("Attemping to connect to " + IPAddress + 
                          ") via UDP port 9876");
       }
   catch (UnknownHostException ex) 
       { 
        System.err.println(ex);
        System.exit(1);
       }
  

  // set up the buffered reader to read from the keyboard
  try {
       br = new BufferedReader (new FileReader ("UDPInputFile.txt"));
       s1 = br.readLine();
       while (s1 != null)
         {
          lines.add(s1);
          s1 = br.readLine ();
         }
       size = lines.size();
       System.out.println ("ArrayList lines has size of: " + size); 

       done = false;

       DatagramSocket clientSocket = new DatagramSocket(); 
       for (int i = 0; i < size ; i++)
          {
       
           byte[] sendData = new byte[1024]; 
  
           s1 = (String) lines.get(i);
           sendData = s1.getBytes();         

           System.out.println ("Sending data to " + sendData.length + 
                               " bytes to server from line " + (i + 1));
           DatagramPacket sendPacket = 
              new DatagramPacket(sendData, sendData.length, IPAddress, 9876); 
  
           clientSocket.send(sendPacket); 
          }
       done = true;

       byte[] receiveData = new byte[1024]; 

       keepGoing = true;

       DatagramPacket receivePacket = 
            new DatagramPacket(receiveData, receiveData.length); 
  
       System.out.println ("Waiting for return packet");
       clientSocket.setSoTimeout(10000);

       while (keepGoing)
          {
           try {
              clientSocket.receive(receivePacket); 
              String modifiedSentence = 
                  new String(receivePacket.getData()); 
  
              //InetAddress returnIPAddress = receivePacket.getAddress();
        
              //int port = receivePacket.getPort();

              //System.out.println ("From server at: " + returnIPAddress + 
              //                    ":" + port);
              System.out.println("Message: " + modifiedSentence); 

             }
         catch (SocketTimeoutException ste)
             {
              System.out.println ("Timeout Occurred: Packet assumed lost");
              if (done)
                keepGoing = false;
             }
  
          }
       clientSocket.close(); 
      }
  catch (IOException ex)
     {
      System.err.println(ex);
     }
  } 


 public static void main(String... args) throws Exception 
   { 
    String serverHostname = new String ("127.0.0.1");

    if (args.length > 0)
       serverHostname = args[0];
  
    new BioUdpSimpleClient2 (serverHostname);

   }
} 

