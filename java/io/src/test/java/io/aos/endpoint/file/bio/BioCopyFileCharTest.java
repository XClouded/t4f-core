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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BioCopyFileCharTest {

    @Test
    public void test() throws IOException {
        File from = new File("src/main/resources/io/aos/file/1.txt");
        File to = new File("target/1.txt");
        doCopy(from, to);
    }

    protected void doCopy(File from, File to) throws IOException {
        FileReader inputStream = null;
        FileWriter outputStream = null;
        int c;
        try {
            inputStream = new FileReader(from);
            outputStream = new FileWriter(to);
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
