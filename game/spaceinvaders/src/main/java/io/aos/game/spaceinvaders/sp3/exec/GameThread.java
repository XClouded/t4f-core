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

import io.aos.game.spaceinvaders.sp3.canvas.SpaceInvadersCanvas;
import io.aos.game.spaceinvaders.sp3.model.Aliens;
import io.aos.game.spaceinvaders.sp3.model.Defender;
import io.aos.game.spaceinvaders.sp3.model.Wall;
import io.aos.game.spaceinvaders.sp3.util.HiScores;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class GameThread extends Thread {

    private SpaceInvadersCanvas ca;
    public Defender vaisseauSp;
    public Aliens vaisseauxE;
    public Wall obstacle1, obstacle2;
    private BufferedImage[] numeros;
    private int gameStatus, incrementScore, score, nbLignes, nbColonnes, level, firstTime, i, placetemp;
    private int[][] niveaux = new int[10][4];
    private int[] temp, bestScore;
    private long time;
    private final int DELAY = 62;
    public HiScores resultats = new HiScores(this);

    public GameThread(SpaceInvadersCanvas canvas) {

        ca = canvas;

        // Initialisation du nombre d'ennemis
        nbLignes = 4;
        nbColonnes = 6;
        gameStatus = 0;
        temp = new int[4];
        bestScore = new int[2];

        // Chargement des images
        numeros = new BufferedImage[10];
        try {
            numeros[0] = ImageIO.read(getClass().getResource("/1.png"));
            numeros[1] = ImageIO.read(getClass().getResource("/2.png"));
            numeros[2] = ImageIO.read(getClass().getResource("/3.png"));
            numeros[3] = ImageIO.read(getClass().getResource("/4.png"));
            numeros[4] = ImageIO.read(getClass().getResource("/5.png"));
            numeros[5] = ImageIO.read(getClass().getResource("/6.png"));
            numeros[6] = ImageIO.read(getClass().getResource("/7.png"));
            numeros[7] = ImageIO.read(getClass().getResource("/8.png"));
            numeros[8] = ImageIO.read(getClass().getResource("/9.png"));
            numeros[9] = ImageIO.read(getClass().getResource("/10.png"));
        }
        catch (java.io.IOException e) {
            System.err.println("Impossible de charger les images");
        }

        // Initialisation des niveaux
        // premi�re dimension = niveau, deuxi�me = param�tres :
        // 0 = vitesse, 1 = nombre de tirs ennemis simultan�s, 2 = vitesse du
        // premier tir, 3 = vitesse du deuxi�me tir
        niveaux[0][0] = 12;
        niveaux[0][1] = 1;
        niveaux[0][2] = 1;
        niveaux[0][3] = 0;
        niveaux[1][0] = 11;
        niveaux[1][1] = 1;
        niveaux[1][2] = 1;
        niveaux[1][3] = 0;
        niveaux[2][0] = 10;
        niveaux[2][1] = 1;
        niveaux[2][2] = 2;
        niveaux[2][3] = 0;
        niveaux[3][0] = 9;
        niveaux[3][1] = 1;
        niveaux[3][2] = 2;
        niveaux[3][3] = 0;
        niveaux[4][0] = 8;
        niveaux[4][1] = 2;
        niveaux[4][2] = 1;
        niveaux[4][3] = 1;
        niveaux[5][0] = 7;
        niveaux[5][1] = 2;
        niveaux[5][2] = 1;
        niveaux[5][3] = 1;
        niveaux[6][0] = 6;
        niveaux[6][1] = 2;
        niveaux[6][2] = 2;
        niveaux[6][3] = 1;
        niveaux[7][0] = 5;
        niveaux[7][1] = 2;
        niveaux[7][2] = 2;
        niveaux[7][3] = 1;
        niveaux[8][0] = 4;
        niveaux[8][1] = 2;
        niveaux[8][2] = 2;
        niveaux[8][3] = 1;
        niveaux[9][0] = 2;
        niveaux[9][1] = 2;
        niveaux[9][2] = 2;
        niveaux[9][3] = 2;

        // Cr�ation des objets
        vaisseauSp = new Defender(ca, this, 2);
        vaisseauxE = new Aliens(ca, this);
        obstacle1 = new Wall(this, ca, (ca.width * 2) / 10, (ca.height * 8) / 10);
        obstacle2 = new Wall(this, ca, (ca.width * 6) / 10, (ca.height * 8) / 10);
    }

    // Nouvelle partie
    public void init() {
        level = 0;
        score = 0;
        incrementScore = 50;
        vaisseauSp.Initialisation();
        vaisseauxE.Initialisation();
        obstacle1.Initialisation();
        obstacle2.Initialisation();
    }

    public void changeStatus(int change) {
        gameStatus = change;
    }

    public int getStatus() {
        return gameStatus;
    }

    public void setFirstTime() {
        firstTime = 0;
    }

    // Niveau suivant
    public void NextLevel() {
        level++;
        vaisseauSp.AnnulerTirs();
        vaisseauxE.cancelShoots();
        vaisseauxE.Initialisation();
        obstacle1.Initialisation();
        obstacle2.Initialisation();
        this.augmenterIncrement();
    }

    public int[] getParams() {
        return niveaux[level];
    }

    // Arret des tirs en cas de game over
    public void GameOver() {
        resultats.enregistrerScore();
        auDodo(2000);
        gameStatus = 3;
        vaisseauSp.AnnulerTirs();
        vaisseauxE.cancelShoots();
        ca.repaint();
    }

    public void auDodo(int time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {
        }
    }

    public int getScore() {
        return score;
    }

    public void run() {

        // Boucle de jeu
        while (true) {

            time = System.currentTimeMillis();
            if (gameStatus == 1) {
                // 1 : calcul des nouvelles positions

                vaisseauSp.tirV1.avance();
                vaisseauxE.tirE1.avance();
                vaisseauxE.tirE2.avance();
                vaisseauxE.avance();

                // 2 : gestion des collisions
                obstacle1.collision();
                obstacle2.collision();
                vaisseauxE.collision();
                vaisseauSp.collision();
                vaisseauSp.tirV1.collision();
                vaisseauxE.tirE1.collision();
                vaisseauxE.tirE2.collision();

                // 3 : r�affichage

            }

            else if (gameStatus == 2) {
                auDodo(2000);
                gameStatus = 1;
            }

            else if (gameStatus == 3) {
                // On est sur l'�cran de Game Over
                auDodo(3000);

                // Ecran des Hi-Scores
                gameStatus = 4;
                ca.repaint();
                auDodo(4000);
                gameStatus = 0;
            }

            // Controle de la vitesse de rafraichissement : on mise sur 10
            // images/secondes
            time = System.currentTimeMillis() - time;
            if (time < DELAY) {
                auDodo(DELAY - (int) time);
            }
            ca.repaint();
        }

    }

    public BufferedImage changeNiveau() {
        return numeros[level];
    }

    public int getNbLignes() {
        return this.nbLignes;
    }

    public int getNbColonnes() {
        return this.nbColonnes;
    }

    public void augmenterScore() {
        score += incrementScore;
    }

    public void augmenterIncrement() {
        incrementScore += incrementScore / 2;
    }

    public int getLevel() {
        return level;
    }

}
