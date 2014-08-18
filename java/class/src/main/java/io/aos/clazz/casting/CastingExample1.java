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
package io.aos.clazz.casting;

class A1 {
}

class B1 extends A1 {
}

public class CastingExample1 {

    public static void main(String... args) {
        A1 a_a;
        A1 a_b;
        B1 b_b;

        a_a = new A1();
        a_b = new B1();

        b_b = (B1) a_b;
        System.out.println("OK1");

        // Compiles but ill not work at runtime...
        // (java.lang.ClassCastException)
        b_b = (B1) a_a;
        System.out.println("KO2");

    }

}
