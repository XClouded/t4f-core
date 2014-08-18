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
 * Soient trois classes A, B et C. B extends A et C extends B. 
 * Chacune de ces classes impl�mente la m�thode doIt(). 
 * 
 * Comment, � partir d�une m�thode de C peut-on acc�der � 
 * 1.    la m�thode doIt() de C.
 * 2.    la m�thode doIt() de B.
 * 3.    la m�thode doIt() de A.
 * 
 **/

public class Question1 {
    
    
public class A {
}

public class B extends A {
}

public class C extends B {
}

}
