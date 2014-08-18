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

public class BioTcpWebRedirector implements Runnable {

  private int port;
  private String newSite;
  
  public static void main(String... args) {

    int thePort;
    String theSite;
    
    try {
      theSite = args[0];
      // trim trailing slash
      if (theSite.endsWith("/")) {
        theSite = theSite.substring(0, theSite.length()-1);
      }
    }
    catch (Exception e) {
      System.out.println(
       "Usage: java Redirector http://www.newsite.com/ port");
      return;
    }
    
    try {
      thePort = Integer.parseInt(args[1]);
    }  
    catch (Exception e) {
      thePort = 80;
    }  
      
    Thread t = new Thread(new BioTcpWebRedirector(theSite, thePort));
    t.start();
  
  } 

public BioTcpWebRedirector(String site, int port) {
    this.port = port;
    this.newSite = site;
  }

  public void run() {
    
    try {
      
      ServerSocket server = new ServerSocket(this.port); 
      System.out.println("Redirecting connections on port " 
        + server.getLocalPort() + " to " + newSite);
        
      while (true) {
        
        try {
          Socket s = server.accept();
          Thread t = new RedirectThread(s);
          t.start();
        }  // end try
        catch (IOException e) {   
        }
        
      } // end while
      
    } // end try
    catch (BindException e) {
      System.err.println("Could not start server. Port Occupied");
    }         
    catch (IOException e) {
      System.err.println(e);
    }         

  }  // end run

  class RedirectThread extends Thread {
        
    private Socket connection;
        
    RedirectThread(Socket s) {
      this.connection = s;    
    }
        
    public void run() {
      
      try {
        
        Writer outputStream = new BufferedWriter(
                      new OutputStreamWriter(
                       connection.getOutputStream(), "ASCII"
                      )
                     );
        
        Reader inputStream= new InputStreamReader(
                     new BufferedInputStream( 
                      connection.getInputStream()
                     )
                    );
                    
        // read the first line only; that's all we need
        StringBuffer request = new StringBuffer(80);
        while (true) {
          int c = inputStream.read();
          if (c == '\r' || c == '\n' || c == -1) break;
          request.append((char) c);
        }

        // If this is HTTP/1.0 or later send a MIME header
        String get = request.toString();
        int firstSpace = get.indexOf(' ');
        int secondSpace = get.indexOf(' ', firstSpace+1);
        String theFile = get.substring(firstSpace+1, secondSpace);
        if (get.indexOf("HTTP/") != -1) {
          outputStream.write("HTTP/1.0 302 FOUND\r\n");
          Date now = new Date();
          outputStream.write("Date: " + now + "\r\n");
          outputStream.write("Server: Redirector 1.0\r\n");
          outputStream.write("Location: " + newSite + theFile + "\r\n");        
          outputStream.write("Content-type: text/html\r\n\r\n");                 
          outputStream.flush();                
        }
        // Not all browsers support redirection so we need to 
        // produce HTML that says where the document has moved to.
        outputStream.write("<HTML><HEAD><TITLE>Document moved</TITLE></HEAD>\r\n");
        outputStream.write("<BODY><H1>Document moved</H1>\r\n");
        outputStream.write("The document " + theFile  
         + " has moved to\r\n<A HREF=\"" + newSite + theFile + "\">" + newSite 
         + theFile + "</A>.\r\n Please update your bookmarks<P>");
        outputStream.write("</BODY></HTML>\r\n");
        outputStream.flush();

      } // end try
      catch (IOException e) {
      }
      finally {
        try {
          if (connection != null) connection.close();
        }
        catch (IOException e) {}  
      }     
  
    }  // end run
    
  }

}
