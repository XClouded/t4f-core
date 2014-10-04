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
package io.aos.concurrent.util;

public class Barrier {
	private int threads2Wait4;
	private InterruptedException iex;

	public Barrier(int nThreads) {
		threads2Wait4 = nThreads;
	}

	public synchronized int waitForRest() throws InterruptedException {
		int threadNum = --threads2Wait4;

		if (iex != null)
			throw iex;
		if (threads2Wait4 <= 0) {
			notifyAll();
			return threadNum;
		}
		while (threads2Wait4 > 0) {
			if (iex != null)
				throw iex;
			try {
				wait();
			} catch (InterruptedException ex) {
				iex = ex;
				notifyAll();
			}
		}
		return threadNum;
	}

	public synchronized void freeAll() {
		iex = new InterruptedException("Barrier Released by freeAll");
		notifyAll();
	}
}
