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
package io.aos.string;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class StringTest {

    @Test
    public void testSimple() {
        
        String string1 = "hello world";
        String string2 = "hello world";
        String string3 = new String(string2);
        
        System.out.println("1: " + (string1 == string2)); //true
        System.out.println("2: " + (string1 == string3)); //false

        System.out.println("3: " + string1.equals(string2)); //true
        System.out.println("4: " + string1.equals(string3)); //true
        System.out.println();
        
        System.out.println(string1.length());
        System.out.println(string1.substring(0, 1));
        System.out.println(string1.substring(string1.length() - 1, string1.length()));

    }
    
    
    @Test
    public void testIntern() {
        String string1 = "bonjour";
        assertSame(string1.intern(), string1.intern());
    }
    
}
