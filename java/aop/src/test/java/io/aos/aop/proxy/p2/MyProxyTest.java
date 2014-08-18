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
package io.aos.aop.proxy.p2;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import io.aos.aop.proxy.p2.MyProxyBuilder;
import io.aos.aop.proxy.p2.MyProxyImpl;
import io.aos.aop.proxy.p2.MyProxyInterface;
import io.aos.aop.proxy.p2.MyProxyInvocationHandler;

import java.lang.reflect.Proxy;

import org.junit.Test;

public class MyProxyTest {

    @Test
    public void test() {
        MyProxyInterface delegate = new MyProxyImpl();
        MyProxyInterface foo = (MyProxyInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[] {MyProxyInterface.class},
                new MyProxyInvocationHandler(delegate));
        assertNotNull(foo);
        assertEquals(foo.saySomething(), MyProxyImpl.HELLO);
    }

    @Test
    public void test2() {
        MyProxyInterface delegate = new MyProxyImpl();
        MyProxyInterface foo = (MyProxyInterface) MyProxyBuilder
                .newInstance(delegate, MyProxyInterface.class);
        assertNotNull(foo);
        assertEquals(foo.saySomething(), MyProxyImpl.HELLO);
    }

    @Test
    public void test3() {
        MyProxyInterface delegate = new MyProxyImpl();
        MyProxyInterface foo = MyProxyBuilder.newInstance(delegate, MyProxyInterface.class);
        assertNotNull(foo);
        assertEquals(foo.saySomething(), MyProxyImpl.HELLO);
    }

}
