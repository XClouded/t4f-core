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

public class SwitchBreakMain {

    public static void main(String... args) {
        AosConsole.println(transformDigitToString('5'));
    }

    public static String transformDigitToString(char digit) {
        String result = "";
        switch (digit) {
        case '1':
            result = "one";
            break;
        case '2':
            result = "two";
            break;
        case '3':
            result = "three";
            break;
        case '4':
            result = "four";
            break;
        case '5':
            result = "five";
            break;
        case '6':
            result = "six";
            break;
        case '7':
            result = "seven";
            break;
        case '8':
            result = "height";
            break;
        case '9':
            result = "nine";
            break;
        case '0':
            result = "zero";
            break;
        default:
            System.out.println(digit + "is not a digit.");
        }
        return result;
    }

}
