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
package io.aos.endpoint.socket.bio;

import java.io.*;

class BioTcpSerialPoint3d extends BioTcpSerialPoint2d implements Serializable {
    private int z;

    public BioTcpSerialPoint3d(int px, int py, int pz) {
        super(px, py);
        z = pz;
    }

    public BioTcpSerialPoint3d() {
        super();
        z = 0;
        // perhaps a better method would be to replace the above code with
        // this (0, 0, 0);
    }

    public BioTcpSerialPoint3d(BioTcpSerialPoint3d pt) {
        super((BioTcpSerialPoint2d) pt);
        z = pt.getZ();

        // perhaps a better method would be to replace the above code with
        // this (pt.getX(), pt.getY(), pt.getZ());
    }

    public void setZ(int pz) {
        dprint("setZ(): Changing value of z from " + z + " to " + pz);
        z = pz;
    }

    public int getZ() {
        return z;
    }

    public void setXYZ(int px, int py, int pz) {
        setXY(px, py);
        setZ(pz);
    }

    public double distanceFrom(BioTcpSerialPoint3d pt) {
        double xyDistance = super.distanceFrom((BioTcpSerialPoint2d) pt);
        int dz = Math.abs(z - pt.getZ());
        dprint("distanceFrom(): deltaZ = " + dz);

        return Math.sqrt((xyDistance * xyDistance) + (dz * dz));
    }

    public double distanceFromOrigin() {
        return distanceFrom(new BioTcpSerialPoint3d());
    }

    public String toStringForZ() {
        String str = ", " + z;
        return str;
    }

    public String toString() {
        String str = toStringForXY() + toStringForZ() + ")";
        return str;
    }

    public static void mainputStream(String[] args) {
        BioTcpSerialPoint3d pt1 = new BioTcpSerialPoint3d();
        System.out.println("pt1 = " + pt1);

        BioTcpSerialPoint3d pt2 = new BioTcpSerialPoint3d(1, 2, 3);
        System.out.println("pt2 = " + pt2);

        pt1.setDebug(true); // turn on debugging statements
        // for pt1
        System.out.println("Distance from " + pt2 + " to " + pt1 + " is " + pt2.distanceFrom(pt1));

        System.out.println("Distance from " + pt1 + " to the originputStream(0, 0) is " + pt1.distanceFromOrigin());

        System.out.println("Distance from " + pt2 + " to the originputStream(0, 0) is " + pt2.distanceFromOrigin());

        pt1.setXYZ(3, 5, 7);
        System.out.println("pt1 = " + pt1);

        pt2.setXYZ(-3, -5, -7);
        System.out.println("pt2 = " + pt2);

        System.out.println("Distance from " + pt1 + " to " + pt2 + " is " + pt1.distanceFrom(pt2));

        System.out.println("Distance from " + pt2 + " to " + pt1 + " is " + pt2.distanceFrom(pt1));

        pt1.setDebug(false); // turning off debugging
        // statements for pt1

        System.out.println("Distance from " + pt1 + " to the originputStream(0, 0) is " + pt1.distanceFromOrigin());

        System.out.println("Distance from " + pt2 + " to the originputStream(0, 0) is " + pt2.distanceFromOrigin());

    }
}
