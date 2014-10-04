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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.ZipFile;

public class SerializableZipFileNot extends ZipFile implements Serializable {
    private static final long serialVersionUID = 1L;

    public SerializableZipFileNot(String filename) throws IOException {
        super(filename);
    }

    public SerializableZipFileNot(File file) throws IOException {
        super(file);
    }

    public static void main(String... args) {

        try {

            SerializableZipFileNot szf = new SerializableZipFileNot(args[0]);
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
