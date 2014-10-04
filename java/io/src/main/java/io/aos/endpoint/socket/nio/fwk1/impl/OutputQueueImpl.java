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
import io.aos.endpoint.socket.nio.fwk1.api.ChannelFacade;
import io.aos.endpoint.socket.nio.fwk1.api.OutputQueue;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.LinkedList;

public class OutputQueueImpl implements OutputQueue {
    private final BufferFactory bufferFactory;
    private final ChannelFacade facade;
    private final LinkedList<ByteBuffer> queue;
    private ByteBuffer active = null;

    public OutputQueueImpl(BufferFactory bufferFactory, ChannelFacade adapter) {
        this.bufferFactory = bufferFactory;
        this.facade = adapter;
        queue = new LinkedList<ByteBuffer>();
    }

    public synchronized boolean isEmpty() {
        return (active == null) && (queue.size() == 0);
    }

    public synchronized int drainTo(ByteChannel channel) throws IOException {
        int bytesWritten = 0;

        while (true) {
            if (active == null) {
                if (queue.size() == 0)
                    break;

                active = queue.removeFirst();
                active.flip();
            }

            int rc = channel.write(active);
            bytesWritten += rc;

            if (!active.hasRemaining()) {
                bufferFactory.returnBuffer(active);
                active = null;
            }

            if (rc == 0)
                break;
        }

        return bytesWritten;
    }

    // -- not needed by framework

    public synchronized boolean enqueue(ByteBuffer byteBuffer) {

        if (byteBuffer.remaining() == 0) {
            return false;
        }

        if (queue.size() > 0) {
            ByteBuffer tail = queue.getLast();
            if (tail.hasRemaining()) {
                topUpBuffer(tail, byteBuffer);
            }
        }

        while (byteBuffer.hasRemaining()) {
            ByteBuffer newBuf = bufferFactory.newBuffer();
            topUpBuffer(newBuf, byteBuffer);
            queue.addLast(newBuf);
        }

        facade.modifyInterestOps(SelectionKey.OP_WRITE, 0);

        return true;

    }

    private void topUpBuffer(ByteBuffer dest, ByteBuffer src) {
        if (src.remaining() <= dest.remaining()) {
            dest.put(src);
        }
        else {
            // TODO: make this more efficient with buffer slice?
            while (dest.hasRemaining()) {
                dest.put(src.get());
            }
        }
    }
}
