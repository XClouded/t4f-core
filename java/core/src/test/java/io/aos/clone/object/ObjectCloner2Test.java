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
package io.aos.clone.object;


import io.aos.clone.object.ObjectCloner;

import java.awt.Point;
import java.util.Vector;

import org.junit.Test;

public class ObjectCloner2Test {

    @Test
    public void test() throws Exception {
        String meth = "deep";

        Vector v1 = new Vector();
        Point p1 = new Point(1, 1);
        v1.addElement(p1);

        // see what it is
        System.out.println("Original = " + v1);
        Vector vNew = null;
        if (meth.equals("deep")) {
            vNew = (Vector) (ObjectCloner.deepCopy(v1)); // A
        } else if (meth.equals("shallow")) {
            // shallow copy
            vNew = (Vector) v1.clone(); // B
        }

        System.out.println("New = " + vNew);

        p1.x = 2;
        p1.y = 2;

        System.out.println("Original = " + v1);
        System.out.println("New = " + vNew);

    }

}
