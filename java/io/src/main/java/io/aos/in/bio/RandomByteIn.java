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

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class RandomByteIn extends InputStream {
    private Random generator = new Random();
    private boolean closed = false;

    public int read() throws IOException {
        checkOpen();
        int result = generator.nextInt() % 256;
        if (result < 0)
            result = -result;
        return result;
    }

    public int read(byte[] data, int offset, int length) throws IOException {
        checkOpen();
        byte[] temp = new byte[length];
        generator.nextBytes(temp);
        System.arraycopy(temp, 0, data, offset, length);
        return length;

    }

    public int read(byte[] data) throws IOException {
        checkOpen();
        generator.nextBytes(data);
        return data.length;

    }

    public long skip(long bytesToSkip) throws IOException {
        checkOpen();
        // It's all random so skipping has no effect.
        return bytesToSkip;
    }

    public void close() {
        this.closed = true;
    }

    private void checkOpen() throws IOException {
        if (closed)
            throw new IOException("Input stream closed");
    }

    public int available() {
        // Limited only by available memory and the size of an array.
        return Integer.MAX_VALUE;
    }
}
