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
import java.util.ArrayList;


/**
 *  Allocates large direct buffers until the virtual address space (or available
 *  commit charge) is exhausted.
 */
public class AllocationFailureExample
{
    public static void main(String... argv)
    throws Exception
    {
        final int bufSize = 250 * 1024 * 1024;  // 250 MB, adjust upwards for 64 bit
        ArrayList<ByteBuffer> refs = new ArrayList<ByteBuffer>();
        while (true)
        {
            System.out.println("allocating buffer " + refs.size());
            refs.add(ByteBuffer.allocateDirect(bufSize));
        }
    }
}
