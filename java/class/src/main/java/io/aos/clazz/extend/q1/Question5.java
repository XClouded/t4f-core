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

/*
 * Comment le programme suivant va-t-il se comporter lors de la compilation et lors de l�ex�cution ?
 * 
 * public class Question5 {
 *     public static void main(String... args){
 *       X reference1 = new Z();
 *       Y reference2 = (Y)reference1;
 *       System.out.println(reference2.f());
 *     }
 * }
 *
 *
 *class X{int f(){return 1;}}
 *class Y extends X{int f(){return 2;}}
 *class Z extends Y{int f(){return 3;}}
 */

public class Question5 {

    public static void main(String... args){
      X reference1 = new Z();
      Y reference2 = (Y) reference1;
      System.out.println(reference2.f());
    }
}


class X {int f(){return 1;}}

class Y extends X{int f(){return 2;}}

class Z extends Y{int f(){return 3;}}
