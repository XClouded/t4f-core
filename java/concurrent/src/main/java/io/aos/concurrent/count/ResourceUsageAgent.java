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
package io.aos.concurrent.count;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.List;

public class ResourceUsageAgent {
    // A Java agent class defines a premain() method to run before main()
    public static void premain(final String args, final Instrumentation inst) {
        // This agent simply registers a shutdown hook to run when the VM exits
        Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    // This code runs when the VM exits
                    try {
                        // Decide where to send our output
                        PrintWriter out;
                        if (args != null && args.length() > 0) 
                            out = new PrintWriter(new FileWriter(args));
                        else
                            out = new PrintWriter(System.err);

                        // Use java.lang.management to query peak thread usage
                        ThreadMXBean tb = ManagementFactory.getThreadMXBean();
                        out.printf("Current thread count: %d%n",
                                   tb.getThreadCount());
                        out.printf("Peak thread count: %d%n",
                                   tb.getPeakThreadCount());

                        // Use java.lang.management to query peak memory usage
                        List<MemoryPoolMXBean> pools = 
                            ManagementFactory.getMemoryPoolMXBeans();
                        for(MemoryPoolMXBean pool: pools) {
                            MemoryUsage peak = pool.getPeakUsage();
                            out.printf("Peak %s memory used: %,d%n",
                                       pool.getName(), peak.getUsed());
                            out.printf("Peak %s memory reserved: %,d%n",
                                       pool.getName(), peak.getCommitted());
                        }

                        // Use the Instrumentation object passed to premain()
                        // to get a list of all classes that have been loaded
                        Class[] loaded = inst.getAllLoadedClasses();
                        out.println("Loaded classes:");
                        for(Class c : loaded) out.println(c.getName());

                        out.close();  // close and flush the output stream
                    }
                    catch(Throwable t) {
                        // Exceptions in shutdown hooks are ignored so
                        // we've got to print this out explicitly
                        System.err.println("Exception in agent: " + t);
                    }
                }
            });
    }
}
