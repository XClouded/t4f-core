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

import java.io.*;
import java.nio.charset.*;
import java.nio.*;
import java.nio.channels.*;

public class CharsetConverterTest {

    public static void main(String... args) {

        if (args.length != 2) {
            System.err.println("Usage: java Recoder inputEncoding outputEncoding <inFile >outFile");
            return;
        }

        try {
            Charset inputEncoding = Charset.forName(args[0]);
            Charset outputEncoding = Charset.forName(args[1]);
            convert(inputEncoding, outputEncoding, System.in, System.out);
        }
        catch (UnsupportedCharsetException ex) {
            System.err.println(ex.getCharsetName() + " is not supported by this VM.");
        }
        catch (IllegalCharsetNameException ex) {
            System.err.println("Usage: java Recoder inputEncoding outputEncoding <inFile >outFile");
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void convert(Charset inputEncoding, Charset outputEncoding, InputStream inputStream,
            OutputStream outStream) throws IOException {

        ReadableByteChannel readableInputStream = Channels.newChannel(inputStream);
        WritableByteChannel outputStream = Channels.newChannel(outStream);

        for (ByteBuffer inBuffer = ByteBuffer.allocate(4096); readableInputStream.read(inBuffer) != -1; inBuffer
                .clear()) {
            inBuffer.flip();
            CharBuffer cBuffer = inputEncoding.decode(inBuffer);
            ByteBuffer outBuffer = outputEncoding.encode(cBuffer);
            while (outBuffer.hasRemaining()) {
                outputStream.write(outBuffer);
            }
        }
    }
}
