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
package io.aos.guice.by.ib1;


import io.aos.guice.by.ib1.Customer;
import io.aos.guice.by.ib1.NotifierModule;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

    @Test
    public void test() {

        Injector injector = Guice.createInjector();
        Customer customer = injector.getInstance(Customer.class);
        customer.changeSomething();

        NotifierModule module = new NotifierModule();
        Injector anotherInjector = Guice.createInjector(module);

        Customer anotherCustomer = anotherInjector.getInstance(Customer.class);
        anotherCustomer.changeSomething();

    }

}
