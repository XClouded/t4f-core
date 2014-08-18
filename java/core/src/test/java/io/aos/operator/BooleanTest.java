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

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class BooleanTest {
    
    @Test
    public void testBoolean() {
        assertEquals(true, and(true, true));
        assertEquals(false, and(false, true));
        assertEquals(true, or(true, true));
        assertEquals(true, or(false, true));
    }

    @Test
    public void testBoolean2() {
        Random rand = new Random();
        int i = rand.nextInt() % 100;
        int j = rand.nextInt() % 100;
        System.out.println("i<j :" + (i < j));
        System.out.println("i>j :" + (i > j));
        System.out.println("i>50 AND j<50 :" + ((i > 50) && (j < 50)));
        // System.out.println(i&&j);
    }

    private boolean and(boolean b1, boolean b2) {
        return (b1 && b2);
    }

    private boolean or(boolean b1, boolean b2) {
        return (b1 || b2);
    }

}
