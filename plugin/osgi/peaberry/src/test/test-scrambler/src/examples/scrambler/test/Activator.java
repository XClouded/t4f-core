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
package examples.scrambler.test;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import org.ops4j.peaberry.ServiceUnavailableException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

import examples.scrambler.Scramble;

public class Activator implements BundleActivator {

	// plain text to be scrambled...
	final static String TEXT = "This is a simple test of peaberry.";

	@Inject
	Scramble service;

	Thread tester;

	public void start(final BundleContext ctx) throws Exception {

		// inject imported service proxy into the activator
		createInjector(osgiModule(ctx), new ImportModule()).injectMembers(this);

		// quick'n'dirty test thread
		tester = new Thread(new Runnable() {
			public void run() {
				// support cooperative cancellation
				while (Thread.currentThread() == tester) {
					try {
						System.out.println('[' + service.process(TEXT) + ']');
					} catch (final ServiceUnavailableException e) {
						System.err.println("No scrambler service!");
					}
					try {
						Thread.sleep(2000);
					} catch (final InterruptedException e) {
						// wake-up
					}
				}
			}
		});

		tester.start();
	}

	public void stop(final BundleContext ctx) throws Exception {

		// cooperatively stop the thread
		final Thread zombie = tester;
		tester = null;
		zombie.join();
	}
}
