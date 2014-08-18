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

public class CondVar {
	private BusyFlag SyncVar;

	public CondVar() {
		this(new BusyFlag());
	}

	public CondVar(BusyFlag sv) {
		SyncVar = sv;
	}

	public void cvWait() throws InterruptedException {
		cvTimedWait(SyncVar, 0);
	}

	public void cvWait(BusyFlag sv) throws InterruptedException {
		cvTimedWait(sv, 0);
	}

	public void cvTimedWait(int millis) throws InterruptedException {
		cvTimedWait(SyncVar, millis);
	}

	public void cvTimedWait(BusyFlag sv, int millis) throws InterruptedException {
		int i = 0;
		InterruptedException errex = null;

		synchronized (this) {
			// You must own the lock in order to use this method
			if (sv.getBusyFlagOwner() != Thread.currentThread()) {
				throw new IllegalMonitorStateException("current thread not owner");
			}

			// Release the lock (Completely)
			while (sv.getBusyFlagOwner() == Thread.currentThread()) {
				i++;
				sv.freeBusyFlag();
			}

			// Use wait() method
			try {
				if (millis == 0) {
					wait();
				} else {
					wait(millis);
				}
			} catch (InterruptedException iex) {
				errex = iex;
			}
		}

		// Obtain the lock (Return to original state)
		for (; i > 0; i--) {
			sv.getBusyFlag();
		}

		if (errex != null)
			throw errex;
		return;
	}

	public void cvSignal() {
		cvSignal(SyncVar);
	}

	public synchronized void cvSignal(BusyFlag sv) {
		// You must own the lock in order to use this method
		if (sv.getBusyFlagOwner() != Thread.currentThread()) {
			throw new IllegalMonitorStateException("current thread not owner");
		}
		notify();
	}

	public void cvBroadcast() {
		cvBroadcast(SyncVar);
	}

	public synchronized void cvBroadcast(BusyFlag sv) {
		// You must own the lock in order to use this method
		if (sv.getBusyFlagOwner() != Thread.currentThread()) {
			throw new IllegalMonitorStateException("current thread not owner");
		}
		notifyAll();
	}
}
