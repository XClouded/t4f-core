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
import io.aos.endpoint.socket.nio.fwk1.api.InputQueue;

import java.nio.ByteBuffer;

public class NadaHandler implements InputHandler {
    private final NadaProtocol protocol;

    public NadaHandler(NadaProtocol protocol) {
        this.protocol = protocol;
    }

    // Implementation of the InputHandler interface
    public ByteBuffer nextMessage(ChannelFacade channelFacade) {
        InputQueue inputQueue = channelFacade.inputQueue();
        int nlPos = inputQueue.indexOf((byte) '\n');

        if (nlPos == -1)
            return null;

        if ((nlPos == 1) && (inputQueue.indexOf((byte) '\r') == 0)) {
            inputQueue.discardBytes(2); // eat CR/NL by itself
            return null;
        }

        return (inputQueue.dequeueBytes(nlPos + 1));
    }

    public void handleInput(ByteBuffer message, ChannelFacade channelFacade) {
        protocol.handleMessage(channelFacade, message);
    }

    public void starting(ChannelFacade channelFacade) {
        // System.out.println ("NadaHandler: starting");
    }

    public void started(ChannelFacade channelFacade) {
        protocol.newUser(channelFacade);
    }

    public void stopping(ChannelFacade channelFacade) {
        // System.out.println ("NadaHandler: stopping");
    }

    public void stopped(ChannelFacade channelFacade) {
        protocol.endUser(channelFacade);
    }
}
