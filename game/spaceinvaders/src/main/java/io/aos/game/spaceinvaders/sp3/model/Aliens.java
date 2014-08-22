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
import java.util.Random;

import javax.imageio.ImageIO;

public class Aliens {

    private SpaceInvadersCanvas ca;
    private GameThread ga;
    private Missile tempTir1;
    public Missile tirE1, tirE2;
    public BufferedImage ennemi, ennemi2, ennemi3, ennemia, ennemi2a, ennemi3a, tirE;
    private int posX, posY, ecartX, ecartY, searchingX, searchingY, direction, aDescendre, vitesse, nbEnnemis,
            lignesMortesBas, nbLignesDescendues, temp1, temp2, temp3, j, nbTirsMax, tirsEnCours, vitesseAttend,
            tempOrigine, tempVitesse;
    private Random random = new Random(System.currentTimeMillis());
    private int[][] matriceE;
    private int[] typeEnnemis, tempParams;

    public Aliens(SpaceInvadersCanvas canvas, GameThread game) {

        this.ca = canvas;
        this.ga = game;
        this.nbTirsMax = 2;
        this.tirE1 = new Missile(1, ga, ca);
        this.tirE2 = new Missile(1, ga, ca);

        try {
            ennemi = ImageIO.read(getClass().getResource("/ennemi1.png"));
            ennemi2 = ImageIO.read(getClass().getResource("/ennemi2.png"));
            ennemi3 = ImageIO.read(getClass().getResource("/ennemi3.png"));
            ennemi3a = ImageIO.read(getClass().getResource("/ennemi1a.png"));
            ennemi2a = ImageIO.read(getClass().getResource("/ennemi2a.png"));
            ennemia = ImageIO.read(getClass().getResource("/ennemi3a.png"));
            tirE = ImageIO.read(getClass().getResource("/tirennemi.png"));
        }
        catch (IOException e) {
            System.out.println("Erreur de chargement des images");
        }

        // Calcul de l'ecart a l'affichage des ennemis
        // Au depart, les monstres occupent 40% de l'espace vertical, et au
        // total peuvent occuper jusque 80%
        // 10 % d'affichage est reserve en haut au score/nombrede vie, 10% en
        // bas au vaisseau
        ecartX = (ca.getWidth() - (ga.getNbColonnes() * ennemi.getWidth())) / (ga.getNbColonnes() + 1);
        ecartY = ca.height / 10 - ennemi.getHeight();

        // On descend de 9 cases
        aDescendre = (ca.getHeight() * 5) / 90;

        // a chaque ligne correspond un type d'ennemi different
        typeEnnemis = new int[ga.getNbLignes()];

        for (int i = 0; i < ga.getNbLignes(); i++) {
            // 3 types d'ennemis diff�rents
            j = i % 3;
            typeEnnemis[i] = j + 1;
        }

        // Initialisation de la matrice des ennemis
        // la derni�re ligne = nb d'ennemis encore vivants dans la colonne
        // correspondante
        // la derni�re colonne = nb d'ennemis encore vivants sur la ligne
        matriceE = new int[ga.getNbColonnes() + 1][ga.getNbLignes() + 1];

        tempParams = new int[4];
    }

    public void Initialisation() {

        // Initialisation de l'ensemble des variables
        this.nbEnnemis = ga.getNbColonnes() * ga.getNbLignes();

        // matrice des ennemis
        for (int i = 0; i < ga.getNbColonnes(); i++) {
            for (int j = 0; j < ga.getNbLignes(); j++) {
                // ennemi[i][j] = 1 = l'ennemi de la colonne i, ligne j est
                // vivant
                matriceE[i][j] = 1;
                matriceE[ga.getNbColonnes()][j] = ga.getNbColonnes();
            }
            // Nombre d'ennemis vivant dans la colonne
            matriceE[i][ga.getNbLignes()] = ga.getNbLignes();
        }

        posX = ecartX;
        posY = ecartY + (ca.getHeight() / 10);

        this.lignesMortesBas = 0;
        this.nbLignesDescendues = 0;
        this.tirsEnCours = 0;
        tempParams = ga.getParams();
        vitesse = tempParams[0];
        nbTirsMax = tempParams[1];
        this.vitesseAttend = this.vitesse;
        direction = 1;
    }

    // ennemi vivant ?
    public int getEnnemiVivant(int posX, int posY) {
        return matriceE[posX][posY];
    }

    // position
    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    // �cart
    public int getEcartX() {
        return ecartX;
    }

    public int getEcartY() {
        return ecartY;
    }

    public int getRandom() {
        return Math.abs(random.nextInt()) % 2;
    }

    public int getEnnemiRandom() {
        return Math.abs(random.nextInt()) % ga.getNbColonnes();
    }

    public int typeEnnemi(int j) {
        return typeEnnemis[j];
    }

    public void cancelShoots() {
        tirE1.Desactive();
        tirE2.Desactive();
    }

    // Tuer un vaisseau
    public void killEnnemi(int i, int j) {

        matriceE[i][j] = 0;
        nbEnnemis--;

        // D�cr�mentation du nombre d'ennemis vivants dans la colonne et dans la
        // ligne
        matriceE[i][ga.getNbLignes()] -= 1;
        matriceE[ga.getNbColonnes()][j] -= 1;

        // Toute la ligne est morte ?
        if (matriceE[ga.getNbColonnes()][j] == 0) {
            if (ga.getNbLignes() - 1 - this.lignesMortesBas == j) {
                this.lignesMortesBas++;
            }
        }

        // Fin du niveau ?
        if (nbEnnemis == 0) {
            ga.NextLevel();
            ga.changeStatus(2);
        }
    }

