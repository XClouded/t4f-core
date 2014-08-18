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
package io.aos.clazz.constructor;

public class ConstructSuper2Test {

    public static void main(String... args) {
        NeonLight neonLigth = new NeonLight();
        neonLigth.demonstrate();
    }

}

class Light {
    protected String billType = "Small bill";

    protected double getBill(int noHours) {
        double smallAmount = 10.0, smallBill = smallAmount * noHours;
        System.out.println(billType + ": " + smallBill);
        return smallBill;
    }

    public void banner() {
        System.out.println("Let there be the light !");
    }
}

class TubeLight extends Light {

    public String billType = "Large bill";

    public double getBill(final int noHours) {
        double largeAmount = 100.0, largeBill = largeAmount * noHours;
        System.out.println(billType + ": " + largeBill);
        return largeBill;
    }

    public double getBill() {
        System.out.println("No bill");
        return 0.0;
    }
}

class NeonLight extends TubeLight {

    public void demonstrate() {
        super.banner();
        super.getBill(20);
        super.getBill();
        System.out.println(super.billType);
        ((Light) this).getBill(20);
        System.out.println(((Light) this).billType);
    }

}
