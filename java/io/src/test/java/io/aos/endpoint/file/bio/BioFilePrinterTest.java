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
package io.aos.endpoint.file.bio;

import io.aos.out.bio.PrintingByteOut;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class BioFilePrinterTest {

    @Test
    public void test() throws IOException {

        InputStream inputStream = new FileInputStream("pom.xml");
        OutputStream outputStream = System.out;

        // Here's where the output stream is chained to the ASCII output stream.
        PrintingByteOut pout = new PrintingByteOut(outputStream);
        for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
            pout.write(c);
        }
        pout.close();
        outputStream.close();
        inputStream.close();
    
    }
    
}
