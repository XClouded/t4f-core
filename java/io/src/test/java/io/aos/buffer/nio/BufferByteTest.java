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

import org.junit.Test;

public class BufferByteTest {

    @Test
    public void test() {

        // Create a ByteBuffer from a byte array
        byte[] bytes = new byte[10];
        ByteBuffer buf = ByteBuffer.wrap(bytes);

        // Retrieve bytes between the position and limit
        // (see Putting Bytes into a ByteBuffer)
        bytes = new byte[buf.remaining()];
        buf.get(bytes, 0, bytes.length);

        // Retrieve all bytes in the buffer
        buf.clear();
        bytes = new byte[buf.capacity()];
        buf.get(bytes, 0, bytes.length);

    }

}
