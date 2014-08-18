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

public class PrimitiveWrapper {
    
    public static void main(String... args) {

        int i = 0;
        System.out.println(i);
        
        Integer i1;
        
        i1 = new Integer(1);
        System.out.println(i1.toString());
        
        i1 = new Integer("2");
        System.out.println(i1.toString());
                
        System.out.println (Integer.valueOf("3"));
        
        System.out.println(Integer.parseInt("4"));
        
        i = i1.intValue();
        System.out.println(i);
        
    }
    
}
