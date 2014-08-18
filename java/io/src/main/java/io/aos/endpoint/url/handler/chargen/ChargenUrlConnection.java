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
package io.aos.endpoint.url.handler.chargen;

import io.aos.in.bio.FiniteByteIn;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class ChargenUrlConnection extends URLConnection {

  private Socket connection = null;
  
  public final static int DEFAULT_PORT = 19;

  public ChargenUrlConnection(URL u) {
    super(u);
  }

  public synchronized InputStream getInputStream() throws IOException {
  
    if (!connected) this.connect();
    return new FiniteByteIn(this.connection.getInputStream());
    
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
      this.connected = true;
    } 
  
  }

}
