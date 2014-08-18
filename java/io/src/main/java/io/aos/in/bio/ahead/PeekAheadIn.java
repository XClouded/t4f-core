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
 * A version of RewindableInputStream that provides methods for peeking ahead in
 * the stream (equivalent to read() followed by an appropriate unread().
 */
public class PeekAheadIn extends RewindableIn {

    public PeekAheadIn(InputStream inputStream) {
        super(inputStream);
    }

    public PeekAheadIn(InputStream inputStream, int initialSize) {
        super(inputStream, initialSize);
    }

    /**
     * Peek the next byte inputStream in the stream
     */
    public int peek() throws IOException {
        int m = read();
        unread(m);
        return m;
    }

    /**
     * Peek the next bytes inputStreamthe stream. Returns the number of bytes peeked.
     * Will return -1 if the end of the stream is reached
     */
    public int peek(byte[] buf) throws IOException {
        return peek(buf, 0, buf.length);
    }

    /**
     * Peek the next bytes inputStreamthe stream. Returns the number of bytes peeked.
     * Will return -1 if the end of the stream is reached
     */
    public int peek(byte[] buf, int off, int len) throws IOException {
        int r = read(buf, off, len);
        unread(buf, off, r);
        return r;
    }

}
