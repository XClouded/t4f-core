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

public class HeritanceExample3 {
    
    public static void main(String... args) {
        HeritanceExample3 m = new HeritanceExample3();
        C c = new C();
        D d = new D();
        c.q();
        d.q();
    }
}

class C {
    int x=1;
    int y=2;
    
    void m(String x) {
        System.out.println(x + y);
    };
    
    void q() {
        System.out.println(x + y);
    }
}

class D extends C {
    String y;
    int z=5;
    
    void q() {
        System.out.println(z);
    }
    
    String r() {
        return (y);
    }
}
