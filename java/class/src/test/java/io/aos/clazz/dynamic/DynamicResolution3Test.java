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

class A7 {
    String v = "A";
    String v() {
        return v;
    }
    
    String w() {
        return "W";
    }
}

class B7 extends A7 {
    String v = "B7";
    String v() {
        return v;
    }
    
    String w() {
        return super.v;
    }
}

class C7 extends B7 {
    String v = "C";    
    String w() {
        return super.v;
    }
}

class D7 extends C7 {
    String v = "D";
    String v() {
        return v;
    }
}

class DynamicResolution3Test {
    
    public static void main(String... args) {
        D7 d = new D7();
        C7 c = d;
        B7 b = new C7();
        A7 a = b;
        System.out.println("a.v = " + a.v);
        System.out.println("a.v() = " + a.v());
        System.out.println("a.w() = " + a.w());
        System.out.println("b.v = " + b.v);
        System.out.println("b.v() = " + b.v());
        System.out.println("b.w() = " + b.w());
        System.out.println("c.v = " + c.v);
        System.out.println("c.v() = " + c.v());
        System.out.println("c.w() = " + c.w());
        System.out.println("d.v = " + d.v);
        System.out.println("d.v() = " + d.v());
        System.out.println("d.w() = " + d.w());
    }
}
