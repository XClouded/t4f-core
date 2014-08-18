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
/*
 * TestUtils.java
 *
 * Created on July 12, 2006, 8:14 PM
 *
 * @(#)TestUtils.java	1.3 10/03/23
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Oracle or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

package com.sun.jmx.examples.scandir;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.NotificationEmitter;
import javax.management.ObjectName;

/**
 * A utility class defining static methods used by our tests.
 * 
 * @author Sun Microsystems, 2006 - All rights reserved.
 */
public class TestUtils {
    
    /**
     * A LOGGER for this class.
     **/
    private static final Logger LOGGER=
            LoggerFactory.getLogger(TestUtils.class.getName());
    
    /** Creates a new instance of TestUtils */
    private TestUtils() {
    }
    
    /**
     * Returns the ObjectName of the MBean that a proxy object
     * is proxying.
     **/
    public static ObjectName getObjectName(Object proxy) {
        if (!(proxy instanceof Proxy))
            throw new IllegalArgumentException("not a "+Proxy.class.getName());
        final Proxy p = (Proxy) proxy;
        final InvocationHandler handler =
                Proxy.getInvocationHandler(proxy);
        if (handler instanceof MBeanServerInvocationHandler) 
            return ((MBeanServerInvocationHandler)handler).getObjectName();
        throw new IllegalArgumentException("not a JMX Proxy");
    }
    
    /**
     * Transfroms a proxy implementing T in a proxy implementing T plus
     * NotificationEmitter
     *
     **/
    public static <T> T makeNotificationEmitter(T proxy,
                        Class<T> mbeanInterface) {
        if (proxy instanceof NotificationEmitter)
            return proxy;
        if (proxy == null) return null;
        if (!(proxy instanceof Proxy))
            throw new IllegalArgumentException("not a "+Proxy.class.getName());
        final Proxy p = (Proxy) proxy;
        final InvocationHandler handler =
                Proxy.getInvocationHandler(proxy);
        if (!(handler instanceof MBeanServerInvocationHandler))
            throw new IllegalArgumentException("not a JMX Proxy");
        final MBeanServerInvocationHandler h =
                (MBeanServerInvocationHandler)handler;
        final ObjectName name = h.getObjectName();
        final MBeanServerConnection mbs = h.getMBeanServerConnection();
        final boolean isMXBean = h.isMXBean();
        final T newProxy;
        if (isMXBean) 
            newProxy = JMX.newMXBeanProxy(mbs,name,mbeanInterface,true);
        else 
            newProxy = JMX.newMBeanProxy(mbs,name,mbeanInterface,true);
        return newProxy;
    }
    
}
