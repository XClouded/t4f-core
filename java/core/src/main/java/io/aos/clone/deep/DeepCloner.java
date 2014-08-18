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
package io.aos.clone.deep;

import io.aos.clone.shallow.ShallowExample;

/**
 * Illustrate cloning a subclass of a cloneable class.
 **/
public class DeepCloner extends ShallowExample {

    public DeepCloner(int size) {
        super(size);
    }

    public Object clone() {
        DeepCloner copy = (DeepCloner) super.clone();
        copy.integers = new Integer[copy.size];
        for (int i = 0; i < copy.size; i++) {
            copy.integers[i] = new Integer(integers[i].intValue());
        }
        return copy;
    }

}
