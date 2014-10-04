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

import org.junit.Test;

/**
 * Test the effects of buffer flipping.
 */
public class BufferFlipTest {

    @Test
    public void test() throws Exception {
        CharBuffer cb = CharBuffer.allocate(15);
        cb.put("Hello World");
        print(cb);
        cb.flip();
        print(cb);
        cb.flip();
        print(cb);
        cb.flip();
        print(cb);
    }

    private static void print(CharBuffer cb) {
        System.out.println("pos=" + cb.position() + ", limit=" + cb.limit() + ": '" + cb + "'");
    }

}
