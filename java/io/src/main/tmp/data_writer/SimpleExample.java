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
// Copyright (c) Keith D Gregory, all rights reserved
package com.kdgregory.example.bytebuffer;

import java.nio.ByteBuffer;


/**
 *  Demonstrates the creation of a <code>ByteBuffer</code> from an existing
 *  <code>byte[]</code>, and use to store and retrieve data.
 */
public class SimpleExample
{
    public static void main(String... argv)
    throws Exception
    {
        byte[] data = new byte[16];
        ByteBuffer buf = ByteBuffer.wrap(data);

        buf.putShort(0, (short)0x1234);
        buf.putInt(2, 0x12345678);
        buf.putLong(8, 0x1122334455667788L);

        for (int ii = 0 ; ii < data.length ; ii++)
            System.out.println(String.format("index %2d = %02x", ii, data[ii]));

        // demonstrates what happens if you don't keep track of your
        // offsets -- will retrieve the 2 bytes from the "short" value,
        // and the first two bytes of the "int" value
        System.out.println(String.format(
                "retrieving value from wrong index = %04x",
                buf.getInt(0)));
    }
}
