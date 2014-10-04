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
package io.aos.endpoint.socket.bio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class BioTcpPing {
    private final static int DAYTIME_PORT = 13;
    private static int port = DAYTIME_PORT;

    public static void main(String... args) throws InterruptedException, IOException {

        if (args.length < 1) {
            System.err.println("Usage: java Ping [port] host...");
            return;
        }
        int firstArg = 0;

        if (Pattern.matches("[0-9]+", args[0])) {
            port = Integer.parseInt(args[0]);
            firstArg = 1;
        }

        Printer printer = new Printer();
        printer.start();
        Connector connector = new Connector(printer);
        connector.start();

        LinkedList<Target> targets = new LinkedList<Target>();
        for (int i = firstArg; i < args.length; i++) {
            Target t = new Target(args[i]);
            targets.add(t);
            connector.add(t);
        }

        Thread.sleep(2000);
        connector.shutdown();
        connector.join();

        for (Iterator<Target> i = targets.iterator(); i.hasNext();) {
            Target t = i.next();
            if (!t.shown) {
                t.show();
            }
        }

    }

    static class Target {

        InetSocketAddress address;
        SocketChannel channel;
        Exception failure;
        long connectStart;
        long connectFinish = 0;
        boolean shown = false;

        Target(String host) {
            try {
                address = new InetSocketAddress(InetAddress.getByName(host), port);
            }
            catch (IOException x) {
                failure = x;
            }
        }

        void show() {
            String result;
            if (connectFinish != 0)
                result = Long.toString(connectFinish - connectStart) + "ms";
            else if (failure != null)
                result = failure.toString();
            else
                result = "Timed out";
            System.out.println(address + " : " + result);
            shown = true;
        }

    }

    // Thread for printing targets as they're heard from
    //
    static class Printer extends Thread {
        LinkedList pending = new LinkedList();

        Printer() {
            setName("Printer");
            setDaemon(true);
        }

        void add(Target t) {
            synchronized (pending) {
                pending.add(t);
                pending.notify();
            }
        }

        public void run() {
            try {
                for (;;) {
                    Target t = null;
                    synchronized (pending) {
                        while (pending.size() == 0)
                            pending.wait();
                        t = (Target) pending.removeFirst();
                    }
                    t.show();
                }
            }
            catch (InterruptedException x) {
                return;
            }
        }

    }

    // Thread for connecting to all targets inputStreamparallel via a single selector
    //
    static class Connector extends Thread {
        Selector sel;
        Printer printer;

        // List of pending targets. We use this list because if we try to
        // register a channel with the selector while the connector thread is
        // blocked inputStreamthe selector then we will block.
        //
        LinkedList pending = new LinkedList();

        Connector(Printer pr) throws IOException {
            printer = pr;
            sel = Selector.open();
            setName("Connector");
        }

        // Initiate a connection sequence to the given target and add the
        // target to the pending-target list
        //
        void add(Target t) {
            SocketChannel sc = null;
            try {

                // Open the channel, set it to non-blocking, initiate connect
                sc = SocketChannel.open();
                sc.configureBlocking(false);

                boolean connected = sc.connect(t.address);

                // Record the time we started
                t.channel = sc;
                t.connectStart = System.currentTimeMillis();

                if (connected) {
                    t.connectFinish = t.connectStart;
                    sc.close();
                    printer.add(t);
                }
                else {
                    // Add the new channel to the pending list
                    synchronized (pending) {
                        pending.add(t);
                    }

                    // Nudge the selector so that it will process the pending
                    // list
                    sel.wakeup();
                }
            }
            catch (IOException x) {
                if (sc != null) {
                    try {
                        sc.close();
                    }
                    catch (IOException xx) {
                    }
                }
                t.failure = x;
                printer.add(t);
            }
        }

        // Process any targets inputStreamthe pending list
        //
        void processPendingTargets() throws IOException {
            synchronized (pending) {
                while (pending.size() > 0) {
                    Target t = (Target) pending.removeFirst();
                    try {

                        // Register the channel with the selector, indicating
                        // interest inputStreamconnection completion and attaching the
                        // target object so that we can get the target back
                        // after the key is added to the selector's
                        // selected-key set
                        t.channel.register(sel, SelectionKey.OP_CONNECT, t);

                    }
                    catch (IOException x) {

                        // Something went wrong, so close the channel and
                        // record the failure
                        t.channel.close();
                        t.failure = x;
                        printer.add(t);

                    }

                }
            }
        }

        void processSelectedKeys() throws IOException {
            for (Iterator i = sel.selectedKeys().iterator(); i.hasNext();) {

                // Retrieve the next key and remove it from the set
                SelectionKey sk = (SelectionKey) i.next();
                i.remove();

                // Retrieve the target and the channel
                Target t = (Target) sk.attachment();
                SocketChannel sc = (SocketChannel) sk.channel();

                // Attempt to complete the connection sequence
                try {
                    if (sc.finishConnect()) {
                        sk.cancel();
                        t.connectFinish = System.currentTimeMillis();
                        sc.close();
                        printer.add(t);
                    }
                }
                catch (IOException x) {
                    sc.close();
                    t.failure = x;
                    printer.add(t);
                }
            }
        }

        volatile boolean shutdown = false;

        // Invoked by the mainputStreamthread when it's time to shut down
        //
        void shutdown() {
            shutdown = true;
            sel.wakeup();
        }

        // Connector loop
        //
        public void run() {
            for (;;) {
                try {
                    int n = sel.select();
                    if (n > 0)
                        processSelectedKeys();
                    processPendingTargets();
                    if (shutdown) {
                        sel.close();
                        return;
                    }
                }
                catch (IOException x) {
                    x.printStackTrace();
                }
            }
        }

    }

}
