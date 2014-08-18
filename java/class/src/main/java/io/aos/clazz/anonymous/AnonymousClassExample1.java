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
package io.aos.clazz.anonymous;

class A1 {
}

interface IDrawable1 {
    void draw();
}

class Shape1 implements IDrawable1 {
    public void draw() {
        System.out.println("drawing a shape");
    }
}

public class AnonymousClassExample1 {

    public static void main(String... args) {
        Shape1 s = new A1() {
            public Shape1 createCircle(final double radius) {
                class Circle1 extends Shape1 {
                    public void draw() {
                        System.out.println("drawing a circle");
                    }
                }
                return new Circle1();
            }
        }.createCircle(5);
        s.draw();
        System.out.println(s.getClass());
    }

}
