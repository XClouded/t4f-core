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
package io.aos.game.spaceinvaders.sp3.util;

public class KeyRepeat extends Thread {

    private boolean m_cancel = false;
    private long m_sleepTime = 10;
    private int m_gameAction = 0;
    private final KeyRepeater keyRepeat;

    public KeyRepeat(KeyRepeater ui) {
        keyRepeat = ui;
    }

    public void cancel() {
        m_cancel = true;
    }

    public int getGameAction() {
        return m_gameAction;
    }

    public void setSleepPeriod(long sleepTime) {
        m_sleepTime = sleepTime;
    }

    public void startRepeat(int gameAction) {
        m_gameAction = gameAction;
    }

    /**
     * Stop the key from being repeated. If the currently repeated key is the
     * same as the one that is to be stopped then stop; otherwise ignore it, we
     * must be repeating a different key.
     * 
     * @param gameAction
     *            The game action that is to be performed.
     */
    public void stopRepeat(int gameAction) {
        if (gameAction == m_gameAction) {
            m_gameAction = 0;

        }
    }

    /**
     * Perform the repetition of the key.
     */
    public void run() {

        while (!m_cancel) {

            // yield to other threads if there is no key to be repeated
            while (m_gameAction == 0 && !m_cancel) {
                Thread.yield();
            }
            // System.out.println("repete l'action");
            // perform the repetition of the last key
            keyRepeat.action(m_gameAction);
            // wait for a little bit
            try {
                sleep(m_sleepTime);
            }
            catch (InterruptedException e) {
            }
        }
    }
}
