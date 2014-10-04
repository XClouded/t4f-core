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

public class FiniteByteIn extends FilterInputStream {
    private int limit = 8192;
    private int bytesRead = 0;

    public FiniteByteIn(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public FiniteByteIn(InputStream inputStream, int limit) {
        super(inputStream);
        this.limit = limit;
    }

    @Override
    public int read() throws IOException {
        if (bytesRead >= limit) {
            return -1;
        }
        int c = in.read();
        bytesRead++;
        return c;
    }

    @Override
    public int read(byte[] data) throws IOException {
        return this.read(data, 0, data.length);
    }

    @Override
    public int read(byte[] data, int offset, int length) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        }
        else if ((offset < 0) || (offset > data.length) || (length < 0) || ((offset + length) > data.length)
                || ((offset + length) < 0)) {
            throw new IndexOutOfBoundsException();
        }
        else if (length == 0) {
            return 0;
        }

        if (bytesRead >= limit)
            return -1;
        else if (bytesRead + length > limit) {
            int numToRead = bytesRead + length - limit;
            int numRead = in.read(data, offset, numToRead);
            if (numRead == -1)
                return -1;
            bytesRead += numRead;
            return numRead;
        }
        else { // will not exceed limit
            int numRead = in.read(data, offset, length);
            if (numRead == -1)
                return -1;
            bytesRead += numRead;
            return numRead;
        }

    }

    @Override
    public int available() throws IOException {
        if (bytesRead >= limit) {
            return 1;
        }
        else {
            return in.available();
        }
    }

}
