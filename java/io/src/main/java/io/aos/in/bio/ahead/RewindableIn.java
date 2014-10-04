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
package io.aos.in.bio.ahead;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * RewindableInputStream is a specialization of the PushbackInputStream that
 * maintains an internal buffer of read bytes that a user can rewind (unread)
 * back into the stream without having to do their own buffer management. The
 * rewind buffer grows dynamically
 * </p>
 */
public class RewindableIn extends DynamicPushbackIn {
    private static final int INITIAL_CAPACITY = 32;
    private byte[] buffer;
    private int position;
    private final int scale;

    public RewindableIn(InputStream inputStream) {
        this(inputStream, INITIAL_CAPACITY);
    }

    public RewindableIn(InputStream inputStream, int capacity) {
        super(inputStream);
        grow(capacity);
        this.scale = capacity;
    }

    @Override
    public int read() throws IOException {
        int i = super.read();
        if (i != -1) {
            if (position >= buffer.length)
                grow(scale);
            buffer[position++] = (byte) i;
        }
        return i;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int r = super.read(b, off, len);
        if (r != -1) {
            if (position + r >= buffer.length) {
                grow(Math.max(position + r, scale));
            }
            System.arraycopy(b, off, buffer, position, r);
            position = position + r;
        }
        return r;
    }

    @Override
    public long skip(long n) throws IOException {
        return super.skip(n);
    }

    public int position() {
        return position;
    }

    private void grow(int capacity) {
        if (buffer == null) {
            buffer = new byte[capacity];
            return;
        } else {
            byte[] buf = new byte[buffer.length + capacity];
            System.arraycopy(buffer, 0, buf, 0, buffer.length);
            buffer = buf;
        }
    }

    private void shrink(int len) {
        if (buffer == null) {
            return;
        }
        byte[] buf = new byte[buffer.length - len];
        System.arraycopy(buffer, 0, buf, 0, buf.length);
        position = buffer.length - len;
        buffer = buf;
    }

    public void rewind() throws IOException {
        if (buffer.length == 0)
            return;
        unread(buffer, 0, position);
        shrink(buffer.length);
    }

    public void rewind(int offset, int len) throws IOException {
        if (buffer.length == 0)
            return;
        if (offset > buffer.length)
            throw new ArrayIndexOutOfBoundsException(offset);
        unread(buffer, offset, len);
        shrink(len);
    }

    public void rewind(int len) throws IOException {
        if (buffer.length == 0)
            return;
        rewind(buffer.length - len, len);
    }

}
