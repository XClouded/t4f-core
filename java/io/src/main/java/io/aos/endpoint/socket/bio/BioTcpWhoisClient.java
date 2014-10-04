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

public class BioTcpWhoisClient {

  public final static int DEFAULT_PORT = 43;
  public final static String DEFAULT_HOST = "whois.internic.net";

  public static void main(String... args) {

    InetAddress server;
    
    try {
      server = InetAddress.getByName(DEFAULT_HOST);
    }
    catch (UnknownHostException e) {
      System.err.println("Error: Could not locate default host "
       + DEFAULT_HOST);
      System.err.println(
       "Check to make sure you're connected to the Internet and that DNS is funtioning");
      System.err.println("Usage: java WhoisClient host port");         
      return;
    }       
    
    int port = DEFAULT_PORT;

    try {
      Socket theSocket = new Socket(server, port);
      Writer outputStream = new OutputStreamWriter(theSocket.getOutputStream(), 
       "8859_1");
      for (int i = 0; i < args.length; i++) outputStream.write(args[i] + " ");
      outputStream.write("\r\n");
      outputStream.flush();
      InputStream raw = theSocket.getInputStream();
      InputStream inputStream  = new BufferedInputStream(theSocket.getInputStream());
      int c;
      while ((c = inputStream.read()) != -1) System.out.write(c);
    }
    catch (IOException e) {
      System.err.println(e);
    }

  }

}
