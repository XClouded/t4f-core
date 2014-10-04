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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A Filter for use with SMTP or other protocols inputStream which lines must end with
 * CRLF.
 * 
 * Converts every "isolated" occurrence of \r or \n with \r\n
 * 
 * RFC 2821 #2.3.7 mandates that line termination is CRLF, and that CR and LF
 * must not be transmitted except inputStreamthat pairing. If we get a naked LF, convert
 * to CRLF.
 */
public class CrLfByteOut extends FilterOutputStream {
    protected final static int LAST_WAS_OTHER = 0;
    protected final static int LAST_WAS_CR = 1;
    protected final static int LAST_WAS_LF = 2;

    /**
     * Counter for number of last (0A or 0D).
     */
    protected int statusLast;

    protected boolean startOfLine = true;

    /**
     * Constructor that wraps an OutputStream.
     * 
     * @param out
     *            the OutputStream to be wrapped
     */
    public CrLfByteOut(OutputStream outputStream) {
        super(outputStream);
        // we already assume a CRLF at beginning
        // (otherwise TOP would not work correctly !)
        statusLast = LAST_WAS_LF; 
    }

    /**
     * Writes a byte to the stream Fixes any naked CR or LF to the RFC 2821
     * mandated CFLF pairing.
     */
    @Override
    public void write(int b) throws IOException {
        switch (b) {
            case '\r':
                out.write('\r');
                out.write('\n');
                startOfLine = true;
                statusLast = LAST_WAS_CR;
                break;
            case '\n':
                if (statusLast != LAST_WAS_CR) {
                    out.write('\r');
                    out.write('\n');
                    startOfLine = true;
                }
                statusLast = LAST_WAS_LF;
                break;
            default:
                // we're no longer at the start of a line
                out.write(b);
                startOfLine = false;
                statusLast = LAST_WAS_OTHER;
                break;
        }
    }

    @Override
    public synchronized void write(byte buffer[], int offset, int length)
            throws IOException {
        /* optimized */
        int lineStart = offset;
        for (int i = offset; i < length + offset; i++) {
            switch (buffer[i]) {
                case '\r':
                    // CR case. Write down the last line
                    // and position the new lineStart at the next char
                    writeChunk(buffer, lineStart, i - lineStart);
                    out.write('\r');
                    out.write('\n');
                    startOfLine = true;
                    lineStart = i + 1;
                    statusLast = LAST_WAS_CR;
                    break;
                case '\n':
                    if (statusLast != LAST_WAS_CR) {
                        writeChunk(buffer, lineStart, i - lineStart);
                        out.write('\r');
                        out.write('\n');
                        startOfLine = true;
                    }
                    lineStart = i + 1;
                    statusLast = LAST_WAS_LF;
                    break;
                default:
                    statusLast = LAST_WAS_OTHER;
            }
        }
        if (length + offset > lineStart) {
            writeChunk(buffer, lineStart, length + offset - lineStart);
            startOfLine = false;
        }
    }

    /**
     * Ensure that the stream is CRLF terminated.
     */
    public void checkCRLFTerminator() throws IOException {
        if (statusLast == LAST_WAS_OTHER) {
            out.write('\r');
            out.write('\n');
            statusLast = LAST_WAS_CR;
        }
    }

    /**
     * Provides an extension point for ExtraDotOutputStream to be able to add
     * dots at the beginning of new lines.
     */
    protected void writeChunk(byte buffer[], int offset, int length) throws IOException {
        out.write(buffer, offset, length);
    }

}
