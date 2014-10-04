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
package io.aos.game.spaceinvaders.sp3.exec;

public class Action {

    private GameThread gameThread;

    public Action(GameThread gameThread) {
        this.gameThread = gameThread;
    }

    public void performAction(int key) {

        if (gameThread.getStatus() == 0) {

            switch (key) {

            case 32:
                gameThread.setFirstTime();
                gameThread.init();
                gameThread.changeStatus(2);
                break;
            }

        }

        else if (gameThread.getStatus() == 1) {

            switch (key) {

            case -3:
                gameThread.vaisseauSp.moveLeft();
                break;

            case -4:
                gameThread.vaisseauSp.moveRight();
                break;

            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:

            case 57:
                gameThread.vaisseauSp.tirer(gameThread.vaisseauSp.vaisseau.getWidth() / 2);
                break;

            }
        }

    }
}
