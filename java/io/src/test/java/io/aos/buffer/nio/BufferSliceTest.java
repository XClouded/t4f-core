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

package io.aos.buffer.nio;

import java.nio.CharBuffer;

/**
 * Test buffer slice.
 *
 * Created May 2002
 * 
 * @version $Id: BufferSlice.java,v 1.1 2002/05/09 01:38:13 ron Exp $
 */
public class BufferSliceTest
{
	public static void main (String [] argv)
		throws Exception
	{
		CharBuffer buffer = CharBuffer.allocate (8);
		buffer.position (3).limit (5);
		CharBuffer sliceBuffer = buffer.slice();

		println (buffer);
		println (sliceBuffer);

		char [] myBuffer = new char [100];
		CharBuffer cb = CharBuffer.wrap (myBuffer);

		cb.position(12).limit(21);

		CharBuffer sliced = cb.slice();

		println (cb);
		println (sliced);
	}

	private static void println (CharBuffer cb)
	{
		System.out.println ("pos=" + cb.position() + ", limit="
			+ cb.limit() + ", capacity=" + cb.capacity()
			+ ", arrayOffset=" + cb.arrayOffset());
	}
}
