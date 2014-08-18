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
package io.aos.endpoint.file.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.FileChannel;

public class NioRootsChannel {

  final static int SIZE_OF_DOUBLE = 8;
  final static int LENGTH = 1001;
  
  public static void main(String... args) throws IOException {

    // Put 1001 roots into a ByteBuffer via a double view buffer
    ByteBuffer data = ByteBuffer.allocate(SIZE_OF_DOUBLE * LENGTH);
    DoubleBuffer roots = data.asDoubleBuffer();
    while (roots.hasRemaining()) {
      roots.put(Math.sqrt(roots.position()));
    }
    
    // Open a channel to the file where we'll store the data
    FileOutputStream fout = new FileOutputStream("roots.dat");
    FileChannel outChannel = fout.getChannel();
    outChannel.write(data);
    outChannel.close();
  }
}
