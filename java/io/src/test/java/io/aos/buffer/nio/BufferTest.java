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
package io.aos.buffer.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Test;

public class BufferTest {

    @Test
    public void testBuffer() {

        ByteBuffer bb = ByteBuffer.allocate(100);

        bb.mark();
        bb.position(5);
        bb.reset();

        bb.mark().position(5).reset();

        char[] myBuffer = new char[100];

        CharBuffer cb = CharBuffer.wrap(myBuffer);
        cb.position(12).limit(21);

        CharBuffer sliced = cb.slice();

        System.out.println("Sliced: offset=" + sliced.arrayOffset() + ", capacity=" + sliced.capacity());

    }
    
    @Test
    public void test2() {
//        Channel inChannel = ....;        
//        ByteBuffer buf = ByteBuffer.allocate(48);
//        int bytesRead = -1;
//        do {
//        bytesRead = inChannel.read(buf);
//        if (bytesRead != -1) {
//        buf.flip();
//        while(buf.hasRemaining()){
//        System.out.print((char) buf.get());
//        }
//        buf.clear();
//        }
//        } while (bytesRead != -1);
//        inChannel.close();    
//        
    }
    
}
