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
 *  Demonstrates the use of <code>slice()</code> to create a <code>ByteBuffer</code>
 *  that represents an arbitrary offset into an existing buffer. This is very useful
 *  for processing structured binary data, such as that found in a graphics file.
 */
public class SliceExample
{
    public static void main(String... argv)
    throws Exception
    {
        byte[] data = new byte[256];
        for (int ii = 0 ; ii < data.length ; ii++)
            data[ii] = (byte)ii;

        ByteBuffer buf1 = ByteBuffer.wrap(data);

        buf1.position(128);
        ByteBuffer buf2 = buf1.slice();

        System.out.println(String.format(
                "buf2[0], before update = %08x",
                buf2.getInt(0)));

        // note that we're changing the original buffer
        buf1.putInt(128, 0x12345678);

        System.out.println(String.format(
                "buf2[0], after update  = %08x",
                buf2.getInt(0)));
    }
}
