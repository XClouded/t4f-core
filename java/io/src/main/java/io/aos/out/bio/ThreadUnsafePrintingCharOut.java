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
package io.aos.out.bio;

import java.io.IOException;
import java.io.Writer;

public class ThreadUnsafePrintingCharOut extends Writer {
    private final static int CAPACITY = 8192;
    private char[] buffer = new char[CAPACITY];
    private int position = 0;
    private Writer writer;
    private boolean closed = false;

    public ThreadUnsafePrintingCharOut(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void write(char[] text, int offset, int length) throws IOException {
        checkClosed();
        while (length > 0) {
            int n = Math.min(CAPACITY - position, length);
            System.arraycopy(text, offset, buffer, position, n);
            position += n;
            offset += n;
            length -= n;
            if (position >= CAPACITY) {
                flushInternal();
            }
        }
    }

    @Override
    public void write(String s) throws IOException {
        write(s, 0, s.length());
    }

    @Override
    public void write(String s, int offset, int length) throws IOException {
        checkClosed();
        while (length > 0) {
            int n = Math.min(CAPACITY - position, length);
            s.getChars(offset, offset + n, buffer, position);
            position += n;
            offset += n;
            length -= n;
            if (position >= CAPACITY) {
                flushInternal();
            }
        }
    }

    @Override
    public void write(int c) throws IOException {
        checkClosed();
        if (position >= CAPACITY)
            flushInternal();
        buffer[position] = (char) c;
        position++;
    }

    @Override
    public void flush() throws IOException {
        flushInternal();
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        closed = true;
        this.flush();
        writer.close();
    }

    private void flushInternal() throws IOException {
        if (position != 0) {
            writer.write(buffer, 0, position);
            position = 0;
        }
    }

    private void checkClosed() throws IOException {
        if (closed)
            throw new IOException("Writer is closed");
    }
}
