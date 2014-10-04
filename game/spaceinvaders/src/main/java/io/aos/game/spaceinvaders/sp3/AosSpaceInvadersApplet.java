package io.aos.game.spaceinvaders.sp3;

import io.aos.game.spaceinvaders.sp3.canvas.SpaceInvadersCanvas;

import java.applet.Applet;
import java.awt.BorderLayout;

public class AosSpaceInvadersApplet extends Applet {
    private static final long serialVersionUID = 1L;

    private SpaceInvadersCanvas spaceInvadersCanvas;

    public AosSpaceInvadersApplet() {

        spaceInvadersCanvas = new SpaceInvadersCanvas(this);
        setLayout(new BorderLayout());
        add("Center", spaceInvadersCanvas);

    }

}
