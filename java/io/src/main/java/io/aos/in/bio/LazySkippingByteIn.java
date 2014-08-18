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
package io.aos.in.bio;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * {@link FilterInputStream} implementation which skip 
 * the given bytes as late as possible.
 * </p>
 */
public class LazySkippingByteIn extends FilterInputStream {
    private long skipBytes;
    private boolean skipped = false;

    /**
     * Construct the {@link LazySkippingByteIn}
     * 
     * @param in {@link InputStream} to wrap
     * @param skipBytes bytes to skip
     */
    public LazySkippingByteIn(InputStream inputStream, long skipBytes) {
        super(inputStream);
        this.skipBytes = skipBytes;
    }

    @Override
    public int read() throws IOException {
        skipIfNeeded();
        return super.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        skipIfNeeded();
        return super.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        skipIfNeeded();
        return super.read(b);
    }

    @Override
    public int available() throws IOException {
        skipIfNeeded();
        return super.available();
    }

    @Override
    public synchronized void mark(int readlimit) {
        throw new UnsupportedOperationException("mark is not supported.");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public long skip(long n) throws IOException {
        skipIfNeeded();
        return super.skip(n);
    }

    /**
     * Check if the bytes are skipped already. If not do now
     * 
     * @throws IOException
     */
    private void skipIfNeeded() throws IOException {
        if (skipped == false) {
            super.skip(skipBytes);
            skipped = true;
        }
    }

}
