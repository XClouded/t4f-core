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
package io.aos.concurrent.spl0.cs05;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicDouble extends Number {
    private final AtomicReference<Double> value;

    public AtomicDouble() {
        this(0.0);
    }

    public AtomicDouble(double initVal) {
        value = new AtomicReference<Double>(new Double(initVal));
    }

    public double get() {
        return value.get().doubleValue();
    }

    public void set(double newVal) {
        value.set(new Double(newVal));
    }

    public boolean compareAndSet(double expect, double update) {
        Double origVal, newVal;

        newVal = new Double(update);
        while (true) {
            origVal = value.get();

            if (Double.compare(origVal.doubleValue(), expect) == 0) {
                if (value.compareAndSet(origVal, newVal))
                    return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean weakCompareAndSet(double expect, double update) {
        return compareAndSet(expect, update);
    }

    public double getAndSet(double setVal) {
        Double origVal, newVal;

        newVal = new Double(setVal);
        while (true) {
            origVal = value.get();

            if (value.compareAndSet(origVal, newVal))
                return origVal.doubleValue();
        }
    }

    public double getAndAdd(double delta) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() + delta);
            if (value.compareAndSet(origVal, newVal))
                return origVal.doubleValue();
        }
    }

    public double addAndGet(double delta) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() + delta);
            if (value.compareAndSet(origVal, newVal))
                return newVal.doubleValue();
        }
    }

    public double getAndIncrement() {
        return getAndAdd(1.0);
    }

    public double getAndDecrement() {
        return getAndAdd(-1.0);
    }

    public double incrementAndGet() {
        return addAndGet(1.0);
    }

    public double decrementAndGet() {
        return addAndGet(-1.0);
    }

    public double getAndMultiply(double multiple) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() * multiple);
            if (value.compareAndSet(origVal, newVal))
                return origVal.doubleValue();
        }
    }

    public double multiplyAndGet(double multiple) {
        Double origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new Double(origVal.doubleValue() * multiple);
            if (value.compareAndSet(origVal, newVal))
                return newVal.doubleValue();
        }
    }

    @Override
    public int intValue() {
        return DoubleValue().intValue();
    }

    @Override
    public long longValue() {
        return DoubleValue().longValue();
    }

    @Override
    public float floatValue() {
        return DoubleValue().floatValue();
    }

    @Override
    public double doubleValue() {
        return DoubleValue().doubleValue();
    }

    @Override
    public byte byteValue() {
        return (byte) intValue();
    }

    @Override
    public short shortValue() {
        return (short) intValue();
    }

    public Double DoubleValue() {
        return value.get();
    }

    public boolean isNaN() {
        return DoubleValue().isNaN();
    }

    public boolean isInfinite() {
        return DoubleValue().isInfinite();
    }

    @Override
    public String toString() {
        return DoubleValue().toString();
    }

    @Override
    public int hashCode() {
        return DoubleValue().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Double origVal = DoubleValue();

        return ((obj instanceof Double) && (origVal.equals(obj)))
                || ((obj instanceof AtomicDouble) && (origVal.equals(((AtomicDouble) obj).DoubleValue())));
    }

    public int compareTo(Double aValue) {
        return Double.compare(doubleValue(), aValue.doubleValue());
    }

    public int compareTo(AtomicDouble aValue) {
        return Double.compare(doubleValue(), aValue.doubleValue());
    }
}
