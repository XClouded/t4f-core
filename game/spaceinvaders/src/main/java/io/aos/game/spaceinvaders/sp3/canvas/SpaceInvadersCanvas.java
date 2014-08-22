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
package io.aos.game.spaceinvaders.sp3.canvas;

import io.aos.game.spaceinvaders.sp3.exec.Action;
import io.aos.game.spaceinvaders.sp3.exec.GameThread;
import io.aos.game.spaceinvaders.sp3.model.Wall;
import io.aos.game.spaceinvaders.sp3.util.KeyRepeat;
import io.aos.game.spaceinvaders.sp3.util.KeyRepeater;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpaceInvadersCanvas extends Canvas implements KeyRepeater, KeyListener {
    private static final long serialVersionUID = 1L;

    private BufferedImage fondecran, titre, gameOver, nextLevel, hiScores;
    private int tempX, tempY, temp, tempo1, tempo2, tempo4, tempo5;
    private Color tempo3;
    private int[] tabScores = new int[3];
    private int[] obstacleActif;
    private int[][] obstacleCoord;
    private KeyRepeat keyRepeat = new KeyRepeat(this);
    private GameThread game;
    private Font newFont;
    private String nbVies;
    private Action action;
    private String bestScore = new String();
    public final int width, height;

    public SpaceInvadersCanvas(Applet applet) {

        width = this.getWidth();
        height = this.getHeight();

        addKeyListener(this);

        try {

            titre = ImageIO.read(getClass().getResource("/ecrantitre.png"));

            System.out.println(this.getWidth());

            if (this.getWidth() >= 240) {
                fondecran = ImageIO.read(getClass().getResource("/fond240.png"));
            }
            else if (this.getWidth() > 110) {
                fondecran = ImageIO.read(getClass().getResource("/fond3.png"));
            }
            else {
                fondecran = ImageIO.read(getClass().getResource("/fond.png"));
            }

            gameOver = ImageIO.read(getClass().getResource("/gameover.png"));
            nextLevel = ImageIO.read(getClass().getResource("/niveau.png"));
            hiScores = ImageIO.read(getClass().getResource("/highscores.png"));

        }

        catch (java.io.IOException e) {
            System.err.println("Impossible de charger les images");
        }

        nbVies = new String();

        keyRepeat.setSleepPeriod(20);
        keyRepeat.start();

        game = new GameThread(this);
        game.start();

        action = new Action(game);

        newFont = new Font("Dialog", Font.PLAIN, 12);

    }

    public void paint(Graphics g) {

        g.setFont(newFont);

        if (game.getStatus() == 0) {
            this.clearbackground(g);
            g.drawImage(titre, this.getWidth() / 2 - titre.getWidth() / 2,
                    this.getHeight() / 2 - titre.getHeight() / 2, null);
        }

        else if (game.getStatus() == 3) {
            // Game over
            // temp = 1;
            // bestScore = "Meilleur Score > "+temp;
            this.clearbackground(g);
            g.setColor(Color.WHITE);
            g.drawImage(gameOver, this.getWidth() / 2 - gameOver.getWidth() / 2,
                    this.getHeight() / 2 - gameOver.getHeight() / 2, null);
            g.drawString(bestScore, 5, this.getHeight() / 2 + gameOver.getHeight() / 2);
        }

        else if (game.getStatus() == 1) {
            // Ecran de jeu, soit en action, soit nextLevel
            g.drawImage(fondecran, 0, 0, null);

            // Affichage du vaisseau
            if (game.vaisseauSp.estTouche() > 0) {
                g.drawImage(game.vaisseauSp.vaisseauTouche, game.vaisseauSp.getPosX(), game.vaisseauSp.getPosY(), null);
                game.vaisseauSp.toucheMoins();
            }
            else {
                g.drawImage(game.vaisseauSp.vaisseau, game.vaisseauSp.getPosX(), game.vaisseauSp.getPosY(), null);
            }

            // Affichage des ennemis
            for (int i = 0; i < game.getNbColonnes(); i++) {
                for (int j = 0; j < game.getNbLignes(); j++) {
                    if (game.vaisseauxE.getEnnemiVivant(i, j) == 1) {
                        tempX = game.vaisseauxE.getPosX() + i * game.vaisseauxE.getEcartX() + i
                                * game.vaisseauxE.ennemi.getWidth();
                        tempY = game.vaisseauxE.getPosY() + j * game.vaisseauxE.getEcartY() + j
                                * game.vaisseauxE.ennemi.getHeight();
                        switch (game.vaisseauxE.typeEnnemi(j)) {

                        case 1:
                            g.drawImage(game.vaisseauxE.ennemi, tempX, tempY, null);
                            break;

                        case -1:
                            g.drawImage(game.vaisseauxE.ennemia, tempX, tempY, null);
                            break;

                        case 2:
                            g.drawImage(game.vaisseauxE.ennemi2, tempX, tempY, null);
                            break;

                        case -2:
                            g.drawImage(game.vaisseauxE.ennemi2a, tempX, tempY, null);
                            break;

                        case 3:
                            g.drawImage(game.vaisseauxE.ennemi3, tempX, tempY, null);
                            break;

                        case -3:
                            g.drawImage(game.vaisseauxE.ennemi3a, tempX, tempY, null);
                            break;
                        }
                    }
                }
            }

            // Affichage des obstacles
            dessineObstacle(g, game.obstacle1);
            dessineObstacle(g, game.obstacle2);
            // Affichage des eventuels tirs
            if (game.vaisseauSp.tirEnCours() == 1) {
                g.drawImage(game.vaisseauSp.tirV, game.vaisseauSp.tirV1.getPosX(), game.vaisseauSp.tirV1.getPosY(),
                        null);
            }
            if (game.vaisseauxE.tirE1.isActive() == 1) {
                g.drawImage(game.vaisseauxE.tirE, game.vaisseauxE.tirE1.getPosX(), game.vaisseauxE.tirE1.getPosY(),
                        null);
            }
            if (game.vaisseauxE.tirE2.isActive() == 1) {
                g.drawImage(game.vaisseauxE.tirE, game.vaisseauxE.tirE2.getPosX(), game.vaisseauxE.tirE2.getPosY(),
                        null);
            }
            g.setColor(Color.WHITE);
            g.drawString("score " + game.getScore(), 0, 0);
            nbVies = "| " + game.vaisseauSp.getVies() + " vies";
            // g.drawString(nbVies, this.getWidth() -
            // newFont.stringWidth(nbVies), 0);
            g.drawString(nbVies, 0, 0);
        }
        else if (game.getStatus() == 2) {
            // Changement de niveau
            // this.clearbackground(g);
            g.drawImage(fondecran, 0, 0, null);
            temp = game.changeNiveau().getWidth() + nextLevel.getWidth();
            g.drawImage(nextLevel, (this.getWidth() / 2) - temp / 2, (this.getHeight() / 2) - 5, null);
            g.drawImage(game.changeNiveau(), (this.getWidth() / 2) - (temp / 2) + 2 + nextLevel.getWidth(),
                    (this.getHeight() / 2) - 5, null);
        }

        else if (game.getStatus() == 4) {
            this.clearbackground(g);
            g.drawImage(hiScores, this.getWidth() / 2 - hiScores.getWidth() / 2, 10, null);
            tabScores = game.resultats.getScores();
            g.setColor(Color.WHITE);
            tempo1 = game.getScore();
            tempo2 = 10 + hiScores.getHeight() + 10;

            for (int i = 0; i < 3; i++) {
                if (tabScores[i] == tempo1) {
                    // Nouveau meilleur score du joueur , on l'affiche en rouge
                    g.setColor(Color.RED);
                    g.drawString((i + 1) + " > " + tabScores[i], 5, tempo2);
                    g.setColor(Color.WHITE);
                }

                else {
                    g.drawString((i + 1) + " > " + tabScores[i], 5, tempo2);
                }
                tempo2 += 15;
            }

        }
    }

    // Affichage des obstacles
    public void dessineObstacle(Graphics g, Wall ob) {

        if (ob.getActifs() > 0) {
            tempo3 = g.getColor();
            tempo4 = (width * 4) / 100;
            tempo5 = (height * 4) / 100;
            g.setColor(Color.WHITE);
            obstacleActif = ob.getMatriceActifs();
            obstacleCoord = ob.getMatriceCoord();
            for (int i = 0; i < 6; i++) {
                if (obstacleActif[i] == 1) {
                    g.fillRect(obstacleCoord[i][0], obstacleCoord[i][1], tempo4, tempo5);
                }
            }
        }
        g.setColor(tempo3);
    }

    @Override
    public void action(int key) {
        action.performAction(key);
    }

    protected void keyPressed(int keyCode) {
        keyRepeat.startRepeat(keyCode);
    }

    protected void keyReleased(int keyCode) {
        keyRepeat.stopRepeat(keyCode);
    }

    protected void clearbackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        action(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
