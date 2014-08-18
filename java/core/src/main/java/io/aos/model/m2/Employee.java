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

import java.util.Date;

public class Employee extends StaffMember {

  public String socSecNumber;
  public double payRate;
  
  public Employee(String name, String phone, String address, Date birthDate, String socSecNumber, double payRate) {
    super(name,address,phone,birthDate);
    this.socSecNumber=socSecNumber;
    this.payRate=payRate;
  }

  public double pay() {
    return payRate;
  }

  public String giveDetails() {
    String text=super.giveDetails();
    text = text+".\nLe numero de securite sociale est "+socSecNumber+" et le salaire est "+payRate; 
    return text;
  }

}
