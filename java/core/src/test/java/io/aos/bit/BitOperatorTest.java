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
package io.aos.bit;

import org.junit.Test;

public class BitOperatorTest {

    @Test
    public void test1() {
        int i = 0;
        int j = 0;
        boolean t = true;
        boolean r;
        r = (t & 0 < (i += 1));
        r = (t && 0 < (i += 2));
        r = (t | 0 < (j += 1));
        r = (t || 0 < (j += 2));
        System.out.println("i : " + i + "& j : " + j);
    }
    
    @Test
    public void test2() {
        byte b = 1;
        char c = 1;
        short s = 1;
        int i = 1;
        //s=b*2; //operateur unaire : b--> int et s de type short s=(short)(b*2);
        i = b << s;
        b <<= s;
        //c = c+b;//idem
        s += i;
    }

}
