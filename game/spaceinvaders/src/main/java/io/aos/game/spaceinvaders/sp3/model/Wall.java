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
package io.aos.game.spaceinvaders.sp3.model;

import io.aos.game.spaceinvaders.sp3.canvas.SpaceInvadersCanvas;
import io.aos.game.spaceinvaders.sp3.exec.GameThread;

import java.awt.image.BufferedImage;

public class Wall {

    private int[][] coordonnees;
    private int[] isActive;
    private int actifs, temp;
    private GameThread ga;
    private SpaceInvadersCanvas ca;

    public Wall(GameThread game, SpaceInvadersCanvas canvas, int origineX, int origineY) {

        ga = game;
        ca = canvas;
        isActive = new int[6];
        coordonnees = new int[6][2];

        // Initialisation des coordonnees sur l'�cran de l'obstacle
        coordonnees[0][0] = origineX + pourcent(ca.width, 4);
        coordonnees[0][1] = origineY + pourcent(ca.height, 2);
        coordonnees[1][0] = coordonnees[0][0];
        coordonnees[1][1] = origineY + pourcent(ca.height, 6);

        coordonnees[2][0] = origineX + pourcent(ca.width, 8);
        coordonnees[2][1] = origineY;
        coordonnees[3][0] = coordonnees[2][0];
        coordonnees[3][1] = origineY + pourcent(ca.height, 4);

        coordonnees[4][0] = origineX + pourcent(ca.width, 12);
        coordonnees[4][1] = origineY + pourcent(ca.height, 2);
        coordonnees[5][0] = coordonnees[4][0];
        coordonnees[5][1] = origineY + pourcent(ca.height, 6);
    }

    public void Initialisation() {

        actifs = 6;
        for (int i = 0; i < actifs; i++) {
            isActive[i] = 1;
        }
    }

    public int pourcent(int chiffre, int pourcentage) {
        return (chiffre * pourcentage) / 100;
    }

    public int[] getMatriceActifs() {
        return isActive;
    }

    public int[][] getMatriceCoord() {
        return coordonnees;
    }

    public int getActifs() {
        return actifs;
    }

    public void desactive() {
        actifs = 0;
    }

    public void collision() {
        if (actifs > 0) {
            temp = collisionV(ga.vaisseauSp.tirV1, ga.vaisseauSp.tirV);
            if (temp == 1) {
                ga.vaisseauSp.TirFini();
            }
            temp = collisionE(ga.vaisseauxE.tirE1, ga.vaisseauxE.tirE);
            if (temp == 1) {
                ga.vaisseauxE.TirFini();
            }
            temp = collisionE(ga.vaisseauxE.tirE2, ga.vaisseauxE.tirE);
            if (temp == 1) {
                ga.vaisseauxE.TirFini();
            }

        }
    }

    public int collisionE(Missile t, BufferedImage iT) {
        if (t.isActive() == 1) {
            for (int i = 0; i < 6; i++) {
                if (isActive[i] == 1) {
                    if (t.getPosY() + iT.getHeight() >= coordonnees[i][1]) {
                        if ((coordonnees[i][0] <= t.getPosX())
                                && (coordonnees[i][0] + (ca.width * 4) / 100 >= t.getPosX())) {
                            // L'obstacle est touche, fin du tir
                            // ga.vaisseauxE.TirFini();
                            t.Desactive();
                            actifs--;
                            isActive[i] = 0;
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int collisionV(Missile t, BufferedImage iT) {
        if (t.isActive() == 1) {
            for (int j = 5; j >= 0; j--) {
                if (isActive[j] == 1) {
                    if ((t.getPosY() >= coordonnees[j][1]) || (t.getPosY() + iT.getHeight() <= coordonnees[j][1])) {
                        System.out.println("passe");
                        if ((coordonnees[j][0] <= t.getPosX())
                                && (coordonnees[j][0] + ((ca.width * 4) / 100) + 1 >= t.getPosX())) {
                            System.out.println("passe2");
                            // L'obstacle est touche, fin du tir

                            actifs--;
                            isActive[j] = 0;

                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }
}
