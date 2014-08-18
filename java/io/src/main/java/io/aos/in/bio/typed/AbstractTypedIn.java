/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regardataInputStreamg copyright ownership.  The AOS licenses this file   *
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
package io.aos.in.bio.typed;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractTypedIn extends FilterInputStream {
    // This is really an array of unsigned bytes.
    protected int[] buf = new int[0];
    protected int index = 0;

    public AbstractTypedIn(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int read() throws IOException {
        int result;
        if (index < buf.length) {
            result = buf[index];
            index++;
        } // end if
        else {
            try {
                this.fill();
                // fill is required to put at least one byte
                // in the buffer or throw an EOF or IOException.
                result = buf[0];
                index = 1;
            }
            catch (EOFException ex) {
                result = -1;
            }
        } // end else
        return result;
    }

    @Override
    public int read(byte[] data, int offset, int length) throws IOException {

        if (data == null) {
            throw new NullPointerException();
        }
        else if ((offset < 0) || (offset > data.length) || (length < 0) || ((offset + length) > data.length)
                || ((offset + length) < 0)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        else if (length == 0) {
            return 0;
        }

        // Check for end of stream.
        int datum = this.read();
        if (datum == -1) {
            return -1;
        }

        data[offset] = (byte) datum;

        int bytesRead = 1;
        try {
            for (; bytesRead < length; bytesRead++) {

                datum = this.read();

                // In case of end of stream, return as much as we've got,
                // then wait for the next call to read to return -1.
                if (datum == -1)
                    break;
                data[offset + bytesRead] = (byte) datum;
            }
        }
        catch (IOException ex) {
            // Return what's already in the data array.
        }
        return bytesRead;

    }

    @Override
    public int available() throws IOException {
        return buf.length - index;
    }

    @Override
    public long skip(long bytesToSkip) throws IOException {

        long bytesSkipped = 0;
        for (; bytesSkipped < bytesToSkip; bytesSkipped++) {
            int c = this.read();
            if (c == -1)
                break;
        }
        return bytesSkipped;
    }

    @Override
    public void mark(int readlimit) {
    }

    @Override
    public void reset() throws IOException {
        throw new IOException("marking not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    protected abstract void fill() throws IOException;

}
