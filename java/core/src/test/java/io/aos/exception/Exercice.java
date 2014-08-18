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

import java.io.FileNotFoundException;

public class Exercice {

    public static void main(String... args) {
        try {
            f();
            System.out.println("a");
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println("b");
        } catch (FileNotFoundException f) {
            System.out.println("c");
        } finally {
            System.out.println("d");
        }
        System.out.println("e");
    }

    private static void f() throws FileNotFoundException {
        throw new FileNotFoundException("File not Found");
    }

}
