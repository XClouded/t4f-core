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

import io.aos.clazz.dynamic.B;

class B2 extends DynamicResolution2Test {
    
    void m(DynamicResolution2Test x, B2 y) {
        System.out.println("Execution B.m(A x , B y)");
    }
    
    void m(DynamicResolution2Test x, DynamicResolution2Test y) {
        System.out.println("Execution B.m(A x, A y)");
    }
    
    void m(B2 x, B2 y) {
        System.out.println("Execution B.m(B x  , B y )");
    }
    
}

class DynamicResolution2Test {
    
    void m(DynamicResolution2Test x, B y) {
        System.out.println("Execution A.m(A x , B y )");
    }
    
    void m(B2 x, B2 y) {
        System.out.println("Execution A.m(B x  , B y )");
    }
    
    void m(DynamicResolution2Test x, DynamicResolution2Test y) {
        System.out.println("Execution A.m(A x , A y)");
    }
    
    public static void main(String... args) {
        DynamicResolution2Test o;
        B2 u;
        B2 v;
        o = new B2();
        u = new B2();
        v = new B2();
        o.m(u, v);
    }
}
