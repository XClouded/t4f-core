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
package io.aos.exception;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;

public class ExceptionThrowing3Test {
    public static void main(String... args){
        f();
        g();
    }
    public static void f(){
        try {
            System.out.println("Pressez un touche");
            System.in.read();
        } catch (IOException i) {
            i.getMessage();
        }
    }
    public static void g(){
        try {
            File f = new File("exemple.xml");
            FileReader reader = new FileReader(f);
        } catch (FileNotFoundException f) {
            f.getMessage();
        }
    }
}
