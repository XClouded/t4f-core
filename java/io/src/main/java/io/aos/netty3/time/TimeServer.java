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
package io.aos.netty3.time;


import io.aos.netty3.time.hserver.TimeServerHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class TimeServer {
    private static final ChannelGroup allChannels = new DefaultChannelGroup(
            "time-server");
    private final String host;
    private final int port;

    public TimeServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {

        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

        ServerBootstrap bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                 return Channels.pipeline(new TimeServerHandler());
//                return Channels.pipeline(new TimeServerEncoderPojo(),
//                        new TimeServerHandlerPojo());
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
//        bootstrap.setOption("child.reuseAddress", true);

        Channel channel = bootstrap.bind(new InetSocketAddress(host, port));
        allChannels.add(channel);

        waitForShutdownCommand();
        
        System.out.println("Shutting down.");
        ChannelGroupFuture future = allChannels.close();
        future.awaitUninterruptibly();
        factory.releaseExternalResources();
        bootstrap.releaseExternalResources();

    }

    public void waitForShutdownCommand() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ChannelGroup getAllChannels() {
        return allChannels;
    }

    public static void main(String... args) throws Exception {
        String host;
        int port;
        if (args.length != 2) {
            host = "localhost";
            port = 8080;
        } else {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        new TimeServer(host, port).run();
    }

}
