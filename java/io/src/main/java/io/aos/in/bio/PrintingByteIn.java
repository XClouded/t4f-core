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

public class PrintingByteIn extends FilterInputStream {

    public PrintingByteIn(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int read() throws IOException {
        int b = in.read();
        // printing, ASCII characters
        if (b >= 32 && b <= 126) {
            return b;
        }
        else {
            if (b == '\n' || b == '\r' || b == '\t') {
                return b;
            }
            else {
                return '?';
            }
        }
        // nonprinting characters
    }

    @Override
    public int read(byte[] data, int offset, int length) throws IOException {
        int result = in.read(data, offset, length);
        for (int i = offset; i < offset + result; i++) {
            // Do nothing with the printing characters.
            if (data[i] == '\n' || data[i] == '\r' || data[i] == '\t' || data[i] == -1) {
                // print nothing
            }
            // nonprinting characters
            else {
                if (data[i] < 32 || data[i] > 126) {
                    data[i] = (byte) '?';
                }
            }
        }
        return result;
    }

}
