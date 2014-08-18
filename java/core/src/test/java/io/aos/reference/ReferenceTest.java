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
package io.aos.reference;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * This class demonstrates the way arguments are given to a method.
 */
public class ReferenceTest {

    /**
     * We create a new Data class with a initial value 34.
     */
    @Test
    public void test1() {
        
        String s = new String("Hello1");

        // It will print out "Hello1"
        System.out.println("Hello1=" + s);

        changeString(s);

        // It will print out "Hello1"
        System.out.println("Changed Hello1=" + s);

        PrimitiveInteger d = new PrimitiveInteger(34);

        // The value will be 34.
        System.out.println("Main: " + d.getValue());

        // We invoke 3 times the increment method on the data.
        d.increment();
        d.increment();
        d.increment();

        // The value will be 34+3=37.
        System.out.println("Main: " + d.getValue());

        // We call a method that will act on the data.
        delegateIncrementation(d);

        // Thereafter, the value will be 40.
        System.out.println("Main: " + d.getValue());

        // We still invoke 3 times the increment method on the data.
        d.increment();
        d.increment();
        d.increment();

        // The value will be 40+3=43
        System.out.println("Main: " + d.getValue());
    }

    @Test
    public void test2() {
        ObjectInteger aosInt1 = new ObjectInteger();
        ObjectInteger aosInt2 = new ObjectInteger(aosInt1);
        assertTrue(aosInt1 != aosInt2);
        assertTrue(aosInt1 == aosInt2.getAosInt());
    }

    @Test
    public void test3() {
        ObjectInteger aosInt1 = new ObjectInteger();
        aosInt1 = new ObjectInteger(aosInt1);
        assertTrue(aosInt1 != aosInt1.getAosInt());
    }

    private void changeString(String changingString) {
        changingString = new String("Hello2");
        // It will print out "Hello2"
        System.out.println("Changing String=" + changingString);
    }

    private void delegateIncrementation(PrimitiveInteger d) {

        // The value will be 37.
        System.out.println("Delegate Incrementation: " + d.getValue());

        // We increment 3 times.
        d.increment();
        d.increment();
        d.increment();

        // The value will be 37+3=40.
        System.out.println("Delegate Incrementation: " + d.getValue());

    }

    public static class PrimitiveInteger {
        private int i;

        public PrimitiveInteger(int i) {
            this.i = i;
        }

        public void increment() {
            this.i++;
        }

        public int getValue() {
            return this.i;
        }

    }

    public static class ObjectInteger {
        private ObjectInteger aosInt;

        public ObjectInteger() {
        }

        public ObjectInteger(ObjectInteger aosInt) {
            this.aosInt = aosInt;
        }

        public ObjectInteger getAosInt() {
            return this.aosInt;
        }

    }

}
