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
import java.io.IOException;
import java.io.InputStream;

public class DecimalIn extends AbstractTypedIn {

    private int numRead = 0;
    private int breakAfter = 15;
    private int ratio = 4; // number of bytes of output per byte of input

    public DecimalIn(InputStream inputStream) {
        super(inputStream);
    }

    protected void fill() throws IOException {

        buf = new int[ratio];
        int datum = in.read();
        this.numRead++;
        if (datum == -1) {
            // Let read() handle end of stream.
            throw new EOFException();
        }

        String dec = Integer.toString(datum);
        if (datum < 10) { // Add two leadataInputStreamg zeros.
            dec = "00" + dec;
        } else if (datum < 100) { // Add leadataInputStreamg zero.
            dec = '0' + dec;
        }
        for (int i = 0; i < dec.length(); i++) {
            buf[i] = dec.charAt(i);
        }
        if (numRead < breakAfter) {
            buf[buf.length - 1] = ' ';
        } else {
            buf[buf.length - 1] = '\n';
            numRead = 0;
        }
    }

    public int available() throws IOException {
        return (buf.length - index) + ratio * in.available();
    }
}
