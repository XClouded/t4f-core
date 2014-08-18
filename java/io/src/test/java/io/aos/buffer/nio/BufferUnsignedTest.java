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

import java.nio.ByteBuffer;

/**
 * Utility class to get and put unsigned values to a ByteBuffer object.
 * All methods here are static and take a ByteBuffer object argument.
 * Since java does not provide unsigned primitive types, each unsigned
 * value read from the buffer is promoted up to the next bigger primitive
 * data type.  getUnsignedByte() returns a short, getUnsignedShort() returns
 * an int and getUnsignedInt() returns a long.  There is no getUnsignedLong()
 * since there is no primitive type to hold the value returned.  If needed,
 * methods returning BigInteger could be implemented.
 * Likewise, the put methods take a value larger than the type they will
 * be assigning.  putUnsignedByte takes a short argument, etc.
 *
 * 
 * @version $Id: Unsigned.java,v 1.1 2002/02/12 22:06:44 ron Exp $
 */
public class BufferUnsignedTest
{
	public static short getUnsignedByte (ByteBuffer bb)
	{
		return ((short)(bb.get() & 0xff));
	}

	public static void putUnsignedByte (ByteBuffer bb, int value)
	{
		bb.put ((byte)(value & 0xff));
	}

	public static short getUnsignedByte (ByteBuffer bb, int position)
	{
		return ((short)(bb.get (position) & (short)0xff));
	}

	public static void putUnsignedByte (ByteBuffer bb, int position,
		int value)
	{
		bb.put (position, (byte)(value & 0xff));
	}

	// ---------------------------------------------------------------

	public static int getUnsignedShort (ByteBuffer bb)
	{
		return (bb.getShort() & 0xffff);
	}

	public static void putUnsignedShort (ByteBuffer bb, int value)
	{
		bb.putShort ((short)(value & 0xffff));
	}

	public static int getUnsignedShort (ByteBuffer bb, int position)
	{
		return (bb.getShort (position) & 0xffff);
	}

	public static void putUnsignedShort (ByteBuffer bb, int position,
		int value)
	{
		bb.putShort (position, (short)(value & 0xffff));
	}

	// ---------------------------------------------------------------

	public static long getUnsignedInt (ByteBuffer bb)
	{
		return ((long)bb.getInt() & 0xffffffffL);
	}

	public static void putUnsignedInt (ByteBuffer bb, long value)
	{
		bb.putInt ((int)(value & 0xffffffffL));
	}

	public static long getUnsignedInt (ByteBuffer bb, int position)
	{
		return ((long)bb.getInt (position) & 0xffffffffL);
	}

	public static void putUnsignedInt (ByteBuffer bb, int position,
		long value)
	{
		bb.putInt (position, (int)(value & 0xffffffffL));
	}

	// ---------------------------------------------------

	public static void main (String [] argv)
		throws Exception
	{
		ByteBuffer buffer = ByteBuffer.allocate (20);

		buffer.clear();
		BufferUnsignedTest.putUnsignedByte (buffer, 255);
		BufferUnsignedTest.putUnsignedByte (buffer, 128);
		BufferUnsignedTest.putUnsignedShort (buffer, 0xcafe);
		BufferUnsignedTest.putUnsignedInt (buffer, 0xcafebabe);

		for (int i = 0; i < 8; i++) {
			System.out.println ("" + i + ": "
				+ Integer.toHexString ((int)getUnsignedByte (buffer, i)));
		}

		System.out.println ("2: " + Integer.toHexString (getUnsignedShort (buffer, 2)));
		System.out.println ("4: " + Long.toHexString (getUnsignedInt (buffer, 4)));
	}
}
