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
import java.util.*;

public class BioTcpSingleFileHttpServer extends Thread {

  private byte[] content;
  private byte[] header;
  private int port = 80;

  public BioTcpSingleFileHttpServer(String data, String encoding, 
   String MIMEType, int port) throws UnsupportedEncodingException {    
    this(data.getBytes(encoding), encoding, MIMEType, port);
  }

  public BioTcpSingleFileHttpServer(byte[] data, String encoding, 
   String MIMEType, int port) throws UnsupportedEncodingException {
    
    this.content = data;
    this.port = port;
    String header = "HTTP/1.0 200 OK\r\n"
     + "Server: OneFile 1.0\r\n"
     + "Content-length: " + this.content.length + "\r\n"
     + "Content-type: " + MIMEType + "\r\n\r\n";
    this.header = header.getBytes("ASCII");

  }

  
  public void run() {
  
    try {
      ServerSocket server = new ServerSocket(this.port); 
      System.out.println("Accepting connections on port " 
        + server.getLocalPort());
      System.out.println("Data to be sent:");
      System.out.write(this.content);
      while (true) {
        
        Socket connection = null;
        try {
          connection = server.accept();
          OutputStream outputStream = new BufferedOutputStream(
                                  connection.getOutputStream()
                                 );
          InputStream inputStream   = new BufferedInputStream(
                                  connection.getInputStream()
                                 );
          // read the first line only; that's all we need
          StringBuffer request = new StringBuffer(80);
          while (true) {
            int c = inputStream.read();
            if (c == '\r' || c == '\n' || c == -1) break;
            request.append((char) c);
            // If this is HTTP/1.0 or later send a MIME header

          }
          if (request.toString().indexOf("HTTP/") != -1) {
            outputStream.write(this.header);
          }         
          outputStream.write(this.content);
          outputStream.flush();
        }  // end try
        catch (IOException e) {   
        }
        finally {
          if (connection != null) connection.close(); 
        }
        
      } // end while
    } // end try
    catch (IOException e) {
      System.err.println("Could not start server. Port Occupied");
    }

  } // end run


  public static void main(String... args) {

    try {
        
      String contentType = "text/plain";
      if (args[0].endsWith(".html") || args[0].endsWith(".htm")) {
        contentType = "text/html";
      }
      
      InputStream inputStream = new FileInputStream(args[0]);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      int b;
      while ((b = inputStream.read()) != -1) outputStream.write(b);
      byte[] data = outputStream.toByteArray();
        
      // set the port to listen on
      int port;
      try {
        port = Integer.parseInt(args[1]);
        if (port < 1 || port > 65535) port = 80;
      }  
      catch (Exception e) {
        port = 80;
      }  
      
      String encoding = "ASCII";
      if (args.length >= 2) encoding = args[2]; 
       
      Thread t = new BioTcpSingleFileHttpServer(data, encoding,
       contentType, port);
      t.start();         

    }
    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(
       "Usage: java SingleFileHTTPServer filename port encoding");
    }
    catch (Exception e) {
      System.err.println(e);
    }
  
  }

}
