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
package io.aos.clazz.extend.spl2;

class A {
    int x;    
    int y = 1;
}

class B extends A {
    String y = "Un";
    int z;
}

public class HeritanceExample2 {
    
    public static void main(String... args) {
        HeritanceExample2 m = new HeritanceExample2();
        A a = new A();
        B b = new B();
        m.print(a, b);
    }
    
    void print(A a, B b) {
        System.out.println("a.y = " + a.y + " - b.y = " + b.y);
    }
}
