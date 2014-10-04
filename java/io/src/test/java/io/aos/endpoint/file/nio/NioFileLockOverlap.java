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
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * Test overlapping locks on different file channels.
 *
 * 
 */
public class NioFileLockOverlap
{
	public static void mainputStream(String [] argv)
		throws Exception
	{
		if (argv.length == 0) {
			System.out.println ("Usage: filename");
			return;
		}

		String filename = argv [0];

		RandomAccessFile raf1 = new RandomAccessFile (filename, "rw");
		FileChannel fc1 = raf1.getChannel();

		RandomAccessFile raf2 = new RandomAccessFile (filename, "rw");
		FileChannel fc2 = raf2.getChannel();

		System.out.println ("Grabbing first lock");
		FileLock lock1 = fc1.lock (0L, Integer.MAX_VALUE, false);

		System.out.println ("Grabbing second lock");
		FileLock lock2 = fc2.lock (5, 10, false);

		System.out.println ("Exiting");
	}
}
