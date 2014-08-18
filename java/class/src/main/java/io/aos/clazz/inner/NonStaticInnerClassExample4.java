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
package io.aos.clazz.inner;

/**
 * Ambiguit� entre classe container et classe parent
 */
class B {
    protected double x = 2.17;
}

class A {
    private double x = 3.14;

    class C extends B {
        private double w = x;
        private double y = this.x;
        private double z = A.this.x;

        public void printX() {
            System.out.println("ambigous?" + w);
            System.out.println("this.x" + y);
            System.out.println("A.this.x" + z);
        }
    }
}

public class NonStaticInnerClassExample4 {
    public static void main(String... args) {
        A.C ref = new A().new C();
        ref.printX();
    }
}
