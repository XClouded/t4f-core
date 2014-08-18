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
package io.aos.endpoint.socket.nio.fwk1.impl;

import io.aos.endpoint.socket.nio.fwk1.api.BufferFactory;
import io.aos.endpoint.socket.nio.fwk1.api.InputQueue;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.io.IOException;

class InputQueueImpl implements InputQueue {
    private final BufferFactory bufferFactory;
    private final ByteBuffer emptyBuffer;
    private ByteBuffer buffer = null;

    public InputQueueImpl(BufferFactory bufferFactory) {
        this.bufferFactory = bufferFactory;
        emptyBuffer = ByteBuffer.allocate(0).asReadOnlyBuffer();
    }

    public synchronized int fillFrom(ByteChannel channel) throws IOException {
        if (buffer == null) {
            buffer = bufferFactory.newBuffer();
        }

        return channel.read(buffer);
    }

    // -- not needed by framework

    public synchronized boolean isEmpty() {
        return (buffer == null) || (buffer.position() == 0);
    }

    public synchronized int indexOf(byte b) {
        if (buffer == null) {
            return -1;
        }

        int pos = buffer.position();

        for (int i = 0; i < pos; i++) {
            if (b == buffer.get(i)) {
                return i;
            }
        }

        return -1;
    }

    public synchronized ByteBuffer dequeueBytes(int count) {
        if ((buffer == null) || (buffer.position() == 0) || (count == 0)) {
            return emptyBuffer;
        }

        int size = Math.min(count, buffer.position());

        ByteBuffer result = ByteBuffer.allocate(size);

        buffer.flip();

        // TODO: Validate this
        // result.put (buffer.array(), 0, size);
        // buffer.position (size);
        // result.position (size);

        // TODO: this if() should be replaceable by the above
        if (buffer.remaining() <= result.remaining()) {
            result.put(buffer);
        }
        else {
            while (result.hasRemaining()) {
                result.put(buffer.get());
            }
        }

        if (buffer.remaining() == 0) {
            bufferFactory.returnBuffer(buffer);
            buffer = null;
        }
        else {
            buffer.compact();
        }

        result.flip();

        return (result);
    }

    public void discardBytes(int count) {
        dequeueBytes(count);
    }

}
