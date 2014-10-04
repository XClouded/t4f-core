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
package io.aos.endpoint.socket.nio.fwk1.nada;

import io.aos.endpoint.socket.nio.fwk1.api.ChannelFacade;
import io.aos.endpoint.socket.nio.fwk1.api.InputHandler;
import io.aos.endpoint.socket.nio.fwk1.api.InputHandlerFactory;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NadaProtocol implements InputHandlerFactory {
    Map<ChannelFacade, NadaUser> users = Collections.synchronizedMap(new HashMap<ChannelFacade, NadaUser>());

    public InputHandler newHandler() throws IllegalAccessException, InstantiationException {
        return new NadaHandler(this);
    }

    void newUser(ChannelFacade facade) {
        NadaUser user = new NadaUser(facade);

        users.put(facade, user);
        user.send(ByteBuffer.wrap((user.getNickName() + "\n").getBytes()));
    }

    void endUser(ChannelFacade facade) {
        users.remove(facade);
    }

    public void handleMessage(ChannelFacade facade, ByteBuffer message) {
        broadcast(users.get(facade), message);
    }

    private void broadcast(NadaUser sender, ByteBuffer message) {
        synchronized (users) {
            for (NadaUser user : users.values()) {
                if (user != sender) {
                    sender.sendTo(user, message);
                }
            }
        }
    }

    private static class NadaUser {
        private final ChannelFacade facade;
        private String nickName;
        private ByteBuffer prefix = null;
        private static int counter = 1;

        public NadaUser(ChannelFacade facade) {
            this.facade = facade;
            setNickName("nick-" + counter++);
        }

        public void send(ByteBuffer message) {
            facade.outputQueue().enqueue(message.asReadOnlyBuffer());
        }

        public void sendTo(NadaUser recipient, ByteBuffer message) {
            recipient.send(prefix);
            recipient.send(message);
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;

            String prefixStr = "[" + nickName + "] ";

            prefix = ByteBuffer.wrap(prefixStr.getBytes());
        }
    }

}
