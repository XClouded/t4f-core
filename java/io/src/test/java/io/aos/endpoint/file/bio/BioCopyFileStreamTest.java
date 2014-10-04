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
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class BioCopyFileStreamTest {

    @Test
    public void test() throws IOException {
        copy("pom.xml", "target/pom.xml.copy");
    }

    public static void copy(String inFile, String outFile) throws IOException {
        FileInputStream inputStream= null;
        FileOutputStream fout = null;

        try {
            inputStream= new FileInputStream(inFile);
            fout = new FileOutputStream(outFile);
            BytePipe.transport(inputStream, fout);
        } finally {
            try {
                if (inputStream!= null)
                    inputStream.close();
            } catch (IOException ex) {
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException ex) {
                // Do nothing.
            }
        }
    }

}
