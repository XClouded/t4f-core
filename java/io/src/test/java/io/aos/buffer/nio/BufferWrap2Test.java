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

import java.nio.CharBuffer;

public class BufferWrap2Test {
    
    public static void main(String[] argv) throws Exception {
    
        char[] chars = new char[60];

        CharBuffer cb = CharBuffer.wrap(chars, 12, 42);

        System.out.println("pos=" + cb.position() + ", limit=" + cb.limit() + ", cap=" + cb.capacity());

        cb.put("This is a test String");

        cb.flip();

        System.out.println("pos=" + cb.position() + ", limit=" + cb.limit() + ", cap=" + cb.capacity());

        cb.clear();

        cb.put("Foobar");
        System.out.println("pos=" + cb.position() + ", limit=" + cb.limit() + ", cap=" + cb.capacity());

        for (int i = 0; i < 20; i++) {
            System.out.println("[" + i + "] = " + chars[i]);
        }

    }

}
