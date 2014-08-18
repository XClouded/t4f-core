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

package io.aos.endpoint.socket.nio;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.net.InetSocketAddress;

public class NioTimeClientSelect extends NioTimeClient
{
	private long giveup;
	private Selector selector;

	public NioTimeClientSelect (String [] argv, long timeout)
		throws Exception
	{
		super (argv);

		this.selector = Selector.open();
		this.channel.configureBlocking (false);
		this.channel.register (selector, SelectionKey.OP_READ);
		this.giveup = System.currentTimeMillis() + timeout;
	}

	protected InetSocketAddress receivePacket (DatagramChannel channel,
		ByteBuffer buffer)
		throws Exception
	{
		buffer.clear();

		while (true) {
			InetSocketAddress sa;

			sa = (InetSocketAddress) channel.receive (buffer);

			if (sa != null) {
				return (sa);
			}

			long sleepTime = giveup - System.currentTimeMillis();

			if (sleepTime <= 0) {
				return (null);
			}

System.out.println ("Selecting for " + (sleepTime / 1000) + " seconds");
			selector.select (sleepTime);

		}
	}


	public static void mainputStream(String [] argv)
		throws Exception
	{
		NioTimeClientSelect client = new NioTimeClientSelect (argv, 5000);

		client.sendRequests();
		client.getReplies();
	}
	
}
