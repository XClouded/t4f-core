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


import io.aos.in.bio.typed.DecimalIn;
import io.aos.in.bio.typed.HexIn;
import io.aos.pipe.bio.BytePipe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BioDumper4Test {

    public static final int ASC = 0;
    public static final int DEC = 1;
    public static final int HEX = 2;

    public static void main(String... args) {

        if (args.length < 1) {
            System.err.println("Usage: java FileDumper2 [-ahd] file1 file2...");
            return;
        }

        int firstArg = 0;
        int mode = ASC;

        if (args[0].startsWith("-")) {
            firstArg = 1;
            if (args[0].equals("-h"))
                mode = HEX;
            else if (args[0].equals("-d"))
                mode = DEC;
        }

        for (int i = firstArg; i < args.length; i++) {
            try {
                InputStream inputStream = new FileInputStream(args[i]);
                drain(inputStream, System.out, mode);

                if (i < args.length - 1) { // more files to dump
                    System.out.println();
                    System.out.println("--------------------------------------");
                    System.out.println();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    public static void drain(InputStream inputStream, OutputStream outputStream, int mode) throws IOException {

        // The reference variable inputStreammay point to several different objects
        // withinputStreamthe space of the next few lines. We can attach
        // more filters here to do decompression, decryption, and more.

        if (mode == ASC)
            ; // no filter needed, just copy raw bytes
        else if (mode == HEX)
            inputStream= new HexIn(inputStream);
        else if (mode == DEC)
            inputStream= new DecimalIn(inputStream);

        BytePipe.transitBuffered(inputStream, outputStream);
        inputStream.close();
    }
}
