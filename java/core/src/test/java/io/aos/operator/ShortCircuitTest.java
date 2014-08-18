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
package io.aos.operator;

import org.junit.Test;

public class ShortCircuitTest {
    
    @Test
    public void test() {
        int i = 2;
        if ((test1(i)) && (test2(i)) && (test3(i))) {
            System.out.println("i>3");
        }
        else {
            System.out.println("i<=3");
        }
    }

    private boolean test1(int value) {
        System.out.println("test1");
        return value > 1;
    }
    
    private boolean test2(int value) {
        System.out.println("test2");
        return value > 2;
    }
    
    private boolean test3(int value) {
        System.out.println("test3");
        return value > 3;
    }
    
}
