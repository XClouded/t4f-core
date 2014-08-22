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
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Defender {

    public BufferedImage vaisseau, tirV, vaisseauTouche;
    public Missile tirV1;
    private SpaceInvadersCanvas ca;
    private GameThread ga;
    private int vies, posX, posY, tirEnCours, deplace, touche;

    // Constructeur
    public Defender(SpaceInvadersCanvas canvas, GameThread game, int deplacement) {

        ca = canvas;
        ga = game;
        deplace = deplacement;

        // Chargement de l'image du vaisseau
        try {
            vaisseau = ImageIO.read(getClass().getResource("/vaisseau.png"));
            tirV = ImageIO.read(getClass().getResource("/tir.png"));
            vaisseauTouche = ImageIO.read(getClass().getResource("/vaisseauTouche.png"));
        }
        catch (IOException e) {
            System.out.println("Impossible de charger les images");
        }

        tirV1 = new Missile(-1, ga, ca);
    }

    // Initialisation du vaisseau
    public void Initialisation() {
        vies = 3;
        posX = ca.getWidth() / 2 - vaisseau.getWidth() / 2;
        // posY = ca.getHeight() - ca.getHeight()/20 - vaisseau.getHeight()/2;
        posY = ca.getHeight() - vaisseau.getHeight();

        tirEnCours = 0;
        touche = 0;
    }

    public int estTouche() {
        return touche;
    }

    public void toucheMoins() {
        touche--;
    }

    // Fonctions de position
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void moveLeft() {
        // D�passe t'on l'�cran ?
        if (posX > 0) {
            this.setPos(posX - deplace, posY);
        }
    }

    public void moveRight() {
        // Limite de l'�cran ?
        if (posX + vaisseau.getWidth() < ca.getWidth()) {
            this.setPos(posX + deplace, posY);
        }
    }

    // Desactiver les tirs en cas de game over
    public void AnnulerTirs() {
        tirV1.Desactive();
        tirEnCours = 0;
        touche = 0;
    }

    public void TirFini() {
        tirEnCours--;
        tirV1.Desactive();
    }

    public void setPos(int posXVaisseau, int posYVaisseau) {
        this.posX = posXVaisseau;
        this.posY = posYVaisseau;
    }

    // Le vaisseau tire d�j� ?
    public int tirEnCours() {
        return tirEnCours;
    }

    // Etat du vaisseau
    public int getVies() {
        return this.vies;
    }

    public void tirer(int milieuDuVaisseau) {
        if (this.tirEnCours == 0) {
            this.tirEnCours++;
            tirV1.Initialisation(posX + vaisseau.getWidth() / 2, posY - tirV.getHeight(), -1, 7);
        }
    }

    public void perdVie() {
        this.vies--;
        touche = 5;

        if (vies == 0) {
            ga.GameOver();
            ga.changeStatus(3);
        }
    }

    public void collision() {

        if (ga.vaisseauxE.tirE1.isActive() == 1) {
            if (ga.vaisseauxE.tirE1.getPosY() + ga.vaisseauxE.tirE.getHeight() >= posY) {
                if ((posX <= ga.vaisseauxE.tirE1.getPosX())
                        && (posX + ga.vaisseauSp.vaisseau.getWidth() >= ga.vaisseauxE.tirE1.getPosX())) {
                    // Le vaisseau est touch�, fin du tir
                    ga.vaisseauxE.TirFini();
                    ga.vaisseauxE.tirE1.Desactive();
                    perdVie();
                }
            }
        }

        if (ga.vaisseauxE.tirE2.isActive() == 1) {
            if (ga.vaisseauxE.tirE2.getPosY() + ga.vaisseauxE.tirE.getHeight() >= posY) {
                if ((posX <= ga.vaisseauxE.tirE2.getPosX())
                        && (posX + ga.vaisseauSp.vaisseau.getWidth() >= ga.vaisseauxE.tirE2.getPosX())) {
                    // Le vaisseau est touch�, fin du tir
                    ga.vaisseauxE.TirFini();
                    ga.vaisseauxE.tirE2.Desactive();
                    perdVie();
                }
            }
        }
    }
}
