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

public class StaffMember {

  public String name;
  public String phone;
  public String address;
  public Date birthDate;

  StaffMember (String name, String address, String phone, Date birthDate) {
   this.name=name;
   this.phone=phone;
   this.address=address;
   this.birthDate=birthDate;
  }  
  
  public String giveDetails() {
    String text = "Le StaffMember s'appelle "+name+", a pour n� de t�l. "+phone+", pour adresse ";
    text = text+address+ " et est n� le "+birthDate.toString();
    return text;
  }
}
