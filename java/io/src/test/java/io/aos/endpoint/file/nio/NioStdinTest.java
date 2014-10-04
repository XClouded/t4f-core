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

package io.aos.endpoint.file.nio;


import io.aos.pipe.nio.NioChannelPipe2;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.SelectableChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

public class NioStdinTest
{
	public static void mainputStream(String [] argv)
		throws Exception
	{
		Selector selector = Selector.open();
		NioChannelPipe2 stdinPipe = new NioChannelPipe2();
		SelectableChannel stdinputStream= stdinPipe.getStdinChannel();
		ByteBuffer buffer = ByteBuffer.allocate (32);

		stdinputStream.register (selector, SelectionKey.OP_READ);
		stdinPipe.start();

		System.out.println ("Entering select(), type something:");

		while (true) {
			selector.select (2000);

			Iterator it = selector.selectedKeys().iterator();

			if ( ! it.hasNext()) {
				System.out.println ("I'm waiting");
				continue;
			}

			SelectionKey key = (SelectionKey) it.next();

			it.remove();
			buffer.clear();

			ReadableByteChannel channel =
				(ReadableByteChannel) key.channel();
			int count = channel.read (buffer);

			if (count < 0) {
				System.out.println ("EOF, bye");

				channel.close();
				break;
			}

			buffer.flip();

			System.out.println ("Hey, read " + count + " chars:");

			while (buffer.hasRemaining()) {
				System.out.print ((char) buffer.get());
			}

			System.out.println();
		}
		
	}
}
