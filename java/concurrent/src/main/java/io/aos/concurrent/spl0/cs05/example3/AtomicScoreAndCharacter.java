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
package io.aos.concurrent.spl0.cs05.example3;

import java.util.concurrent.atomic.*;

public class AtomicScoreAndCharacter {
    public class ScoreAndCharacter {
        private int score, char2type;

        public ScoreAndCharacter(int score, int char2type) {
            this.score = score;
            this.char2type = char2type;
        }

        public int getScore() {
            return score;
        }

        public int getCharacter() {
            return char2type;
        }
    }

    private AtomicReference<ScoreAndCharacter> value;

    public AtomicScoreAndCharacter() {
        this(0, -1);
    }

    public AtomicScoreAndCharacter(int initScore, int initChar) {
        value = new AtomicReference<ScoreAndCharacter>
                    (new ScoreAndCharacter(initScore, initChar));
    }

    public int getScore() {
        return value.get().getScore();
    }

    public int getCharacter() {
        return value.get().getCharacter();
    }

    public void set(int newScore, int newChar) {
        value.set(new ScoreAndCharacter(newScore, newChar));
    }

    public void setScore(int newScore) {
        ScoreAndCharacter origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new ScoreAndCharacter
                         (newScore, origVal.getCharacter());
            if (value.compareAndSet(origVal, newVal)) break;
        }
    }

    public void setCharacter(int newCharacter) {
        ScoreAndCharacter origVal, newVal;

        while (true) {
            origVal = value.get();
            newVal = new ScoreAndCharacter
                         (origVal.getScore(), newCharacter);
            if (value.compareAndSet(origVal, newVal)) break;
        }
    }

    public void setCharacterUpdateScore(int newCharacter) {
        ScoreAndCharacter origVal, newVal;
        int score;

        while (true) {
            origVal = value.get();
            score = origVal.getScore();
            score = (origVal.getCharacter() == -1) ? score : score-1;

            newVal = new ScoreAndCharacter (score, newCharacter);
            if (value.compareAndSet(origVal, newVal)) break;
        }
    }

    public boolean processCharacter(int typedChar) {
        ScoreAndCharacter origVal, newVal;
        int origScore, origCharacter;
        boolean retValue;

        while (true) {
            origVal = value.get();
            origScore = origVal.getScore();
            origCharacter = origVal.getCharacter();

            if (typedChar == origCharacter) {
                origCharacter = -1;
                origScore++;
                retValue = true;
            } else {
                origScore--;
                retValue = false;
            }

            newVal = new ScoreAndCharacter(origScore, origCharacter);
            if (value.compareAndSet(origVal, newVal)) break;
        }
        return retValue;
    }
}
