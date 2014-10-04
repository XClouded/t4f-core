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
package io.aos.concurrent.applet;

import java.io.PrintStream;

public class ThreadLister {

    private static void printThreadInfo(PrintStream out, Thread t, String indent) {

        if (t == null) {
            return;
        }
        out.println(indent + "Thread: " + t.getName() + "  Priority: " + t.getPriority()
                + (t.isDaemon() ? " Daemon" : "") + (t.isAlive() ? "" : " Not Alive"));
    }

    // Display info about a thread group and its threads and groups
    private static void list_group(PrintStream out, ThreadGroup g, String indent) {
        if (g == null)
            return;
        int num_threads = g.activeCount();
        int num_groups = g.activeGroupCount();
        Thread[] threads = new Thread[num_threads];
        ThreadGroup[] groups = new ThreadGroup[num_groups];

        g.enumerate(threads, false);
        g.enumerate(groups, false);

        out.println(indent + "Thread Group: " + g.getName() + "  Max Priority: " + g.getMaxPriority()
                + (g.isDaemon() ? " Daemon" : ""));

        for (int i = 0; i < num_threads; i++)
            printThreadInfo(out, threads[i], indent + "    ");
        for (int i = 0; i < num_groups; i++)
            list_group(out, groups[i], indent + "    ");
    }

    // Find the root thread group and list it recursively
    public static void listAllThreads(PrintStream out) {
        ThreadGroup current_thread_group;
        ThreadGroup root_thread_group;
        ThreadGroup parent;

        // Get the current thread group
        current_thread_group = Thread.currentThread().getThreadGroup();

        // Now go find the root thread group
        root_thread_group = current_thread_group;
        parent = root_thread_group.getParent();
        while (parent != null) {
            root_thread_group = parent;
            parent = parent.getParent();
        }

        // And list it, recursively
        list_group(out, root_thread_group, "");
    }

    public static void main(String... args) {
        ThreadLister.listAllThreads(System.out);
    }
}
