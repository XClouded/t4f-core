package io.aos.game.spaceinvaders.sp2;

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {

    public static void main(String[] args) {
        new SpaceInvaders();
    }

    public SpaceInvaders() {
        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

}
