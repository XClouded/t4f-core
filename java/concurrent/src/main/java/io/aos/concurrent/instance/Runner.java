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
package io.aos.concurrent.instance;

public class Runner implements Runnable {

    public Value value;
    
    public Runner(Value value) {
        this.value = value;
        System.out.println("#" + Thread.currentThread().getName() + "# - Parameter attribute=" + this.value.getValue());
        System.out.println("#" + Thread.currentThread().getName() + "# - Class attribute=" + this.value.getValue());
    }

    @Override
    public void run() {
        System.out.println("#" + Thread.currentThread().getName() + "# - Class attribute hashCode=" + this.value.hashCode());
        System.out.println("#" + Thread.currentThread().getName() + "# - Class attribute=" + this.value.getValue());
        System.out.println("#" + Thread.currentThread().getName() + "# - Adding one to class attribute");
        int i = value.getValue();
        i++;
        value.setValue(i);
        System.out.println("#" + Thread.currentThread().getName() + "# - Now Value=" + this.value.getValue());
        System.out.println("#" + Thread.currentThread().getName() + "# - ========================");
    }

}
