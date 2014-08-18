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
package io.aos.clazz.extend.q1;

/**
 * Que pourra-t-on lire lors de l�ex�cution du programme suivant :
 * public class Question2 {
 * public static void main(String... args){
 *     B b = new B("Bonjour")
 * 
 * }}
 * 
 * class A {
 * public A() {this("1","2");}
 * public A(String a1, String a2) {this(a1+a2);}
 * public A(String a) {System.out.println(a);}
 * }
 * 
 * class B extends A{
 *   public B(String text) {
 *   System.out.println(text);
 *   }
 * }
 *
 */
public class Question2 {

    public static void main(String... args){
        B b = new B("Bonjour");

    }}

class A {
    public A() {this("1","2");}
    public A(String a1, String a2) {this(a1+a2);}
    public A(String a) {System.out.println(a);}
}

class B extends A{
    public B(String text) {
        System.out.println(text);
    }
}
