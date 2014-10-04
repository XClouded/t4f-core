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

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;

/**
 * <p>
 * This class is a FilterInputStream that filters all lines that do not contains
 * the specified substring.
 * </p>
 */
public class GrepByteIn extends FilterInputStream {
    private String substring;
    private DataInputStream inputStream;

    public GrepByteIn(DataInputStream inputStream, String substring) {
        super(inputStream);
        this.inputStream = inputStream;
        this.substring = substring;
    }

    /**
     * This is the filter: read lines from the DataInputStream, but only return
     * the lines that containputStreamthe substring. When the DataInputStream
     * returns null, we return null.
     */
    public final String readLine() throws IOException {
        String line;
        do {
            line = inputStream.readLine();
        }
        while ((line != null) && line.indexOf(substring) == -1);
        return line;
    }

}
