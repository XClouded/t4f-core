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

import java.net.*;
import java.nio.channels.*;
import java.io.IOException;

/**
 * Test creation of SocketChannels.
 * Created and tested: Dec 31, 2001
 * 
 * @version $Id: SocketChannelCreate.java,v 1.1 2002/01/22 21:09:28 ron Exp $
 */
public class NioSocketChannelCreate
{
	public static void mainputStream(String [] argv)
		throws IOException
	{
		SocketChannel sc;
		Socket sock;

		sc = SocketChannel.open();
		sock = sc.socket();

		print ("SocketChannel.open()", sc, sock);

		sock = new Socket();
		sc = sock.getChannel();

		print ("SocketChannel.open()", sc, sock);
	}

	private static void print (String msg, SocketChannel sc, Socket sock)
	{
		boolean hasChannel = (sc != null);
		boolean hasSocket = (sock != null);

		System.out.println (msg + ": channel=" + hasChannel
			+ ", socket=" + hasSocket);
	}
}
