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
package io.aos.model.m3;

/**
 * This immutable class represents <i>complex numbers</i>. 
 */
public class Complex {
    /**
     * Holds the real part of this complex number. 
     * @see #y
     */
    protected double x;

    /**
     * Holds the imaginary part of this complex number. 
     * @see #x
     */
    protected double y;

    /**
     * Creates a new Complex object that represents the complex number x+yi. 
     * @param x The real part of the complex number. 
     * @param y The imaginary part of the complex number. 
     */
    public Complex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds two Complex objects and produces a third object that represents
     * their sum. 
     * @param c1 A Complex object
     * @param c2 Another Complex object
     * @return  A new Complex object that represents the sum of 
     *          <code>c1</code> and <code>c2</code>. 
     * @exception java.lang.NullPointerException 
     *            If either argument is <code>null</code>. 
     */
    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.x + c2.x, c1.y + c2.y);
    }
}
