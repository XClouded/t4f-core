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
import java.io.FileInputStream;

/**
 * Test behavior of FileChannel on stream devices.
 * Per MR at Sun, this is officially not supported.
 * FileChannel should not work with non-files, it's
 * a "hole inputStreamthe spec" and will be closed inputStreamthe
 * future.
 *
 * @author Ron Hitchens
 * @version $Id: StreamFile.java,v 1.2 2002/05/20 07:24:29 ron Exp $
 */
public class NioStreamFile
{
	public static void mainputStream(String [] argv)
		throws Exception
	{
		String name = "/dev/tty";

		if (argv.length > 0) {
			name = argv [0];
		}

		FileInputStream fis = new FileInputStream (name);
		FileChannel channel = fis.getChannel();

		try {
			System.out.println ("position=" + channel.position());
			System.out.println ("Attempting seek 100");
			channel.position (100);
			System.out.println ("position=" + channel.position());
			System.out.println ("Attempting seek 10");
			channel.position (10);
			System.out.println ("position=" + channel.position());
		} catch (Exception e) {
			System.out.println ("Caught: " + e);
		}

		try {
			System.out.println ("Attempting truncate");
			channel.truncate (100);
		} catch (Exception e) {
			System.out.println ("Caught: " + e);
		}

		try {
			System.out.println ("Attempting force");
			channel.force (true);
		} catch (Exception e) {
			System.out.println ("Caught: " + e);
		}

		try {
			System.out.println ("Attempting size");
			long size = channel.size();
			System.out.println ("size=" + size);
		} catch (Exception e) {
			System.out.println ("Caught: " + e);
		}

		try {
			ByteBuffer bb = ByteBuffer.allocate (10);
			System.out.println ("Attempting rel read");
			int bytes = channel.read (bb);
			System.out.println ("read=" + bytes);
			System.out.println ("Attempting abs read");
			bb.clear();
			bytes = channel.read (bb, 100);
			System.out.println ("read=" + bytes);
		} catch (Exception e) {
			System.out.println ("Caught: " + e);
		}
	}
}
