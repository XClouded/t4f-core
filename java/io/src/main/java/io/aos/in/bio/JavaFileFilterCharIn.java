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

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class JavaFileFilterCharIn extends FilterReader {
    private int buffer = -1;
    private boolean endOfStream = false;

    public JavaFileFilterCharIn(Reader reader) {
        super(reader);
    }

    public int read() throws IOException {

        if (this.buffer != -1) {
            int c = this.buffer;
            this.buffer = -1;
            return c;
        }

        int c = in.read();
        if (c != '\\')
            return c;

        int next = in.read();
        if (next != 'u') { // This is not a Unicode escape
            this.buffer = next;
            return c;
        }

        // Read next 4 hex digits
        // If the next four chars do not make a valid hex digit
        // this is not a valid .java file.
        StringBuffer sb = new StringBuffer();
        sb.append((char) in.read());
        sb.append((char) in.read());
        sb.append((char) in.read());
        sb.append((char) in.read());
        String hex = sb.toString();
        try {
            return Integer.valueOf(hex, 16).intValue();
        }
        catch (NumberFormatException ex) {
            throw new IOException("Bad Unicode escape: \\u" + hex);
        }
    }

    public int read(char[] text, int offset, int length) throws IOException {

        if (endOfStream) {
            return -1;
        }
        int numRead = 0;

        for (int i = offset; i < offset + length; i++) {
            int temp = this.read();
            if (temp == -1) {
                this.endOfStream = true;
                break;
            }
            text[i] = (char) temp;
            numRead++;
        }
        return numRead;

    }

    public long skip(long n) throws IOException {
        char[] c = new char[(int) n];
        int numSkipped = this.read(c);
        return numSkipped;
    }

}
