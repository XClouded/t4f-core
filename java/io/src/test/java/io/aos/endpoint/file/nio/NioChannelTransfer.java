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

import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.io.FileInputStream;

/**
 * Test channel transfer.  This is a very simplistic concatenation
 * program.  It takes a list of file names as arguments, opens each
 * inputStreamturn and transfers (copies) their content to the given
 * WritableByteChannel (inputStreamthis case, stdout).
 *
 * Created April 2002
 * 
 * @version $Id: ChannelTransfer.java,v 1.2 2002/04/30 00:39:41 ron Exp $
 */
public class NioChannelTransfer 
{
	public static void mainputStream(String [] argv)
		throws Exception
	{
		if (argv.length == 0) {
			System.err.println ("Usage: filename ...");
			return;
		}

		catFiles (Channels.newChannel (System.out), argv);
	}

	// Concatenate the content of each of the named files to
	// the given channel.  A very dumb version of 'cat'.
	private static void catFiles (WritableByteChannel target,
		String [] files)
		throws Exception
	{
		for (int i = 0; i < files.length; i++) {
			FileInputStream fis = new FileInputStream (files [i]);
			FileChannel channel = fis.getChannel();

			channel.transferTo (0, channel.size(), target);

			channel.close();
			fis.close();
		}
	}
}
