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

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class DataPipe {

    public static void main(String... args) throws IOException {

        DataInputStream dinputStream= null;
        try {
            FileInputStream fis = new FileInputStream(args[0]);
            System.out.println("-----------" + args[0] + "-----------");
            dinputStream= new DataInputStream(fis);
            while (true) {
                int theNumber = dinputStream.readInt();
                System.out.println(theNumber);
            }
        } catch (EOFException ex) {
            // Normal termination
            dinputStream.close();
        } catch (IOException ex) {
            // Abnormal termination
            System.err.println(ex);
        }
    }
}
