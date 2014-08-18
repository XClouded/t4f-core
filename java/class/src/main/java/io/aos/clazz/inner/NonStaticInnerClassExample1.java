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
 * Exemple de non static inner class
 */

class TopLevelClass3 {
    private String msg = "Let the sunshine";

    public NonStaticInnerClass makeInstance() {
        return new NonStaticInnerClass();
    }

    public class NonStaticInnerClass {
        //private static int staticVar;
        private String string;

        public NonStaticInnerClass() {
            string = msg;
        }

        public void printMessage() {
            System.out.println(string);
        }

    }
}

public class NonStaticInnerClassExample1 {
    public static void main(String... args) {
        TopLevelClass3 topLevelClass = new TopLevelClass3();
        TopLevelClass3.NonStaticInnerClass nonStaticInnerClass = topLevelClass
                .makeInstance();
        nonStaticInnerClass.printMessage();

        //TopLevelClass.NonStaticInnerClass nonStaticInnerClass2 = new
        //TopLevelClass().nonStaticInnerClass();
        TopLevelClass3.NonStaticInnerClass nonStaticInnerClass3 = topLevelClass.new NonStaticInnerClass();
    }
}
