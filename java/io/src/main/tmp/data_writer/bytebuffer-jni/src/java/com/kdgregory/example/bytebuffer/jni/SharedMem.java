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
package com.kdgregory.example.bytebuffer.jni;

import java.nio.ByteBuffer;

/**
 *  A holder for a shared memory segment. The constructor is given the "key" of
 *  the segment, and will either create or attach to the segment with that key.
 *  The program then calls {@link #getBuffer} to map the segment as a direct
 *  <code>ByteBuffer</code>. When done, the program must call {@link #close} (or
 *  invoke the finalizer).
 *  <p>
 *  That this class <em>does not</em> attempt to synchronize access to the
 *  shared memory. The behavior when multiple processes attempt to write to
 *  the memory at the same time is undefined. For production code, you would
 *  need to create a semaphore that controls access, and not permit direct use
 *  of the mapping buffer. At which point, it's probably easiest just to use
 *  some other IPC mechanism.
 */
public class SharedMem
{
    static
    {
        System.loadLibrary("SharedMem");
    }


    private ByteBuffer _buf;


    public SharedMem(int key, int size)
    {
        attach(key, size);
    }


    public ByteBuffer getBuffer()
    {
        return _buf;
    }


    public void close()
    {
        if (_buf == null)
            return;
        detach();
        _buf = null;
    }


    @Override
    protected void finalize()
    throws Throwable
    {
        close();
    }


    private native void attach(int key, int size);
    private native void detach();
}
