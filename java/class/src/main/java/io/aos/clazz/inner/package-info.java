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

/**
 * Inner Class Example
 * 
 * To see an inner class in use, let's first consider an array. In the following
 * example, we will create an array, fill it with integer values and then output
 * only values of even indices of the array in ascending order.
 * 
 * The DataStructure class below consists of:
 * 
 * The DataStructure outer class, which includes methods to add an integer onto
 * the array and print out values of even indices of the array. The
 * InnerEvenIterator inner class, which is similar to a standard Java iterator.
 * Iterators are used to step through a data structure and typically have
 * methods to test for the last element, retrieve the current element, and move
 * to the next element. A main method that instantiates a DataStructure object
 * (ds) and uses it to fill the arrayOfInts array with integer values (0, 1, 2,
 * 3, etc.), then calls a printEven method to print out values of even indices
 * of arrayOfInts.
 * 
 * public class DataStructure { // create an array private final static int SIZE
 * = 15; private int[] arrayOfInts = new int[SIZE];
 * 
 * public DataStructure() { // fill the array with ascending integer values for
 * (int i = 0; i < SIZE; i++) { arrayOfInts[i] = i; } }
 * 
 * public void printEven() { // print out values of even indices of the array
 * InnerEvenIterator iterator = this.new InnerEvenIterator(); while
 * (iterator.hasNext()) { System.out.println(iterator.getNext() + " "); } }
 * 
 * // inner class implements the Iterator pattern private class
 * InnerEvenIterator { // start stepping through the array from the beginning
 * private int next = 0;
 * 
 * public boolean hasNext() { // check if a current element is the last in the
 * array return (next <= SIZE - 1); }
 * 
 * public int getNext() { // record a value of an even index of the array int
 * retValue = arrayOfInts[next]; //get the next even element next += 2; return
 * retValue; } }
 * 
 * public static void main(String s[]) { // fill the array with integer values
 * and print out only // values of even indices DataStructure ds = new
 * DataStructure(); ds.printEven(); } }
 * 
 * The output is:
 * 
 * 0 2 4 6 8 10 12 14
 * 
 * Note that the InnerEvenIterator class refers directly to the arrayOfInts
 * instance variable of the DataStructure object.
 * 
 * Inner classes can be used to implement helper classes like the one shown in
 * the example above. If you plan on handling user-interface events, you will
 * need to know how to use inner classes because the event-handling mechanism
 * makes extensive use of them. Local and Anonymous Inner Classes
 * 
 * There are two additional types of inner classes. You can declare an inner
 * class within the body of a method. Such a class is known as a local inner
 * class. You can also declare an inner class within the body of a method
 * without naming it. These classes are known as anonymous inner classes. You
 * will encounter such classes in advanced Java programming. Modifiers
 * 
 * You can use the same modifiers for inner classes that you use for other
 * members of the outer class. For example, you can use the access specifiers —
 * private, public, and protected — to restrict access to inner classes, just as
 * you do to other class members.
 */
package io.aos.clazz.inner;

