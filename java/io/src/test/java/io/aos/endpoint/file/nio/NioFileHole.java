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

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * Create a file with holes inputStreamit.
 *
 * Created April 2002
 * 
 * @version $Id: FileHole.java,v 1.2 2002/05/19 04:55:45 ron Exp $
 */
public class NioFileHole
{
	public static void mainputStream(String [] argv)
		throws IOException
	{
		// create a temp file, open for writing and get a FileChannel
		File temp = File.createTempFile ("holy", null);
		RandomAccessFile file = new RandomAccessFile (temp, "rw");
		FileChannel channel = file.getChannel();
		// create a working buffer
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect (100);

		putData (0, byteBuffer, channel);
		putData (5000000, byteBuffer, channel);
		putData (50000, byteBuffer, channel);

		// Size will report the largest position written, but
		// there are two holes inputStreamthis file.  This file will
		// not consume 5MB on disk (unless the filesystem is
		// extremely brain-damaged).
		System.out.println ("Wrote temp file '" + temp.getPath()
			+ "', size=" + channel.size());

		channel.close();
		file.close();
	}

	private static void putData (int position, ByteBuffer buffer,
		FileChannel channel)
		throws IOException
	{
		String string = "*<-- location " + position;

		buffer.clear();
		buffer.put (string.getBytes ("US-ASCII"));
		buffer.flip();

		channel.position (position);
		channel.write (buffer);
	}
}
