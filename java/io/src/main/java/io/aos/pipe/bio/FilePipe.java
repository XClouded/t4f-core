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
package io.aos.pipe.bio;

import io.aos.in.bio.typed.DecimalIn;
import io.aos.in.bio.typed.DoubleIn;
import io.aos.in.bio.typed.FloatIn;
import io.aos.in.bio.typed.HexIn;
import io.aos.in.bio.typed.IntIn;
import io.aos.in.bio.typed.LittleEndianDoubleIn;
import io.aos.in.bio.typed.LittleEndianFloatIn;
import io.aos.in.bio.typed.LittleEndianIntIn;
import io.aos.in.bio.typed.LittleEndianLongIn;
import io.aos.in.bio.typed.LittleEndianShortIn;
import io.aos.in.bio.typed.LongIn;
import io.aos.in.bio.typed.ShortIn;
import io.aos.in.bio.typed.TypedByteIn;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class FilePipe {
    public static final int ASC = 0;
    public static final int DEC = 1;
    public static final int HEX = 2;
    public static final int SHORT = 3;
    public static final int INT = 4;
    public static final int LONG = 5;
    public static final int FLOAT = 6;
    public static final int DOUBLE = 7;

    public static void main(String... args) {

        if (args.length < 1) {
            System.err.println("Usage: java FileDumper5 [-ahdsilfx] [-little] [-gzip|-deflated] "
                    + "[-password password] file1...");
        }

        boolean bigEndian = true;
        int firstFile = 0;
        int mode = ASC;
        boolean deflated = false;
        boolean gzipped = false;
        String password = null;

        // Process command-line switches.
        for (firstFile = 0; firstFile < args.length; firstFile++) {
            if (!args[firstFile].startsWith("-"))
                break;
            if (args[firstFile].equals("-h"))
                mode = HEX;
            else if (args[firstFile].equals("-d"))
                mode = DEC;
            else if (args[firstFile].equals("-s"))
                mode = SHORT;
            else if (args[firstFile].equals("-i"))
                mode = INT;
            else if (args[firstFile].equals("-l"))
                mode = LONG;
            else if (args[firstFile].equals("-f"))
                mode = FLOAT;
            else if (args[firstFile].equals("-x"))
                mode = DOUBLE;
            else if (args[firstFile].equals("-little"))
                bigEndian = false;
            else if (args[firstFile].equals("-deflated") && !gzipped)
                deflated = true;
            else if (args[firstFile].equals("-gzip") && !deflated)
                gzipped = true;
            else if (args[firstFile].equals("-password")) {
                password = args[firstFile + 1];
                firstFile++;
            }
        }

        for (int i = firstFile; i < args.length; i++) {
            try {
                InputStream inputStream = new FileInputStream(args[i]);
                drain(inputStream, System.out, mode, bigEndian, deflated, gzipped, password);

                if (i < args.length - 1) { // more files to dump
                    System.out.println();
                    System.out.println("--------------------------------------");
                    System.out.println();
                }
            }
            catch (IOException ex) {
                System.err.println(ex);
                ex.printStackTrace();
            }
        }
    }

    public static void drain(InputStream inputStream, OutputStream outputStream, int mode, boolean bigEndian,
            boolean deflated, boolean gzipped, String password) throws IOException {

        // The reference variable inputStreammay point to several different
        // objects
        // withinputStreamthe space of the next few lines.
        if (password != null && password.length() > 0) {
            // Create a key.
            try {
                byte[] desKeyData = password.getBytes();
                DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey desKey = keyFactory.generateSecret(desKeySpec);

                // Use Data Encryption Standard.
                Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");
                des.init(Cipher.DECRYPT_MODE, desKey);

                inputStream = new CipherInputStream(inputStream, des);
            }
            catch (GeneralSecurityException ex) {
                throw new IOException(ex.getMessage());
            }
        }

        if (deflated) {
            inputStream = new InflaterInputStream(inputStream);
        }
        else if (gzipped) {
            inputStream = new GZIPInputStream(inputStream);
        }

        // could really pass to FileDumper3 at this point
        if (bigEndian) {
            DataInputStream dinputStream = new DataInputStream(inputStream);
            switch (mode) {
            case HEX:
                inputStream = new HexIn(inputStream);
                break;
            case DEC:
                inputStream = new DecimalIn(inputStream);
                break;
            case INT:
                inputStream = new IntIn(dinputStream);
                break;
            case SHORT:
                inputStream = new ShortIn(dinputStream);
                break;
            case LONG:
                inputStream = new LongIn(dinputStream);
                break;
            case DOUBLE:
                inputStream = new DoubleIn(dinputStream);
                break;
            case FLOAT:
                inputStream = new FloatIn(dinputStream);
                break;
            default:
            }
        }
        else {
            TypedByteIn linputStream = new TypedByteIn(inputStream);
            switch (mode) {
            case HEX:
                inputStream = new HexIn(inputStream);
                break;
            case DEC:
                inputStream = new DecimalIn(inputStream);
                break;
            case INT:
                inputStream = new LittleEndianIntIn(linputStream);
                break;
            case SHORT:
                inputStream = new LittleEndianShortIn(linputStream);
                break;
            case LONG:
                inputStream = new LittleEndianLongIn(linputStream);
                break;
            case DOUBLE:
                inputStream = new LittleEndianDoubleIn(linputStream);
                break;
            case FLOAT:
                inputStream = new LittleEndianFloatIn(linputStream);
                break;
            default:
            }
        }
        for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
            outputStream.write(c);
        }
        inputStream.close();
    }

    private static void transport(InputStream inputStream, OutputStream outputStream, int mode, boolean bigEndian,
            boolean deflated, boolean gzipped) throws IOException {

        // The reference variable inputStreammay point to several different
        // objects
        // withinputStreamthe space of the next few lines. We can attach
        // more filters here to do decompression, decryption, and more.
        if (deflated) {
            inputStream = new InflaterInputStream(inputStream);
        }
        else if (gzipped) {
            inputStream = new GZIPInputStream(inputStream);
        }

        if (bigEndian) {
            
            DataInputStream dinputStream = new DataInputStream(inputStream);

            switch (mode) {
            case HEX:
                inputStream = new HexIn(inputStream);
                break;
            case DEC:
                inputStream = new DecimalIn(inputStream);
                break;
            case INT:
                inputStream = new IntIn(dinputStream);
                break;
            case SHORT:
                inputStream = new ShortIn(dinputStream);
                break;
            case LONG:
                inputStream = new LongIn(dinputStream);
                break;
            case DOUBLE:
                inputStream = new DoubleIn(dinputStream);
                break;
            case FLOAT:
                inputStream = new FloatIn(dinputStream);
                break;
            default:
            }
        
        }
        
        else {
        
            TypedByteIn linputStream = new TypedByteIn(inputStream);
            
            switch (mode) {
            case HEX:
                inputStream = new HexIn(inputStream);
                break;
            case DEC:
                inputStream = new DecimalIn(inputStream);
                break;
            case INT:
                inputStream = new LittleEndianIntIn(linputStream);
                break;
            case SHORT:
                inputStream = new LittleEndianShortIn(linputStream);
                break;
            case LONG:
                inputStream = new LittleEndianLongIn(linputStream);
                break;
            case DOUBLE:
                inputStream = new LittleEndianDoubleIn(linputStream);
                break;
            case FLOAT:
                inputStream = new LittleEndianFloatIn(linputStream);
                break;
            default:
            }
            
        }

        BytePipe.transport(inputStream, outputStream);

        inputStream.close();

    }

}
