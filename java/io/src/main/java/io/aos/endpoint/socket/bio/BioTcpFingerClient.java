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

public class BioTcpFingerClient {

  public final static int DEFAULT_PORT = 79;

  public static void main(String... args) {

    String hostname = "localhost";

    try {
      hostname = args[0];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      hostname = "localhost";
    }

    Socket connection = null;
    try {
      connection = new Socket(hostname, DEFAULT_PORT);
      Writer outputStream = new OutputStreamWriter(
       connection.getOutputStream(), "8859_1");
      for (int i = 1; i < args.length; i++) outputStream.write(args[i] + " ");
      outputStream.write("\r\n");
      outputStream.flush();
      connection.shutdownOutput();
      InputStream raw = connection.getInputStream();
      BufferedInputStream buffer = new BufferedInputStream(raw);
      InputStreamReader inputStream= new InputStreamReader(buffer, "8859_1");
      int c; 
      while ((c = inputStream.read()) != -1) {
        // filter non-printable and non-ASCII as recommended by RFC 1288
        if ((c >= 32 && c < 127) || c == '\t' || c == '\r' || c == '\n') { 
          System.out.write(c);
        }
      }
    }
    catch (IOException e) {
      System.err.println(e);
    }
    finally {
      try {
        if (connection != null) connection.close();
      }
      catch (IOException e) {} 
    }

  }

}
