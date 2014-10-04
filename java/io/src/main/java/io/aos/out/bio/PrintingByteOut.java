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

public class PrintingByteOut extends FilterOutputStream {

    public PrintingByteOut(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void write(int b) throws IOException {
        // Carriage return, linefeed, and tab.
        if (b == '\n' || b == '\r' || b == '\t') {
            out.write(b);
        }
        // Non-printing characters.
        else if (b < 32 || b > 126) {
            out.write('?');
        }
        // Printing, ASCII characters.
        else {
            out.write(b);
        }
    }

    @Override
    public void write(byte[] data, int offset, int length) throws IOException {
        for (int i = offset; i < offset + length; i++) {
            this.write(data[i]);
        }
    }

}
