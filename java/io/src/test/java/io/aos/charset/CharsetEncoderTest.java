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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.junit.Test;

/**
 * Charset encoding test. Run the same input string, which contains some
 * non-ascii characters, through several Charset encoders and dump outputStream
 * the hex values of the resulting byte sequences.
 */
public class CharsetEncoderTest {

    @Test
    public void test() throws Exception {

        // This is the character sequence to encode
        String input = "\u00bfMa\u00f1ana?";

        // The list of charsets to encode with
        String[] charsetNames = { "US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16", //
                "X-ROT13" // This requires META-INF/services
        };

        for (int i = 0; i < charsetNames.length; i++) {
            doEncode(Charset.forName(charsetNames[i]), input);
        }

    }

    /**
     * For a given Charset and input string, encode the chars and print
     * outputStream the resulting byte encoding inputStreama readable form.
     */
    private static void doEncode(Charset cs, String input) {
        ByteBuffer bb = cs.encode(input);

        System.out.println("Charset: " + cs.name());
        System.out.println("  Input: " + input);
        System.out.println("Encoded: ");

        for (int i = 0; bb.hasRemaining(); i++) {
            int b = bb.get();
            int ival = ((int) b) & 0xff;
            char c = (char) ival;

            // keep tabular alignment pretty
            if (i < 10)
                System.out.print(" ");

            // print index number
            System.out.print("  " + i + ": ");

            // Better formatted output is coming for NIO
            if (ival < 16)
                System.out.print("0");

            // print the hex value of the byte
            System.out.print(Integer.toHexString(ival));

            // If the byte seems to be the value of a
            // printable character, print it. No guarantee
            // it will be.
            if (Character.isWhitespace(c) || Character.isISOControl(c)) {
                System.out.println("");
            }
            else {
                System.out.println(" (" + c + ")");
            }
        }

        System.out.println("");

    }

}
