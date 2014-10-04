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

import java.io.*;

public class FileBytePipeTest {

    public static void main(String... args) {

        try {
            FileOutputStream fout = new FileOutputStream("roots.dat");
            DataOutputStream dout = new DataOutputStream(fout);
            for (int i = 0; i <= 1000; i++) {
                dout.writeDouble(Math.sqrt(i));
            }
            dout.flush();
            dout.close();
        }
        catch (IOException e) {
            System.err.println(e);
        }

    }

}
