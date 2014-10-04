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


import io.aos.out.bio.MultiByteOut;
import io.aos.pipe.bio.BytePipe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BioCopyMultiTest {

    public static void main(String... args) throws IOException {

        if (args.length < 2) {
            System.out.println("Usage: java MultiCopier infile outfile1 outfile2...");
            return;
        }

        FileInputStream inputStream= new FileInputStream(args[0]);
        FileOutputStream fout1 = new FileOutputStream(args[1]);
        MultiByteOut mout = new MultiByteOut(fout1);
        for (int i = 2; i < args.length; i++) {
            mout.addOutputStream(new FileOutputStream(args[i]));
        }
        BytePipe.transitBuffered(inputStream, mout);
        inputStream.close();
        mout.close();
    }

}
