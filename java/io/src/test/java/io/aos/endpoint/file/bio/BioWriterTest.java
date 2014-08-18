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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BioWriterTest {

    @Test
    public void writeFile1() throws Exception {
        FileOutputStream fos = new FileOutputStream("C:/demo.txt");
        byte b = 01;
        fos.write(b);
        fos.close();
    }

    @Test
    private void writeFile2() throws IOException {
        FileOutputStream fos = new FileOutputStream("test1.txt");
        fos.write("This is some test test".getBytes());
        fos.close();
    }

    @Test
    private void writeFile3() throws IOException {
        FileWriter fw = new FileWriter("test2.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("this is some test");
        bw.flush();
        bw.close();
        fw.close();
    }

}