    // Comptage des colonnes mortes
    public int getColsMortesGauche() {

        int fini = 0, i = 0;
        while ((fini == 0) && (i < ga.getNbColonnes())) {
            if (matriceE[i][ga.getNbLignes()] == 0) {
                i++;
            }
            else {
                fini = 1;
            }
        }
        return i;
    }

    // Comptage des colonnes mortes
    public int getColsMortesDroite() {

        int fini = 0, i = ga.getNbColonnes() - 1, j = 0;
        while ((fini == 0) && (i >= 0)) {
            if (matriceE[i][ga.getNbLignes()] == 0) {
                j++;
                i--;
            }
            else {
                fini = 1;
            }
        }
        return j;
    }

    public void collision() {

        // Collision avec le tir du vaisseau ?
        if (ga.vaisseauSp.tirV1.isActive() == 1) {
            searchingX = posX;
            for (int i = 0; i < ga.getNbColonnes(); i++) {
                searchingY = posY;
                for (int j = 0; j < ga.getNbLignes(); j++) {
                    if ((ga.vaisseauSp.tirV1.getPosX() >= searchingX)
                            && (ga.vaisseauSp.tirV1.getPosX() <= searchingX + ennemi.getWidth())) {
                        if ((ga.vaisseauSp.tirV1.getPosY() >= searchingY)
                                && (ga.vaisseauSp.tirV1.getPosY() <= searchingY + ennemi.getHeight())
                                && (getEnnemiVivant(i, j) == 1)) {
                            ga.augmenterScore();
                            ga.vaisseauSp.TirFini();
                            killEnnemi(i, j);
                        }
                    }
                    searchingY += ennemi.getHeight() + ecartY;
                }
                searchingX += ennemi.getWidth() + ecartX;
            }
        }

        // Collision avec le bas de l'�cran ?
        if (nbLignesDescendues == 9 + this.lignesMortesBas) {
            ga.GameOver();
        }
    }

    public void avance() {

        if (vitesseAttend == 0) {
            // Collision avec le mur ?
            if ((direction == -1)
                    && (posX <= -(getColsMortesGauche() * ecartX + getColsMortesGauche() * ennemi.getWidth()))) {
                direction = 1;
                posY += aDescendre;
                this.nbLignesDescendues += 1;
            }
            else if ((direction == 1)
                    && (posX + ga.getNbColonnes() * ennemi.getWidth() + (ga.getNbColonnes() - 1) * ecartX
                            - (getColsMortesDroite() * ecartX + getColsMortesDroite() * ennemi.getWidth()) >= ca
                                .getWidth())) {
                direction = -1;
                posY += aDescendre;
                this.nbLignesDescendues += 1;
            }
            else {
                posX += 2 * direction;
            }
            vitesseAttend = vitesse;
            invertSprites();
            tirer();

        }
        else {
            vitesseAttend--;
        }
    }

    public void tirer() {

        if (this.tirsEnCours < nbTirsMax) {
            // On peut tirer, on va g�n�rer un nombre al�atoire pour d�cider si
            // oui ou non
            if (this.getRandom() == 1) {
                // On tire au hasard un num�ro qui est soit 1 soit 0
                // on cherche le slot de tir qui est libre
                if (tirE1.isActive() == 1) {
                    tempTir1 = tirE2;
                    tempOrigine = tirE1.getOrigine();
                    tempVitesse = tempParams[3];
                }
                else if (tirE2.isActive() == 1) {
                    tempTir1 = tirE1;
                    tempOrigine = tirE2.getOrigine();
                    tempVitesse = tempParams[2];
                }
                else {
                    // Aucun des tirs n'est actif, on va prendre le premier
                    tempTir1 = tirE1;
                    tempOrigine = -1;
                    tempVitesse = tempParams[2];
                }

                // On va rechercher une colonne d'ennemi qui ne soit pas encore
                // morte et qui ne tire pas d�j�
                temp2 = getEnnemiRandom();
                temp1 = 0;
                while (((temp2 == tempOrigine) || (matriceE[temp2][ga.getNbLignes()] == 0))
                        && (temp1 <= ga.getNbColonnes())) {
                    temp2 = (temp2 + 1) % ga.getNbLignes();
                    temp1++;
                }

                if (temp1 != ga.getNbColonnes() + 1) {
                    // On va maintenant rechercher l'ennemi le plus bas pouvant
                    // tirer
                    for (int i = ga.getNbLignes() - 1; i >= 0; i--) {
                        if (matriceE[temp2][i] == 1) {

                            // Ennemi vivant
                            temp3 = i;
                            // Sortie de la boucle
                            i = -1;
                        }
                    }

                    // Initialisation du tir
                    tempTir1.Initialisation(
                            this.posX + temp2 * this.ecartX + temp2 * ennemi.getWidth() + ennemi.getWidth() / 2,
                            this.posY + temp3 * this.ecartY + (temp3 + 1) * ennemi.getHeight(), temp2, tempVitesse);
                    tirsEnCours++;
                }
            }
        }
    }

    public void TirFini() {
        tirsEnCours--;
    }

    public int getNbTirsMax() {
        return this.nbTirsMax;
    }

    public void invertSprites() {
        for (int i = 0; i < ga.getNbLignes(); i++) {
            // On inverse les sprites � afficher
            typeEnnemis[i] = -typeEnnemis[i];
        }
    }
}
