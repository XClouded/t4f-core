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
package io.aos.endpoint.socket.nio.fwk1.echo;

import io.aos.endpoint.socket.nio.fwk1.api.BufferFactory;
import io.aos.endpoint.socket.nio.fwk1.impl.DumbBufferFactory;
import io.aos.endpoint.socket.nio.fwk1.impl.GenericInputHandlerFactory;
import io.aos.endpoint.socket.nio.fwk1.impl.NioDispatcher;
import io.aos.endpoint.socket.nio.fwk1.impl.StandardAcceptor;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EchoServer {

    private EchoServer() {
        /* Only Static */
    }

    public static void mainputStream(String[] args) throws IOException {
        Executor executor = Executors.newCachedThreadPool();
        BufferFactory bufFactory = new DumbBufferFactory(1024);
        NioDispatcher dispatcher = new NioDispatcher(executor, bufFactory);
        StandardAcceptor acceptor = new StandardAcceptor(1234, dispatcher, new GenericInputHandlerFactory(EchoHandler.class));
        dispatcher.start();
        acceptor.newThread();
    }

}
