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
package io.aos.spl;

import io.aos.console.AosConsole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * This class creates has methods to feed, print and sort a list of Integer.
 * The main method invoked the feed, print a sort method in an way to 
 * print the following result:
 * </p>
 * <p>
 * The initial list:    [6, 4, 6, 2, 8, 2, 0, 8, 0, 3]
 * The initial list sorted:    [0, 0, 2, 2, 3, 4, 6, 6, 8, 8]
 * The initial list sorted plus 2 items:   [0, 0, 2, 2, 3, 4, 6, 6, 8, 8, 9, 5]
 * The initial list sorted plus 2 times sorted:    [0, 0, 2, 2, 3, 4, 5, 6, 6, 8, 8, 9]
 * </p>
 */
public class RandomListMain {
    private static final List<Integer> ints = new ArrayList<Integer>();
    private static final Random random = new Random();

    public static void main(String[] args) {
        
        // Feed the list with 10 integers.
        feed(10);
        // Print the initial list of 10 integers.
        print("The initial list");
        // Sort the list of 10 integers.
        sort();
        // Print the 10 items sorted list.
        print("The initial list sorted");

        // Add 2 items to the list.
        feed(2);
        // Prints the 12 items list.
        print("The initial list sorted plus 2 items");
        // Sort the 12 items list.
        sort();
        // Print the 12 items list.
        print("The initial list sorted plus 2 times sorted");
   
    }
    
    private static void feed(int counts) {
        while (counts != 0) {
            ints.add(random.nextInt(10));
            counts--;
            
        }
    }

    private static void print(String s) {
        AosConsole.println(s + ": " + ints);
    }

    private static void sort() {
        Collections.sort(ints);
    }

}
