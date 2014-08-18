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
package io.aos.clazz.nested;

/**
 * Acc�s dans les top level nested classes
 */
public class NestedClassSpl1 {

    public void nonStaticMethod(){
        System.out.println("nonStaticMethod in AccessTopLevelClass");
    }

    private static class NestedTopClass{
        private static int i;
        private int j;

        public static void staticMethod(){
            System.out.println("staticMethod in NestedTopLevelClass");
        }

        interface NestedTopLevelInterface1{int y2K = 2000;}

        protected static class NestedTopLevelClass1 implements NestedTopLevelInterface1{
            private int k = y2K;      //ok car y2k est static

            public void anotherNonStaticMethod(){
                //int jj = j;   NOK : j n'est pas static
                int ii = i;    //OK : i est static
                int kk = k;    //ok : k est static

                // nonStaticMethod();     //NOK m�thode non static
                staticMethod();         //OK : methode static
            }

            public static void main(String... args){
                int ii = i;    //OK i est static
                //int kk = k;   //NOK k non static
                staticMethod();    //Ok m�thode static
            }
        }
    }
}
