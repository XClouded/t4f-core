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
package io.aos.statement;

import io.aos.console.AosConsole;

public class SwitchNoBreakMain {

    public static void main(String... args) {
        AosConsole.println(countDown(5));
        AosConsole.println(countDown(3));
    }

    public static String countDown(int start) {
        StringBuffer result = new StringBuffer();
        switch (start) {
        case 10:
            result.append("ten - ");
        case 9:
            result.append("nine - ");
        case 8:
            result.append("height - ");
        case 7:
            result.append("seven - ");
        case 6:
            result.append("six - ");
        case 5:
            result.append("five - ");
        case 4:
            result.append("four - ");
        case 3:
            result.append("three - ");
        case 2:
            result.append("two - ");
        case 1:
            result.append("one - ");
        default:
            System.out.println();
        }
        result.append("zero - Go!");
        return result.toString();
    }

}
