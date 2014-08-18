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

import io.aos.pipe.bio.BytePipe;

import java.io.FileInputStream;
import java.io.IOException;

public class BioDumper3Test {

    public static final int ASC = 0;
    public static final int DEC = 1;
    public static final int HEX = 2;

    public static void main(String... args) {

        if (args.length < 1) {
            System.err.println("Usage: java FileDumper [-ahd] file1 file2...");
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
                if (mode == ASC)
                    dumpAscii(args[i]);
                else if (mode == HEX)
                    dumpHex(args[i]);
                else if (mode == DEC)
                    dumpDecimal(args[i]);
            }
            catch (IOException ex) {
                System.err.println("Error reading from " + args[i] + ": " + ex.getMessage());
            }
            if (i < args.length - 1) { // more files to dump
                System.out.println("\r\n--------------------------------------\r\n");
            }
        }
    }

    public static void dumpAscii(String filename) throws IOException {

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filename);
            BytePipe.transport(inputStream, System.out);
        }
        finally {
            if (inputStream != null)
                inputStream.close();
        }
    }

    public static void dumpDecimal(String filename) throws IOException {

        FileInputStream fis = null;
        byte[] buffer = new byte[16];
        boolean end = false;

        try {
            fis = new FileInputStream(filename);
            while (!end) {
                int bytesRead = 0;
                while (bytesRead < buffer.length) {
                    int r = fis.read(buffer, bytesRead, buffer.length - bytesRead);
                    if (r == -1) {
                        end = true;
                        break;
                    }
                    bytesRead += r;
                }
                for (int i = 0; i < bytesRead; i++) {
                    int dec = buffer[i];
                    if (dec < 0)
                        dec = 256 + dec;
                    if (dec < 10)
                        System.out.print("00" + dec + " ");
                    else if (dec < 100)
                        System.out.print("0" + dec + " ");
                    else
                        System.out.print(dec + " ");
                }
                System.out.println();
            }
        }
        finally {
            if (fis != null)
                fis.close();
        }
    }

    public static void dumpHex(String filename) throws IOException {

        FileInputStream fis = null;
        byte[] buffer = new byte[24];
        boolean end = false;

        try {
            fis = new FileInputStream(filename);
            while (!end) {
                int bytesRead = 0;
                while (bytesRead < buffer.length) {
                    int r = fis.read(buffer, bytesRead, buffer.length - bytesRead);
                    if (r == -1) {
                        end = true;
                        break;
                    }
                    bytesRead += r;
                }
                for (int i = 0; i < bytesRead; i++) {
                    int hex = buffer[i];
                    if (hex < 0)
                        hex = 256 + hex;
                    if (hex >= 16)
                        System.out.print(Integer.toHexString(hex) + " ");
                    else
                        System.out.print("0" + Integer.toHexString(hex) + " ");
                }
                System.out.println();
            }
        }
        finally {
            if (fis != null)
                fis.close();
        }
    }
}
