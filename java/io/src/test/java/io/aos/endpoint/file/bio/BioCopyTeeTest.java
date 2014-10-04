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


import io.aos.out.bio.TeeByteOut;
import io.aos.pipe.bio.BytePipe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BioCopyTeeTest {

    public static void main(String... args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java TeeCopier infile outfile1 outfile2");
            return;
        }
        FileInputStream inputStream= new FileInputStream(args[0]);
        FileOutputStream fout1 = new FileOutputStream(args[1]);
        FileOutputStream fout2 = new FileOutputStream(args[2]);
        TeeByteOut tout = new TeeByteOut(fout1, fout2);
        BytePipe.transitBuffered(inputStream, tout);
        inputStream.close();
        tout.close();
    }

}
