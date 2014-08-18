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
package io.aos.aop.proxy.p1;

import io.aos.aop.proxy.p1.DaoUtil;
import io.aos.aop.proxy.p1.InvocationHandlerLogger;
import io.aos.aop.proxy.p1.InvocationHandlerTransaction;
import io.aos.aop.proxy.p1.Ping;
import io.aos.aop.proxy.p1.PingException;
import io.aos.aop.proxy.p1.PingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class PingProxyTest {

    @Test
    public void testLoggingHandler() throws PingException {
        Ping pingImpl = new PingImpl();
        pingImpl.ping();
        InvocationHandler handler = new InvocationHandlerLogger(pingImpl);
        Ping pingProxy = (Ping) Proxy.newProxyInstance(pingImpl.getClass().getClassLoader(), pingImpl.getClass()
                .getInterfaces(), handler);
        pingProxy.ping();
    }

    @Test
    public void testTxnProxyWithThreads() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try {
                        testTxnProxy();
                        testTxnProxy();
                    } catch (PingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; ++i) {
            threads[i].join();
        }
    }

    @Test
    public void testTxnProxy() throws PingException {
        Ping t = new UpdateDbms();
        InvocationHandler handler = new InvocationHandlerTransaction(t);
        Ping proxy = (Ping) Proxy
                .newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), handler);
        proxy.ping();
    }

    @Test
    public void testDecoratedProxies() throws PingException {
        Ping t = new PingImpl();
        InvocationHandler txnHandler = new InvocationHandlerTransaction(t);
        Ping txnProxy = (Ping) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(),
                txnHandler);
        InvocationHandler loggingHandler = new InvocationHandlerLogger(txnProxy);
        Ping proxy = (Ping) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(),
                loggingHandler);
        proxy.ping();
    }

    public static class UpdateDbms implements Ping {

        public void ping() throws PingException {
            Connection conn = DaoUtil.getConnection();
        }

    }

}
