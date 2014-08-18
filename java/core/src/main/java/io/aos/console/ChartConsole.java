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
package io.aos.console;

public class ChartConsole {
    private static final int MAX_X = 20;
    private static final String MARKER = "=";

    public static void main(String... args) {
        for (int x=0; x < MAX_X; x++) {
            int y = f(x);
            print(y);
        }
    }
    
    private static int f(int x) {
      return x;
//        return x*2;
//      return x*x;
    }

    private static void print(int y) {
        for (int i=0; i<y; i++) {
            System.out.print(MARKER);
        }
        System.out.println();
    }

}
