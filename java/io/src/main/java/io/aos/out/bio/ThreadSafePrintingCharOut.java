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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class ThreadSafePrintingCharOut extends Writer {
    protected Writer writer;
    private boolean autoFlush = false;
    private String lineSeparator;
    private boolean closed = false;

    public ThreadSafePrintingCharOut(Writer writer, String lineSeparator) {
        this(writer, false, lineSeparator);
    }

    public ThreadSafePrintingCharOut(Writer writer, char lineSeparator) {
        this(writer, false, String.valueOf(lineSeparator));
    }

    public ThreadSafePrintingCharOut(Writer writer, boolean autoFlush, String lineSeparator) {
        super(writer);
        this.writer = writer;
        this.autoFlush = autoFlush;
        this.lineSeparator = lineSeparator;
    }

    public ThreadSafePrintingCharOut(OutputStream writer, boolean autoFlush, String encoding, String lineSeparator)
            throws UnsupportedEncodingException {
        this(new OutputStreamWriter(writer, encoding), autoFlush, lineSeparator);
    }

    @Override
    public void flush() throws IOException {

        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            writer.flush();
        }

    }

    @Override
    public void close() throws IOException {

        try {
            this.flush();
        }
        catch (IOException e) {
        }

        synchronized (lock) {
            writer.close();
            this.closed = true;
        }

    }

    @Override
    public void write(int c) throws IOException {
        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            writer.write(c);
        }
    }

    @Override
    public void write(char[] text, int offset, int length) throws IOException {
        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            writer.write(text, offset, length);
        }
    }

    @Override
    public void write(char[] text) throws IOException {
        synchronized (lock) {
            if (closed) {
                throw new IOException("Stream closed");
            }
            writer.write(text, 0, text.length);
        }
    }

    @Override
    public void write(String s, int offset, int length) throws IOException {
        synchronized (lock) {
            if (closed)
                throw new IOException("Stream closed");
            writer.write(s, offset, length);
        }
    }

    public void print(boolean b) throws IOException {
        if (b)
            this.write("true");
        else
            this.write("false");
    }

    public void println(boolean b) throws IOException {
        if (b) {
            this.write("true");
        }
        else {
            this.write("false");
        }
        this.write(lineSeparator);
        if (autoFlush) {
            writer.flush();
        }
    }

    public void print(char c) throws IOException {
        this.write(String.valueOf(c));
    }

    public void println(char c) throws IOException {
        this.write(String.valueOf(c));
        this.write(lineSeparator);
        if (autoFlush)
            writer.flush();
    }

    public void print(int i) throws IOException {
        this.write(String.valueOf(i));
    }

    public void println(int i) throws IOException {
        this.write(String.valueOf(i));
        this.write(lineSeparator);
        if (autoFlush)
            writer.flush();
    }

    public void print(long l) throws IOException {
        this.write(String.valueOf(l));
    }

    public void println(long l) throws IOException {
        this.write(String.valueOf(l));
        this.write(lineSeparator);
        if (autoFlush)
            writer.flush();
    }

    public void print(float f) throws IOException {
        this.write(String.valueOf(f));
    }

    public void println(float f) throws IOException {
        this.write(String.valueOf(f));
        this.write(lineSeparator);
        if (autoFlush) {
            writer.flush();
        }
    }

    public void print(double d) throws IOException {
        this.write(String.valueOf(d));
    }

    public void println(double d) throws IOException {
        this.write(String.valueOf(d));
        this.write(lineSeparator);
        if (autoFlush) {
            writer.flush();
        }
    }

    public void print(char[] text) throws IOException {
        this.write(text);
    }

    public void println(char[] text) throws IOException {
        this.write(text);
        this.write(lineSeparator);
        if (autoFlush) {
            writer.flush();
        }
    }

    public void print(String s) throws IOException {
        if (s == null)
            this.write("null");
        else
            this.write(s);
    }

    public void println(String s) throws IOException {
        if (s == null)
            this.write("null");
        else
            this.write(s);
        this.write(lineSeparator);
        if (autoFlush) {
            writer.flush();
        }
    }

    public void print(Object o) throws IOException {
        if (o == null)
            this.write("null");
        else
            this.write(o.toString());
    }

    public void println(Object o) throws IOException {
        if (o == null)
            this.write("null");
        else
            this.write(o.toString());
        this.write(lineSeparator);
        if (autoFlush) {
            writer.flush();
        }
    }

    public void println() throws IOException {
        this.write(lineSeparator);
        if (autoFlush) {
            writer.flush();
        }
    }

}
