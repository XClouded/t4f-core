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
package io.aos.model.m2;

import io.aos.model.m2.Employee;

import java.util.Date;

public class EmployeeTest {

    public static void main(String... args) {

        Employee[] employees = {
                new Employee("Eric", "43343", "Rue de la gare", new Date(), "", 5345.0),
                new Employee("David", "87433", "Place de la gate", new Date(), "54356", 5345.0)
        };
        System.out.println(employees[1].giveDetails());
    }

}
