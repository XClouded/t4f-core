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

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 *  Reads the file written by <code>data_writer.c</code>, using a ByteBuffer to
 *  extract the 4-byte integer from the file's contents.
 */
public class DataReader
{
    public static void main(String... argv)
    throws Exception
    {
        byte[] data = new byte[4];
        FileInputStream inputStream = new FileInputStream("/tmp/example.dat");
        if (inputStream.read(data) < 4)
            throw new Exception("unable to read file contents");

        ByteBuffer buf = ByteBuffer.wrap(data);
        System.out.println(String.format("data = %x", buf.getInt(0)));

        buf.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(String.format("data = %x", buf.getInt(0)));
    }
}
