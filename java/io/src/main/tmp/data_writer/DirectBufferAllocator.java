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
 *  A program that allocates a large buffer outside the Java heap.
 */
public class DirectBufferAllocator
{
    public static void main(String... argv)
    throws Exception
    {
        ByteBuffer myBuffer = ByteBuffer.allocateDirect(100 * 1024 * 1024);

        // now that we've got the buffer, we'll sleep so that you can see
        // it in pmap(): look for a 102408K "anon" block with permissions "rwx"
        Thread.sleep(60000L);
    }
}
