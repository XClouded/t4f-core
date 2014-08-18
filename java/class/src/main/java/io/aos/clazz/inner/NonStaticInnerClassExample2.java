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

/**
 * Acc�s dans les classes locales
 */

class SuperB {
    protected double b1;
    protected static int b2;
}

class SuperC {
    protected double c1;
    protected static int c2;
}

class TopLevelA extends SuperC {
    private double z;
    private static int p;
    
    void nonStaticMethod(final int i) {
        final int j = 10;
        int k;
        class NonStaticLocal extends SuperB {
            //static double d;  //pas de membre static
            int ii = i;
            int jj = j;
            //double kk=k; //k n'est pas final
            double zz = z;
            int pp = p;
            double cc1 = c1;
            int cc2 = c2;
            double bb1 = b1;
            int bb2 = b2;
        }
    }
    
    static void staticMethod(final int i) {
        final int j = 10;
        int k;
        class StaticLocal extends SuperB {
            //static double d;  //pas de membre static
            int ii = i;
            int jj = j;
            //double kk=k; //k n'est pas final
            //double  zz=z; //pas static
            int pp = p;
            //double cc1=c1;  //pas static
            int cc2 = c2;
            double bb1 = b1; //attention
            int bb2 = b2;
        }
    }
}

public class NonStaticInnerClassExample2 {
}
