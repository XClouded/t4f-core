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
package io.aos.format.currency;

import java.text.NumberFormat;
import java.util.Locale;

public class MinimumWage {

    public static void main(String... args) {

        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        double minimumWage = 5.15;

        System.out.println("The minimum wage is " + dollarFormat.format(minimumWage));
        System.out.println("A worker earning minimum wage and working for forty");
        System.out.println("hours a week, 52 weeks a year, would earn " + dollarFormat.format(40 * 52 * minimumWage));
    }

}
