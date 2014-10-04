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
package io.aos.endpoint.socket.nio;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class NioHelloServer {

  public final static int PORT = 2345;

  public static void main(String... args) throws IOException {
    
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    SocketAddress port = new InetSocketAddress(PORT);
    serverChannel.socket().bind(port);
    
    while (true) {
      try {
        SocketChannel clientChannel = serverChannel.accept();
          
        String response = "Hello " 
         + clientChannel.socket().getInetAddress() + " on port " 
         + clientChannel.socket().getPort() + "\r\n";
        response += "This is " + serverChannel.socket() + " on port " 
         + serverChannel.socket().getLocalPort() + "\r\n";
        
        byte[] data = response.getBytes("UTF-8");
        ByteBuffer buffer = ByteBuffer.wrap(data);
        while (buffer.hasRemaining()) clientChannel.write(buffer);
        clientChannel.close();
      }
      catch (IOException ex) {
        // This is an error on one connection. Maybe the client crashed.
        // Maybe it broke the connection prematurely. Whatever happened, 
        // it's not worth shutting down the server for. 
      }
    }  // end while
  }
} // end NewIOHelloServer
