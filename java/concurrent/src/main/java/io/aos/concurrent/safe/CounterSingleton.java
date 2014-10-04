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
package io.aos.concurrent.safe;

/**
 * 
 */
public class CounterSingleton {
    
    private static CounterSingleton _counterSingleton;
    
    private Integer _classMemberCounter = new Integer("0");
    
    //-------------------------------------------------------------------------
    
    private CounterSingleton() {
    }
    
    public static CounterSingleton getInstance() {
        if (_counterSingleton == null) {
            _counterSingleton = new CounterSingleton();
        }
        return _counterSingleton;
    }

    //-------------------------------------------------------------------------
    
    public void incrementCounters(int index) throws InterruptedException {
        
        Integer methodMemberCounter = new Integer("0");
        
        while(true) {
            
            System.out.println("Thread {" + index + " - Class Member Counter=" + _classMemberCounter + " - Method Member Counter=" + methodMemberCounter);
            
            _classMemberCounter++;
            methodMemberCounter++;
            
            Thread.sleep(1000);
            
        }
        
    }
    
}
