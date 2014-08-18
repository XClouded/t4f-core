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
package io.aos.crypto.util;

import java.awt.AWTEventMulticaster;
import java.awt.event.*;

public class Seeder
    implements KeyListener {
  protected byte[] mSeed;
  protected int mBitIndex;
  protected boolean mDone;
  protected char mLastKeyChar;
  protected ActionListener mListenerChain;
  protected Counter mCounter;

  public Seeder(int seedBytes) { reset(seedBytes); }
  
  public void reset(int seedBytes) {
    mSeed = new byte[seedBytes];
    mBitIndex = seedBytes * 8 - 1;
    mDone = false;
    mLastKeyChar = '\0';
    mListenerChain = null;
    mCounter = new Counter();
  }

  public byte[] getSeed() { return mSeed; }
  public int getBitLength() { return mSeed.length * 8; }

  public int getCurrentBitIndex() {
    return mSeed.length * 8 - 1 - mBitIndex;
  }

  public void addActionListener(ActionListener al) {
    mListenerChain = AWTEventMulticaster.add(mListenerChain, al);
  }
  
  public void removeActionListener(ActionListener al) {
    mListenerChain = AWTEventMulticaster.remove(mListenerChain, al);
  }

  public void keyPressed(KeyEvent ke) {}
  public void keyReleased(KeyEvent ke) {}
  public void keyTyped(KeyEvent ke) {
    char keyChar = ke.getKeyChar();
    if (keyChar != mLastKeyChar)
      grabTimeBit();
    mLastKeyChar = keyChar;
  }

  protected void grabTimeBit() {
    if (mDone) return;
    int t = mCounter.getCount();
    int bit = t & 0x0001;

    if (bit != 0) {
      int seedIndex = mBitIndex / 8;
      int shiftIndex = mBitIndex % 8;
      mSeed[seedIndex] |= (bit << shiftIndex);
    }

    mBitIndex--;

    if (mBitIndex < 0) {
      mCounter.stop();
      mBitIndex = 0; // Reset this so getCurrentBitIndex() works.
      mDone = true;

      if (mListenerChain != null) {
        mListenerChain.actionPerformed(
            new ActionEvent(this, 0, "Your seed is ready."));
      }
    }
  }
}
