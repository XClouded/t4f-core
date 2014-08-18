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
package io.aos.charset;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class CharsetUnicodeTest {

    @Test
    public void test1() throws IOException {
        String encoding = System.getProperty("file.encoding", "ISO-8859-1");
        String lineSeparator = System.getProperty("line.separator", "\r\n");

        OutputStream target = System.out;
        OutputStreamWriter outputStream = null;
        try {
            outputStream = new OutputStreamWriter(target, encoding);
        } catch (UnsupportedEncodingException ex) {
            // use platform default encoding
            outputStream = new OutputStreamWriter(target);
        }

        try {
            for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
                // Skip undefined code points; these are not characters
                if (!Character.isDefined(i))
                    continue;

                char c = (char) i;
                // Surrogates are not full characters so skip them;
                // this requires Java 5
                if (Character.isHighSurrogate(c) || Character.isLowSurrogate(c))
                    continue;

                outputStream.write(i + ":\t" + c + lineSeparator);
            }
        } finally {
            outputStream.close();
        }
    }

    @Test
    public void test2() throws IOException {
        String encoding = System.getProperty("file.encoding", "ISO-8859-1");
        OutputStream target = System.out;

        BufferedWriter outputStream = null;
        try {
            outputStream = new BufferedWriter(new OutputStreamWriter(target, encoding));
        } catch (UnsupportedEncodingException ex) {
            outputStream = new BufferedWriter(new OutputStreamWriter(target));
        }

        try {
            for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
                if (!Character.isDefined(i))
                    continue;
                char c = (char) i;
                if (Character.isHighSurrogate(c) || Character.isLowSurrogate(c))
                    continue;

                outputStream.write(i + ":\t" + c);
                outputStream.newLine();
            }
        } finally {
            outputStream.close();
        }
    }

}
