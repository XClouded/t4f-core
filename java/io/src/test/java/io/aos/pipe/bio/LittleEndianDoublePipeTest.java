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

import io.aos.in.bio.typed.TypedByteIn;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class LittleEndianDoublePipeTest {

    public static void main(String... args) {

        for (int i = 0; i < args.length; i++) {

            try {
                FileInputStream fis = new FileInputStream(args[i]);
                // now that we know the file exists, print its name
                System.out.println("-----------" + args[i] + "-----------");
                TypedByteIn dinputStream = new TypedByteIn(fis);
                int fr = 0;
                while (true) {
                    double theNumber = dinputStream.readDouble();
                    System.out.println(++fr + "\t" + theNumber);
                }
            }
            catch (EOFException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
