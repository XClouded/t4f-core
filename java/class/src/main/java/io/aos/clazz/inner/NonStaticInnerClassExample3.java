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
package io.aos.clazz.inner;

class TLCClassA{
    private String msg = "TCLClassA object";

    public TLCClassA(String objNum) {msg = msg+objNum;}
    public void printMsg(){System.out.println(msg);}

    class InnerB{
        private String msg = "InnerB object";

        public InnerB(String objNum) {msg = msg + objNum;}
        public void printMsg() {System.out.println(msg);}

        class InnerC{
            private String msg ="InnerC object";
            public InnerC(String objNum) {msg = msg+ objNum;}
            public void printMsg(){
                System.out.println(msg);
                System.out.println(this.msg);
                System.out.println(InnerC.this.msg);
                System.out.println(InnerB.this.msg);
                InnerB.this.printMsg();
                System.out.println(TLCClassA.this.msg);
            }

        }
    }

}

public class NonStaticInnerClassExample3 {

    public static void main(String... args){
        TLCClassA a = new TLCClassA("1");
        TLCClassA.InnerB b = a.new InnerB("1");
        TLCClassA.InnerB.InnerC c = b.new InnerC("1");
        c.printMsg();
    }

}
