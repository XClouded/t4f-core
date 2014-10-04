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
package io.aos.netty3.framing;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class EchoServerHandler extends SimpleChannelHandler {

	protected Channel outboundChannel = null;

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {

		// Connect to the logging server
		InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 9090);

		ClientSocketChannelFactory clientFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap outboundClientBootstrap = new ClientBootstrap(clientFactory);
		outboundClientBootstrap.setOption("connectTimeoutMillis", "30000");
		outboundClientBootstrap.getPipeline().addLast("handler", new DiscardServerHandler());
		ChannelFuture outboundClientFuture = outboundClientBootstrap.connect(address);

		outboundClientFuture.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture outboundClientFuture)
					throws Exception {
				if (outboundClientFuture.isSuccess()) {
					outboundChannel = outboundClientFuture.getChannel();
				}
			}
	      });
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        ChannelBuffer buf = (ChannelBuffer) e.getMessage();
		System.out.println("EchoServer Received: " + buf.readableBytes());

		// Echo the message
		Channel ch = e.getChannel();
		ch.write(e.getMessage());

		// Forward the message to the logging
		Channels.write(outboundChannel, e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();

		Channel ch = e.getChannel();
		ch.close();
	}
}
