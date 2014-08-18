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
package io.aos.out.bio;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiByteOut extends FilterOutputStream {

    private List<OutputStream> streams = new ArrayList<OutputStream>();

    public MultiByteOut(OutputStream outputStream) {
        super(outputStream);
        streams.add(outputStream);
    }

    public void addOutputStream(OutputStream outputStream) {
        streams.add(outputStream);
    }

    public void write(int b) throws IOException {
        Iterator<OutputStream> iterator = streams.iterator();
        while (iterator.hasNext()) {
            OutputStream outputStream = (OutputStream) iterator.next();
            outputStream.write(b);
        }
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        Iterator<OutputStream> iterator = streams.iterator();
        while (iterator.hasNext()) {
            OutputStream outputStream = (OutputStream) iterator.next();
            outputStream.write(data, offset, length);
        }
    }

    public void flush() throws IOException {
        Iterator<OutputStream> iterator = streams.iterator();
        while (iterator.hasNext()) {
            OutputStream outputStream = (OutputStream) iterator.next();
            outputStream.flush();
        }
    }

    public void close() throws IOException {
        Iterator<OutputStream> iterator = streams.iterator();
        while (iterator.hasNext()) {
            OutputStream outputStream = (OutputStream) iterator.next();
            outputStream.close();
        }
    }

}
