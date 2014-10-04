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

import java.io.Serializable;

class BioTcpSerialPoint2d implements Serializable {
    // The X and Y coordinates of the point
    private int x;
    private int y;

    // A trick to help with debugging
    private boolean debug;

    public void dprint(String s) {
        // print the debugging string only if the "debug"
        // data member is true
        if (debug)
            System.out.println("Debug: " + s);
    }

    public void setDebug(boolean b) {
        debug = b;
    }

    public BioTcpSerialPoint2d(int px, int py) {
        x = px;
        y = py;

        // turn off debugging
        debug = false;
    }

    public BioTcpSerialPoint2d() {
        this(0, 0);
    }

    public BioTcpSerialPoint2d(BioTcpSerialPoint2d pt) {
        x = pt.getX();
        y = pt.getY();

        // a better method would be to replace the above code with
        // this (pt.getX(), pt.getY());
        // especially since the above code does not initialize the
        // variable debug. This way we are reusing code that is already
        // working.
    }

    public void setX(int px) {
        dprint("setX(): Changing value of X from " + x + " to " + px);
        x = px;
    }

    public int getX() {
        return x;
    }

    public void setY(int py) {
        dprint("setY(): Changing value of Y from " + y + " to " + py);
        y = py;
    }

    public int getY() {
        return y;
    }

    public void setXY(int px, int py) {
        setX(px);
        setY(py);
    }

    public double distanceFrom(BioTcpSerialPoint2d pt) {
        int dx = Math.abs(x - pt.getX());
        int dy = Math.abs(y - pt.getY());

        // check outputStream the use of dprint()
        dprint("distanceFrom(): deltaX = " + dx);
        dprint("distanceFrom(): deltaY = " + dy);

        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public double distanceFromOrigin() {
        return distanceFrom(new BioTcpSerialPoint2d());
    }

    public String toStringForXY() {
        String str = "(" + x + ", " + y;
        return str;
    }

    public String toString() {
        String str = toStringForXY() + ")";
        return str;
    }

    public static void mainputStream(String[] args) {
        BioTcpSerialPoint2d pt1 = new BioTcpSerialPoint2d();

        System.out.println("pt1 = " + pt1);

        BioTcpSerialPoint2d pt2 = new BioTcpSerialPoint2d(4, 3);

        System.out.println("pt2 = " + pt2);

        pt1.setDebug(true); // turning on debugging
        // statements for pt1

        System.out.println("Distance from " + pt1 + " to " + pt2 + " is " + pt1.distanceFrom(pt2));

        System.out.println("Distance from " + pt2 + " to " + pt1 + " is " + pt2.distanceFrom(pt1));

        System.out.println("Distance from " + pt1 + " to the originputStream(0, 0) is " + pt1.distanceFromOrigin());

        System.out.println("Distance from " + pt2 + " to the originputStream(0, 0) is " + pt2.distanceFromOrigin());

        pt1.setXY(3, 5);
        System.out.println("pt1 = " + pt1);

        pt2.setXY(-3, -5);
        System.out.println("pt2 = " + pt2);

        System.out.println("Distance from " + pt1 + " to " + pt2 + " is " + pt1.distanceFrom(pt2));

        System.out.println("Distance from " + pt2 + " to " + pt1 + " is " + pt2.distanceFrom(pt1));

        pt1.setDebug(false); // turning off debugging
        // statements for pt1

        System.out.println("Distance from " + pt1 + " to the originputStream(0, 0) is " + pt1.distanceFromOrigin());

        System.out.println("Distance from " + pt2 + " to the originputStream(0, 0) is " + pt2.distanceFromOrigin());

    }
}
