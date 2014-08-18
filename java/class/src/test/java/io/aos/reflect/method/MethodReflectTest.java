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
package io.aos.reflect.method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class MethodReflectTest {
    private Class<?> clazz;

    @Before
    public void before() {
        clazz = this.getClass();
    }
    
    @Test
    public void testMethods() {
        Method[] methods = clazz.getMethods();
        assertTrue(methods.length > 1);
        for (Method method: methods) {
            System.out.println(method.getName());
        }
    }

    @Test
    public void testBeforeMethod() throws NoSuchMethodException, SecurityException {
        final String methodName = "before";
        Method method = clazz.getMethod(methodName);
        assertEquals(method.getName(), methodName);
    }

    @Test(expected = NoSuchMethodException.class)
    public void testEqualsMethod() throws NoSuchMethodException, SecurityException {
        final String methodName = "equals";
        Method method = clazz.getMethod(methodName);
        assertEquals(method.getName(), methodName);
    }

    @Test(expected = NoSuchMethodException.class)
    public void testEqualsMethod2() throws NoSuchMethodException, SecurityException {
        final String methodName = "equals";
        Method method = clazz.getMethod(methodName, MethodReflectTest.class);
        assertEquals(method.getName(), methodName);
    }

    @Test
    public void testEqualsMethodEquals() throws SecurityException, NoSuchMethodException {
        final String methodName = "equals";
        Method method = clazz.getMethod(methodName, Object.class);
        assertEquals(method.getName(), methodName);
    }

    @Test
    public void testEqualsMethodHashCode() throws SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String methodName = "hashCode";
        Method method = clazz.getMethod(methodName);
        assertEquals(method.getName(), methodName);
        Object o = method.invoke(this);
        System.out.println("Hash Code=" + o);
    }

}
