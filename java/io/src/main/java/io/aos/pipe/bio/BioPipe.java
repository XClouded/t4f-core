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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.GeneralSecurityException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class BioPipe {
    public static final int ASC = 0;
    public static final int DEC = 1;
    public static final int HEX = 2;
    public static final int SHORT = 3;
    public static final int INT = 4;
    public static final int LONG = 5;
    public static final int FLOAT = 6;
    public static final int DOUBLE = 7;

    public static void transport(InputStream inputStream, OutputStream out, int mode, boolean bigEndian, boolean deflated, boolean gzipped, String password) throws IOException {
        transport(inputStream, new OutputStreamWriter(out), mode, bigEndian, deflated, gzipped, password);
    }

    public static void transport(InputStream inputStream, Writer writer, int mode, boolean bigEndian, boolean deflated, boolean gzipped, String password) throws IOException {

        // The reference variable in may point to several different objects  within the space of the next few lines.
        if (password != null && !password.equals("")) {
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
            writer.write(c);
        }
        
        inputStream.close();
    
    }

    public static void transport(InputStream inputStream, Writer outputStream, String inputEncoding, String outputEncoding,
            boolean deflated, boolean gzipped, String password) throws IOException {

        if (inputEncoding == null || inputEncoding.equals("")) {
            inputEncoding = "US-ASCII";
        }

        if (outputEncoding == null || outputEncoding.equals("")) {
            outputEncoding = System.getProperty("file.encoding", "8859_1");
        }

        // Note that the reference variable in
        // may point to several different objects
        // within the space of the next few lines.
        if (password != null && !password.equals("")) {
            try {
                // Create a key.
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

        InputStreamReader isr = new InputStreamReader(inputStream, inputEncoding);

        int c;
        while ((c = isr.read()) != -1) {
            outputStream.write(c);
        }
        
    }

}
