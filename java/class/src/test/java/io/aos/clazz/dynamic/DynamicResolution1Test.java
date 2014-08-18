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
package io.aos.clazz.dynamic;

class A1 {
    
    void m(A1 a1, A1 a2) {
        System.out.println("Execution A1.m(A1 a1, A1 a2)");
    }
    
    void m(A1 a, B1 b) {
        System.out.println("Execution A1.m(A1 a, B1 b )");
    }
    
    void m(B1 b, A1 a) {
        System.out.println("Execution A1.m(B1 b, A1 a)");
    }
    
    void m(B1 b1, B1 b2) {
        System.out.println("Execution A1.m(B1 b1, B1 b2)");
    }
}

class B1 extends A1 {
    void m(A1 a1, A1 a2) {
        System.out.println("Execution B1.m(A1 a1, A1 a2)");
    }
    
    void m(A1 a, B1 b) {
        System.out.println("Execution B1.m(A1 a, B1 b)");
    }
    
    void m(B1 b, A1 a) {
        System.out.println("Execution B1.m(B1 b, A1 a)");
    }
    
    void m(B1 b1, B1 b2) {
        System.out.println("Execution B1.m(B1 b1, B1 b2)");
    }
}

class DynamicResolution1Test {
    
    public static void main(String... args) {
        
        A1 a_a;
        A1 a_b;
        B1 b_b;
        
        a_a = new A1();
        a_b = new B1();
        b_b = new B1();        
        
//        Execution A1.m(A1 a1, A1 a2)
        a_a.m(a_a, a_a);
//        Execution A1.m(A1 a1, A1 a2)
        a_a.m(a_a, a_b);
//        Execution A1.m(A1 a1, A1 a2)
        a_a.m(a_b, a_a);
//        Execution A1.m(B1 b1, B1 b2)
        a_a.m(b_b, b_b);

//        Execution B1.m(A1 a1, A1 a2)
        a_b.m(a_a, a_a);
//        Execution B1.m(A1 a1, A1 a2)
        a_b.m(a_a, a_b);
//        Execution B1.m(A1 a1, A1 a2)
        a_b.m(a_b, a_a);
//        Execution B1.m(B1 b1, B1 b2)
        a_b.m(b_b, b_b);

//        Execution B1.m(A1 a1, A1 a2)
        b_b.m(a_a, a_a);
//        Execution B1.m(A1 a1, A1 a2)
        b_b.m(a_a, a_b);
//        Execution B1.m(A1 a1, A1 a2)
        b_b.m(a_b, a_a);
//        Execution B1.m(B1 b1, B1 b2)
        b_b.m(b_b, b_b);

    }
}
