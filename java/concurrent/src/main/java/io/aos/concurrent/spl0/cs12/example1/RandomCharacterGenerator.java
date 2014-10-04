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
package io.aos.concurrent.spl0.cs12.example1;


import io.aos.concurrent.spl0.cs12.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class RandomCharacterGenerator extends Thread implements CharacterSource {
    private char[] chars;
    private int curChar;
    private Random random = new Random();
    private CharacterEventHandler handler;
    private boolean done = true;
    private Lock lock = new ReentrantLock();
    private Condition cv = lock.newCondition();
    private Socket sock;
    private DataInputStream reader;
    private DataOutputStream writer;

    public RandomCharacterGenerator(String host, int port) throws IOException {
        handler = new CharacterEventHandler();
        sock = new Socket(host, port);
        reader = new DataInputStream(sock.getInputStream());
        reader.read();        // Welcome
        writer = new DataOutputStream(sock.getOutputStream());
        getString();
    }

    private synchronized void getString() throws IOException {
        byte b = TypeServerConstants.GET_STRING_REQUEST;
        writer.write(b);
        writer.flush();
        b = (byte) reader.readByte();
        if (b != TypeServerConstants.GET_STRING_RESPONSE) 
            throw new IllegalStateException("Bad recv state " + b);
        String s = reader.readUTF();
        chars = s.toCharArray();
        curChar = 0;
    }

    public int getPauseTime(int minTime, int maxTime) {
        return (int) (minTime + ((maxTime-minTime)*random.nextDouble()));
    }

    public int getPauseTime() {
        return getPauseTime(2000, 5500);
    }

    public void addCharacterListener(CharacterListener cl) {
        handler.addCharacterListener(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {
        handler.removeCharacterListener(cl);
    }

    public void nextCharacter() {
        handler.fireNewCharacter(this,
                                (int) chars[curChar++]);
	if (curChar == chars.length) {
	    try {
	        getString();
	    } catch (IOException ioe) {
	        // Put up a dialog box to alert user of error
	    }
        }
    }

    public void run() {
        try {
            lock.lock();
            while (true) {
                try {
                    if (done) {
                        cv.await();
                    } else {
                        nextCharacter();
                        cv.await(getPauseTime(), TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException ie) {
                    return;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void setDone(boolean b) {
        try {
            lock.lock();
            done = b;

            if (!done) cv.signal();
        } finally {
            lock.unlock();
        }
    }
}
