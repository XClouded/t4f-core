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
package io.aos.primitive;

public class PrimitivesValueAndReference {
    
    public static void main(String... args) {
        
        int i = 0;
        int j = 0;
        byte b = 1;
        char c = 1;
        short s = 1;
        
        int birthDay = 17;
        int birthMonth = 2;
        int birthYear = 1978;
        MyDate myDate = new MyDate(birthDay, birthMonth, birthYear);

        System.out.println("Birthday before increment :" + birthDay);
        myDate.print();

        increment(birthDay);
        increment(myDate);

        System.out.println("Birthday after increment :" + birthDay);
        myDate.print();
    }
    
    public static void increment(int birthDay) {
        birthDay++;
    }
    
    public static void increment(MyDate myDate) {
        myDate.day++;
    }
}

class MyDate {

    int day;
    int month;
    int year;

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    public void print() {
        System.out.println(day + "/" + month + "/" + year);
    }

}
