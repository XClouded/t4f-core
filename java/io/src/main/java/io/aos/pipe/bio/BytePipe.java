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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BytePipe {
    private static final int BYTE_STREAM_CAPACITY = 8182;
    private static final int BYTE_BUFFER_SIZE = 4096;

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = toByteArrayOutputStream(is);
        final byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static ByteArrayOutputStream toByteArrayOutputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTE_STREAM_CAPACITY);
        transport(is, baos);
        return baos;
    }

    public static void transport(InputStream is, ByteArrayOutputStream baos) throws IOException {
        byte[] buf = new byte[BYTE_BUFFER_SIZE];
        int read;
        while ((read = is.read(buf)) > 0) {
            baos.write(buf, 0, read);
        }
    }

    public static void transport(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead; 
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }

    public static void transitBuffered(InputStream is, OutputStream os) throws IOException {
        InputStream bis = new BufferedInputStream(is, 1024 * 1024);
        OutputStream bos = new BufferedOutputStream(os, 1024 * 1024);
        int b; 
        while ((b = bis.read()) != -1) {
            bos.write(b);
        }
        bos.flush();
    }

}
