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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class SafeBufferedCharIn extends BufferedReader {
    private boolean lookingForLineFeed = false;

    public SafeBufferedCharIn(Reader reader) {
        this(reader, 1024);
    }

    public SafeBufferedCharIn(Reader reader, int bufferSize) {
        super(reader, bufferSize);
    }

    @Override
    public String readLine() throws IOException {
        StringBuffer sb = new StringBuffer("");
        while (true) {
            int c = this.read();
            if (c == -1) { // end of stream
                return null;
            }
            else if (c == '\n') {
                if (lookingForLineFeed) {
                    lookingForLineFeed = false;
                    continue;
                }
                else {
                    return sb.toString();
                }
            }
            else if (c == '\r') {
                lookingForLineFeed = true;
                return sb.toString();
            }
            else {
                lookingForLineFeed = false;
                sb.append((char) c);
            }
        }
    }

}
