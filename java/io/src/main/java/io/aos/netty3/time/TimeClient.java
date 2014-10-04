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


import io.aos.netty3.time.hclient.TimeClientHandlerFrameDynamic;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class TimeClient {
    private final String host;
    private final int port;
    
    public TimeClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void run() {
        
        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

        ClientBootstrap bootstrap = new ClientBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
//              return Channels.pipeline(new TimeClientHandlerFrameNope());
              return Channels.pipeline(new TimeClientHandlerFrameDynamic());
//              return Channels.pipeline(new TimeClientHandlerFramePojo());
//              return Channels.pipeline(new TimeClientHandlerFrameDecoder(), new TimeClientHandlerFrameNo());
//              return Channels.pipeline(new TimeClientHandlerFrameDecoderReplay(), new TimeClientHandlerFrameNope());
//            return Channels.pipeline(new TimeClientHandlerFrameDecoderPojo(), new TimeClientHandlerFramePojo());
            }
        });

        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
//        bootstrap.setOption("reuseAddress", true);

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        future.awaitUninterruptibly();

        if (!future.isSuccess()) {
             System.out.println("future is not a success...");
             future.getCause().printStackTrace();
        }

        future.getChannel().getCloseFuture().awaitUninterruptibly();
        factory.releaseExternalResources();
        
        bootstrap.releaseExternalResources();

    }

    public static void main(String... args) throws Exception {
        String host;
        int port;
        if (args.length != 2) {
            host = "localhost";
            port = 8080;
        }
        else {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        new TimeClient(host, port).run();
    }

}
