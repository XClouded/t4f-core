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
package io.aos.clazz.interface2;

interface IStackable {
    
}

interface ISafeStackable extends IStackable {
    
}

interface ISortable {
    
    public void sort();
    
}

class StackImpl implements IStackable {
    
}

class SafeStackImpl extends StackImpl implements ISafeStackable, ISortable {
    
    public void printSafeStack() {
        System.out.println("I am the printSafeStack() method");        
    }
    
    public void sort() {
        System.out.println("I am the sort() method");        
    }
}

public class Stack {
    
    public static void main(String... args) {
        
        Object objRef;
        StackImpl stackRef;
        IStackable iStackRef;
        ISafeStackable iSafeStackRef;
        SafeStackImpl safeStackRef = new SafeStackImpl();
        
        objRef = safeStackRef;
        stackRef = safeStackRef;
        iStackRef = stackRef;
        iSafeStackRef = safeStackRef;
        objRef = iStackRef;
        iStackRef = safeStackRef;
        
        Stack s = new Stack();
        
        s.getInfo(stackRef, safeStackRef, iSafeStackRef, safeStackRef);
        // s.getInfo(stackRef, safeStackRef, iSafeStackRef, stackRef); //
        // Ill�gal
        
        ISortable is = new SafeStackImpl();
        method(is);
        
    }
    
    void getInfo(Object o, StackImpl si, IStackable i, SafeStackImpl ssi) {
        
    }
    
    public static void method (ISortable is) {
        
        is.sort();
        
        SafeStackImpl ssi;
        ssi= (SafeStackImpl) is;
        ssi.printSafeStack();

    }

}
