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
package io.aos.endpoint.url.handler.finger;

import java.net.*;
import java.io.*;

public class FingerUrlConnection extends URLConnection {

  private Socket connection = null;
  
  public final static int DEFAULT_PORT = 79;

  public FingerUrlConnection(URL u) {
    super(u);
  }

  public synchronized InputStream getInputStream() throws IOException {
  
    if (!connected) this.connect();
    InputStream inputStream = this.connection.getInputStream();
    return inputStream;
    
  }

  public String getContentType() {
    return "text/plain";
  }
  
  public synchronized void connect() throws IOException {
  
    if (!connected) {
      int port = url.getPort();
      if ( port < 1 || port > 65535) {
        port = DEFAULT_PORT;
      }
      this.connection = new Socket(url.getHost(), port);
      OutputStream outputStream = this.connection.getOutputStream();
      String names = url.getFile();
      if (names != null && !names.equals("")) {
        // delete initial /
        names = names.substring(1);
        names = URLDecoder.decode(names);
        byte[] result;
        try {
          result = names.getBytes("ASCII");
        }
        catch (UnsupportedEncodingException e) {
          result = names.getBytes();  
        }
        outputStream.write(result);
      }
      outputStream.write('\r');
      outputStream.write('\n');
      outputStream.flush();
      this.connected = true;
    } 
      
  }

}
