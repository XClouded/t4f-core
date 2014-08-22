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

/**
 * 
 * 
 * Date: 28-oct.-2003
 * Time: 10:42:08
 * .
 */
public class Missile {

    private int sens, posX, posY, vitesse, searchingX, searchingY, active, origine;
    private GameThread ga;
    private SpaceInvadersCanvas ca;

    public Missile( int direction, GameThread game, SpaceInvadersCanvas canvas) {


    this.sens = direction;
    this.ga = game;
    this.ca = canvas;
    // active = est-on en train de tirer ?
    active = 0;
    }

    public void Initialisation(int posX, int posY, int origine, int vitesse) {
    // Sens = -1 si vient du vaisseau, 1 si vient des ennemis
    // Vitesse = avanc�e du tir
    // Origine = num�ro de la colonne qui tire si ennemi, -1 si vaisseau

    this.vitesse = vitesse;
    this.posX = posX;
    this.posY = posY;
    this.origine = origine;
    active = 1;
    }

    public int isActive() {
           return active;
    }

    public void Active() {
          this.active = 1;
    }

    public void Desactive() {
           this.active = 0;
    }

    public int getOrigine() {
           return this.origine;
    }

    public int getPosX() {
           return this.posX;
    }

    public int getPosY() {
           return this.posY;
    }

    public void avance () {
        if ( active == 1) {
        this.posY += this.sens*this.vitesse;
        }
    }

    public void collision() {
        if ( active == 1) {
            if ( origine == -1 ) {
            // tir du vaisseau
            if( posY <= 0 )
                {
                // Si on d�passe l'�cran -> stop
                ga.vaisseauSp.TirFini();
                active = 0;
                }
            }
            else {
            // Tir ennemi
            if ( posY >= ca.getHeight() )
                {
                 // Tir ennemi sort de l'�cran ?
                ga.vaisseauxE.TirFini();
                active = 0;
                }
            }
        }
    }
}
