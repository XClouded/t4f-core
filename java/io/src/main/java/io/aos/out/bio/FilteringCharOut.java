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

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class FilteringCharOut extends FilterWriter {

    public FilteringCharOut(Writer writer) {
        super(writer);
    }

    public void write(char[] text, int offset, int length) throws IOException {
        for (int i = offset; i < offset + length; i++) {
            this.write(text[i]);
        }
    }

    public void write(String s, int offset, int length) throws IOException {
        for (int i = offset; i < offset + length; i++) {
            this.write(s.charAt(i));
        }
    }

    public void write(int c) throws IOException {
        // We have to escape the backslashes below.
        if (c == '\\') {
            out.write("\\u005C");
        }
        else if (c < 128) {
            out.write(c);
        }
        else {
            String s = Integer.toHexString(c);
            // Pad with leading zeroes if necessary.
            if (c < 256)
                s = "00" + s;
            else if (c < 4096)
                s = "0" + s;
            out.write("\\u");
            out.write(s);
        }
    }

}
