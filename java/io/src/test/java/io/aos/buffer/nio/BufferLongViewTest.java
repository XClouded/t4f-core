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
import java.nio.ByteOrder;

/**
 * Test views of long elements in a ByteBuffer.
 */
public class BufferLongViewTest {

    public static void main(String[] argv) throws Exception {

        ByteBuffer bb = ByteBuffer.allocate(20);

        bb.put((byte) 0x07);
        bb.put((byte) 0x3b);
        bb.put((byte) 0xc5);
        bb.put((byte) 0x31);
        bb.put((byte) 0x5e);
        bb.put((byte) 0x94);
        bb.put((byte) 0xd6);
        bb.put((byte) 0x04);

        bb.position(1).limit(5);
        bb.mark();

        int value;

        value = bb.order(ByteOrder.BIG_ENDIAN).getInt();

        System.out.println("" + bb.order().toString() + ": " + Integer.toHexString(value));

        bb.reset();

        value = bb.order(ByteOrder.LITTLE_ENDIAN).getInt();

        System.out.println("" + bb.order().toString() + ": " + Integer.toHexString(value));

        bb.reset();

        System.out.println("Expect an exception here");
        System.out.println("" + bb.order().toString() + ": " + bb.getLong());
    }

}
