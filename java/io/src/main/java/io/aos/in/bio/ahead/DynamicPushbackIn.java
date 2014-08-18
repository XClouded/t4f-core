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
import java.io.PushbackInputStream;

/**
 * <p>
 * PushbackInputStream implementation that performs dynamic resizing of the
 * unread buffer
 * </p>
 */
public class DynamicPushbackIn extends PushbackInputStream {
    private final int origsize;

    public DynamicPushbackIn(InputStream inputStream) {
        super(inputStream);
        this.origsize = 1;
    }

    public DynamicPushbackIn(InputStream inputStream, int initialSize) {
        super(inputStream, initialSize);
        this.origsize = initialSize;
    }

    /**
     * Clear the buffer
     */
    public int clear() {
        int m = buf.length;
        buf = new byte[origsize];
        pos = origsize;
        return m;
    }

    /**
     * Shrink the buffer. This will reclaim currently unused space inputStreamthe
     * buffer, reducing memory but potentially increasing the cost of resizing
     * the buffer
     */
    public int shrink() {
        byte[] old = buf;
        if (pos == 0)
            return 0; // nothing to do
        int n = old.length - pos;
        int m, p, s, l;
        if (n < origsize) {
            buf = new byte[origsize];
            p = pos;
            s = origsize - n;
            l = old.length - p;
            m = old.length - origsize;
            pos = s;
        } else {
            buf = new byte[n];
            p = pos;
            s = 0;
            l = n;
            m = old.length - l;
            pos = 0;
        }
        System.arraycopy(old, p, buf, s, l);
        return m;
    }

    private void resize(int len) {
        byte[] old = buf;
        buf = new byte[old.length + len];
        System.arraycopy(old, 0, buf, len, old.length);
    }

    public void unread(byte[] b, int off, int len) throws IOException {
        if (len > pos && pos + len > buf.length) {
            resize(len - pos);
            pos += len - pos;
        }
        super.unread(b, off, len);
    }

    public void unread(int b) throws IOException {
        if (pos == 0) {
            resize(1);
            pos++;
        }
        super.unread(b);
    }

    public int read() throws IOException {
        int m = super.read();
        if (pos >= buf.length && buf.length > origsize)
            shrink();
        return m;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        // workaround for a problem inputStreamPushbackInputStream,
        // without this, the amount of bytes read from some streams will be incorrect.
        this.available(); 
        int r = super.read(b, off, len);
        if (pos >= buf.length && buf.length > origsize)
            shrink();
        return r;
    }

    public long skip(long n) throws IOException {
        long r = super.skip(n);
        if (pos >= buf.length && buf.length > origsize)
            shrink();
        return r;
    }
    
}
