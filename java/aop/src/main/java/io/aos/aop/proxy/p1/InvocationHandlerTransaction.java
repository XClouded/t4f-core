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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Simple Transaction proxy that wraps method within a transaction. Note that
 * Transaction context must be transfered explicitly.
 */
public class InvocationHandlerTransaction implements InvocationHandler {
    protected Object delegate;

    public InvocationHandlerTransaction(Object delegate) {
        this.delegate = delegate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
     * java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        Connection conn = null;

        try {
            conn = DaoUtil.getConnection();

            conn.setAutoCommit(false);
            Object result = method.invoke(delegate, args);
            conn.commit();

            return result;

        } catch (InvocationTargetException e) {
            conn.rollback();
            throw e.getTargetException();
        } finally {
            DaoUtil.closeConnection(conn);
        }

    }

}
