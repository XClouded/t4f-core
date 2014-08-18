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

class A2 {
}

class B2 extends A2 {
}

public class CastingExample2 {
    
    public static void main(String... args) {
        A2 a_a;
        A2 a_b;
        a_a = new A2();
        a_b = new B2();
        testSpecialization(1, a_a);
        testSpecialization(2, a_b);
    }

    private static void testSpecialization(int testNumber, A2 specialization) {
        B2 b_b;
        if (specialization instanceof B2) {
            b_b = (B2) specialization;
            System.out.println(testNumber + ": Casting r�alis�");
        } else {
            System.out.println(testNumber
                    + ": Erreur de casting evitee � l'ex�cution");
        }
    }
}
