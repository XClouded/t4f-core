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

class A {
    void m(A a, B b) {
    }
    
    void m(A a, A b) {
    }
}

class B extends A {
    void m(A a, B b) {
        System.out.println("m(A a, B b)");
    }
    
    void m(B b, A a) {
        System.out.println("m(B b, A a)");
    }
    
    void m(B b, B a) {
        System.out.println("m(B b, B b)");
    }
    
    public static void main(String... args) {
        A a_b1;
        A a_b2;
        B b_b;
        a_b1 = new B();
        System.out.println(a_b1.getClass().getName());
        a_b2 = new B();
        System.out.println(a_b1.getClass().getName());
        b_b = new B();
        System.out.println(a_b1.getClass().getName());
        a_b1.m(a_b2, b_b);
    }
}

