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
package io.aos.serde;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class SerializableZipFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private ZipFile zf;

    public SerializableZipFile(String filename) throws IOException {
        this.zf = new ZipFile(filename);
    }

    public SerializableZipFile(File file) throws IOException {
        this.zf = new ZipFile(file);
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(zf.getName());
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {

        String filename = (String) inputStream.readObject();
        zf = new ZipFile(filename);
    }

    public ZipEntry getEntry(String name) {
        return zf.getEntry(name);
    }

    public InputStream getInputStream(ZipEntry entry) throws IOException {
        return zf.getInputStream(entry);
    }

    public String getName() {
        return zf.getName();
    }

    public Enumeration<? extends ZipEntry> entries() {
        return zf.entries();
    }

    public int size() {
        return zf.size();
    }

    public void close() throws IOException {
        zf.close();
    }

    public static void main(String... args) {

        try {
        
            SerializableZipFile szf = new SerializableZipFile(args[0]);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(szf);
            oout.close();
            System.out.println("Wrote object!");

            ByteArrayInputStream binputStream = new ByteArrayInputStream(bout.toByteArray());
            ObjectInputStream oinputStream = new ObjectInputStream(binputStream);
            Object o = oinputStream.readObject();
            System.out.println("Read object!");

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
