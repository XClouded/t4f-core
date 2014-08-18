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

public class IfMain {

    public static void main(String... args) {

        if1(true, 20, 10);
        if1(false, 20, 30);

        AosConsole.println(testBoolean(false));
        AosConsole.println(testBoolean(true));

        if2();
        if3();

    }

    public static void if1(boolean emergency, int critical, int temperature) {
        if (emergency) {
            System.out.println("Emergency !!!!");
        }
        if (temperature > critical) {
            System.out.println("Temperature too high.");
        }
        if ((temperature > critical) && (emergency)) {
            System.out.println("All parameters are overloaded.");
            soundAlarm();
        }

    }

    public static void if2() {
        for (int i = 0; i < 10; i++) {
            if (i == 1) {
                continue;
            }
            if (i == 4) {
                continue;
            }
            if (i == 9) {
                continue;
            }
            System.out.println(i + "\t" + Math.sqrt(i));
        }
    }

    public static void if3() {
        boolean emergency = true;
        boolean danger = false;
        int critical = 35;
        int temperature = 20;
        if (emergency) {
            soundAlarm();
        } else {
            continueBusiness();
        }
        String result = (emergency == true) ? "soundAlarm" : "continueBusiness";
        System.out.println(result);
        if (temperature > critical) {
            if (emergency)
                soundAlarm();
            if (danger)
                evacuate();
            else
                refresh();
        } else {
            continueBusiness();
        }
    }

    public static String testBoolean(boolean test) {
        return (test == true) ? "OK" : "NOK";
    }

    public static void soundAlarm() {
        AosConsole.println("Beep beep...");
    }

    public static void continueBusiness() {
        AosConsole.println("I continue...");
    }

    public static void evacuate() {
        AosConsole.println("Let's evacuate...");
    }

    public static void refresh() {
        AosConsole.println("I refresh...");
    }

}
