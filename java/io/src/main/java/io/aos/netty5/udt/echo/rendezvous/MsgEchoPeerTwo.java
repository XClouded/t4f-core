/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.aos.netty5.udt.echo.rendezvous;

import io.aos.netty5.udt.util.UtilConsoleReporter;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * UDT Message Flow Peer
 * <p>
 * Sends one message when a connection is open and echoes back any received data
 * to the other peer.
 */
public class MsgEchoPeerTwo extends MsgEchoPeerBase {

    private static final Logger log = Logger.getLogger(MsgEchoPeerTwo.class.getName());

    public MsgEchoPeerTwo(final InetSocketAddress self,
            final InetSocketAddress peer, final int messageSize) {
        super(self, peer, messageSize);
    }

    public static void main(final String[] args) throws Exception {
        log.info("init");

        // peer two is reporting metrics
        UtilConsoleReporter.enable(3, TimeUnit.SECONDS);

        final int messageSize = 64 * 1024;

        final InetSocketAddress self = new InetSocketAddress(Config.hostTwo,
                Config.portTwo);

        final InetSocketAddress peer = new InetSocketAddress(Config.hostOne,
                Config.portOne);

        new MsgEchoPeerTwo(self, peer, messageSize).run();

        log.info("done");
    }

}
