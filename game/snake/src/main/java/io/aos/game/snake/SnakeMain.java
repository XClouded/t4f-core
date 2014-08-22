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
package io.aos.game.snake;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * S - Start Game
 * 
 * P - Pause Game
 * 
 * M - Mute Sound
 * 
 * Cursor Keys - Change Direction
 * 
 */
public class SnakeMain extends Applet implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;

    String copyName = "Snake Pit";
    String copyVers = "Version 1.1";
    String copyInfo = "Copyright";
    String copyLink = "";
    String copyText = copyName + '\n' + copyVers + '\n' + copyInfo + '\n' + copyLink;

    // Thread control variables.

    Thread loopThread;
    Thread loadThread;

    // Constants

    static final int MAX_DELAY = 75; // Milliseconds between screen updates.
    static final int MIN_DELAY = 50;
    static final int DELAY_INCR = 5;

    static final int GRID_WIDTH = 39; // Size of playing field.
    static final int GRID_HEIGHT = 25;
    static final int GRID_SIZE = 16; // Grid size in pixels.

    static final int NUM_LEVELS = 5; // Number of levels.
    static final int NUM_MICE = 6; // Number of active mice.
    static final int NUM_LIVES = 3; // Starting number of lives.

    static final int INIT = 1; // Game states.
    static final int PLAY = 2;
    static final int LEVEL = 3;
    static final int END = 4;
    static final int OVER = 5;

    static final int END_COUNT = 30; // Counter values.
    static final int LEVEL_COUNT = 40;

    static final int TYPE_MASK = 0x00FF0000; // Grid cell types.
    static final int EMPTY = 0x00000000;
    static final int BLOCK = 0x00010000;
    static final int SNAKE = 0x00020000;
    static final int MOUSE = 0x00030000;
    static final int KEY = 0x00040000;

    static final int DIR_MASK = 0x0000FF00; // Grid cell directions.
    static final int NONE = 0x00000000;
    static final int LEFT = 0x00000100;
    static final int RIGHT = 0x00000200;
    static final int UP = 0x00000300;
    static final int DOWN = 0x00000400;

    static final int SHAPE_MASK = 0x000000FF; // Grid cell shapes.
    static final int SQUARE = 0x00000000;
    static final int SNAKEHEAD = 0x00000001;
    static final int SNAKEBODY = 0x00000002;
    static final int SNAKEELB1 = 0x00000003;
    static final int SNAKEELB2 = 0x00000004;
    static final int SNAKETAIL = 0x00000005;
    static final int MOUSEBODY = 0x00000006;
    static final int KEYSHAPE = 0x00000007;

    static final int MOUSE_POINTS = 10; // Scoring values.
    static final int LEVEL_POINTS = 200;
    static final int EXTRA_LIFE = 500;

    // Sizing data.

    int width;
    int height;

    // Game data.

    int score; // Scoring data.
    int highScore;
    int lives;
    int extraLife;

    int level; // Level data.
    int levelTotal;
    int miceNeeded;
    int miceEaten;
    int delay;

    int[][] grid; // Playing field.

    Point[] snake; // Snake data.
    int headPtr;
    int tailPtr;
    int direction;
    int lastDirection;
    boolean lockKeys;

    Point[] mouse; // Mouse data.

    Point key; // Key data.
    boolean keyActive;

    int gameState; // Game state data.
    int levelCounter;
    int endCounter;
    boolean paused;

    // Screen colors.

    Color bgColor = Color.black;
    Color fgColor = Color.white;
    Color blockColor = new Color(0, 0, 153);
    Color fieldColor = new Color(204, 153, 102);
    Color snakeColor = new Color(0, 153, 0);
    Color mouseColor = Color.gray;
    Color keyColor = new Color(204, 102, 102);

    // Basic shapes.

    Polygon snakeHead;
    Polygon snakeBody;
    Polygon snakeTail;
    Polygon snakeElb1;
    Polygon snakeElb2;
    Polygon mouseBody;
    Polygon keyShape;

    // Sound data.

    boolean loaded = false;
    boolean sound;
    AudioClip bonkSound;
    AudioClip munchSound;
    AudioClip squeakSound;
    AudioClip chimeSound;
    AudioClip advanceSound;

    // For tracking the loading of sound clips.

    int clipTotal = 0;
    int clipsLoaded = 0;

    // )ff screen image.

    Dimension offDimension;
    Image offImage;
    Graphics offGraphics;

    // Font data.

    Font font = new Font("Helvetica", Font.BOLD, 12);
    FontMetrics fm = getFontMetrics(font);
    int fontWidth = fm.getMaxAdvance();
    int fontHeight = fm.getHeight();

    public String getAppletInfo() {
        return (copyText);
    }

    public void init() {

        int i;

        // Display copyright information.

        System.out.println(copyText);

        // Set up key event handling and set focus to applet window.

        addKeyListener(this);
        requestFocus();

        // Define the playing grid.

        grid = new int[GRID_WIDTH][GRID_HEIGHT];

        // Initialize basic shapes.

        snakeHead = new Polygon();
        snakeHead.addPoint(4, 15);
        snakeHead.addPoint(3, 14);
        snakeHead.addPoint(1, 12);
        snakeHead.addPoint(1, 8);
        snakeHead.addPoint(3, 6);
        snakeHead.addPoint(3, 4);
        snakeHead.addPoint(6, 1);
        snakeHead.addPoint(9, 1);
        snakeHead.addPoint(12, 4);
        snakeHead.addPoint(12, 6);
        snakeHead.addPoint(14, 8);
        snakeHead.addPoint(14, 12);
        snakeHead.addPoint(12, 14);
        snakeHead.addPoint(11, 15);

        snakeBody = new Polygon();
        snakeBody.addPoint(11, 0);
        snakeBody.addPoint(11, 15);
        snakeBody.addPoint(4, 15);
        snakeBody.addPoint(4, 0);

        snakeElb1 = new Polygon();
        snakeElb1.addPoint(11, 0);
        snakeElb1.addPoint(11, 4);
        snakeElb1.addPoint(15, 4);
        snakeElb1.addPoint(15, 11);
        snakeElb1.addPoint(7, 11);
        snakeElb1.addPoint(4, 8);
        snakeElb1.addPoint(4, 0);

        snakeElb2 = mirror(snakeElb1);

        snakeTail = new Polygon();
        snakeTail.addPoint(11, 0);
        snakeTail.addPoint(8, 15);
        snakeTail.addPoint(7, 15);
        snakeTail.addPoint(4, 0);

        mouseBody = new Polygon();
        mouseBody.addPoint(8, 1);
        mouseBody.addPoint(12, 5);
        mouseBody.addPoint(12, 6);
        mouseBody.addPoint(10, 7);
        mouseBody.addPoint(12, 9);
        mouseBody.addPoint(12, 12);
        mouseBody.addPoint(10, 14);
        mouseBody.addPoint(5, 14);
        mouseBody.addPoint(3, 12);
        mouseBody.addPoint(3, 9);
        mouseBody.addPoint(5, 7);
        mouseBody.addPoint(3, 6);
        mouseBody.addPoint(3, 5);
        mouseBody.addPoint(7, 1);

        keyShape = new Polygon();
        keyShape.addPoint(1, 6);
        keyShape.addPoint(7, 6);
        keyShape.addPoint(9, 4);
        keyShape.addPoint(13, 4);
        keyShape.addPoint(15, 6);
        keyShape.addPoint(15, 10);
        keyShape.addPoint(13, 12);
        keyShape.addPoint(9, 12);
        keyShape.addPoint(7, 10);
        keyShape.addPoint(3, 10);
        keyShape.addPoint(3, 11);
        keyShape.addPoint(0, 11);
        keyShape.addPoint(0, 7);

        // Initialize data.

        highScore = 0;
        sound = true;
        snake = new Point[GRID_WIDTH * GRID_HEIGHT];
        for (i = 0; i < snake.length; i++)
            snake[i] = new Point(-1, -1);
        mouse = new Point[NUM_MICE];
        for (i = 0; i < NUM_MICE; i++)
            mouse[i] = new Point(-1, -1);
        key = new Point(-1, -1);
        lockKeys = false;
        initGame();
        endGame();
        for (i = 0; i < NUM_MICE; i++)
            killMouse(i);
        gameState = INIT;
    }

    public void initGame() {

        // Initialize game data.

        score = 0;
        lives = NUM_LIVES;
        level = 0;
        levelTotal = 0;
        extraLife = EXTRA_LIFE;
        delay = MAX_DELAY;
        paused = false;
        initLevel();
    }

    public void endGame() {

        gameState = OVER;
    }

    public void initLevel() {

        int i;

        // Advance level. Once we have gone thru each, start at the beginning
        // and
        // increase speed.

        level++;
        if (level > NUM_LEVELS) {
            level = 1;
            if (delay > MIN_DELAY)
                delay -= DELAY_INCR;
        }

        // Level total for display.

        levelTotal++;

        // Initialize game data.

        initBlocks();
        initSnake();
        for (i = 0; i < NUM_MICE; i++)
            initMouse(i);
        miceEaten = 0;
        miceNeeded = 3 * (NUM_MICE + NUM_LEVELS - level);
        keyActive = false;
        gameState = PLAY;
    }

    public void endLevel() {

        // Start counter to pause before changing level.

        gameState = LEVEL;
        levelCounter = LEVEL_COUNT;
    }

    public void initLife() {

        // Create a new snake.

        killSnake();
        initSnake();
        gameState = PLAY;
    }

    public void endLife() {

        // Start counter to pause before starting a new snake.

        gameState = END;
        endCounter = END_COUNT;
    }

    public void start() {

        if (loopThread == null) {
            loopThread = new Thread(this);
            loopThread.start();
        }
        if (!loaded && loadThread == null) {
            loadThread = new Thread(this);
            loadThread.start();
        }
    }

    public void stop() {

        if (loopThread != null) {
            loopThread.stop();
            loopThread = null;
        }
        if (loadThread != null) {
            loadThread.stop();
            loadThread = null;
        }
    }

    public void run() {

        int i;
        long startTime;

        // Lower this thread's priority and get the current time.

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        startTime = System.currentTimeMillis();

        // Run thread for loading sounds.

        if (!loaded && Thread.currentThread() == loadThread) {
            loadSounds();
            loaded = true;
            loadThread.stop();
        }

        // This is the main loop.

        while (Thread.currentThread() == loopThread) {

            if (!paused) {

                // Move snake and mice.

                if (gameState == PLAY) {
                    moveSnake();
                    for (i = 0; i < NUM_MICE; i++)
                        moveMouse(i);
                }

                // Check the score and advance high score if necessary.

                if (score > highScore)
                    highScore = score;

                // Add an extra life if score is high enough.

                if (score >= extraLife) {
                    lives++;
                    extraLife += EXTRA_LIFE;
                }

                // See if the snake was killed. If any lives are left, continue
                // with
                // a new one.

                if (gameState == END && --endCounter < 0)
                    if (--lives == 0)
                        endGame();
                    else
                        initLife();

                // If enough mice have been eaten, show the key.

                if (gameState == PLAY && miceEaten == miceNeeded && !keyActive) {
                    if (loaded && sound)
                        chimeSound.play();
                    initKey();
                }

                // If level was completed, go to the next one when the counter
                // finishes.

                if (gameState == LEVEL && --levelCounter < 0)
                    initLevel();
            }

            // Update the screen and set the timer for the next loop.

            repaint();
            try {
                if (gameState == PLAY)
                    startTime += delay;
                else
                    startTime += MIN_DELAY;
                Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
            }
            catch (InterruptedException e) {
                break;
            }
        }
    }

    public void loadSounds() {

        // Load all sound clips by playing and immediately stopping them.

        try {
            bonkSound = getAudioClip(new URL(getCodeBase(), "bonk.au"));
            clipTotal++;
            munchSound = getAudioClip(new URL(getCodeBase(), "munch.au"));
            clipTotal++;
            squeakSound = getAudioClip(new URL(getCodeBase(), "squeak.au"));
            clipTotal++;
            chimeSound = getAudioClip(new URL(getCodeBase(), "chime.au"));
            clipTotal++;
            advanceSound = getAudioClip(new URL(getCodeBase(), "advance.au"));
            clipTotal++;
        }
        catch (MalformedURLException e) {
        }

        try {
            bonkSound.play();
            bonkSound.stop();
            clipsLoaded++;
            repaint();
            Thread.currentThread().sleep(MIN_DELAY);
            munchSound.play();
            munchSound.stop();
            clipsLoaded++;
            repaint();
            Thread.currentThread().sleep(MIN_DELAY);
            squeakSound.play();
            squeakSound.stop();
            clipsLoaded++;
            repaint();
            Thread.currentThread().sleep(MIN_DELAY);
            chimeSound.play();
            chimeSound.stop();
            clipsLoaded++;
            repaint();
            Thread.currentThread().sleep(MIN_DELAY);
            advanceSound.play();
            advanceSound.stop();
            clipsLoaded++;
            repaint();
            Thread.currentThread().sleep(MIN_DELAY);
        }
        catch (InterruptedException e) {
        }
    }

    public void keyPressed(KeyEvent e) {

        char c;

        // Check if any cursor keys have been pressed and set flags.
        // Check if any cursor keys have been pressed and set direction but
        // don't
        // allow a reversal of direction.

        if (!paused && !lockKeys) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && lastDirection != RIGHT)
                direction = LEFT;
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && lastDirection != LEFT && lastDirection != NONE)
                direction = RIGHT;
            else if (e.getKeyCode() == KeyEvent.VK_UP && lastDirection != DOWN)
                direction = UP;
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && lastDirection != UP)
                direction = DOWN;
        }

        // Allow upper or lower case characters for remaining keys.

        c = Character.toLowerCase(e.getKeyChar());

        // 'M' key: toggle sound.

        if (c == 'm') {
            if (sound) {
                bonkSound.stop();
                munchSound.stop();
                squeakSound.stop();
                chimeSound.stop();
                advanceSound.stop();
            }
            sound = !sound;
        }

        // 'P' key: toggle pause mode.

        if (c == 'p')
            paused = !paused;

        // 'S' key: start the game, if not already in progress.

        if (loaded && c == 's' && (gameState == INIT || gameState == OVER))
            initGame();

        // 'HOME' key: jump to web site (undocumented).

        if (e.getKeyCode() == KeyEvent.VK_HOME)
            try {
                getAppletContext().showDocument(new URL(copyLink));
            }
            catch (Exception excp) {
            }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void initBlocks() {

        int i, j;

        // Clear the grid.

        for (i = 0; i < GRID_WIDTH; i++)
            for (j = 0; j < GRID_HEIGHT; j++)
                grid[i][j] = EMPTY | NONE | SQUARE;

        // Add outer walls.

        for (i = 0; i < GRID_WIDTH; i++) {
            grid[i][0] = BLOCK | NONE | SQUARE;
            grid[i][GRID_HEIGHT - 1] = BLOCK | NONE | SQUARE;
        }
        for (j = 1; j < GRID_HEIGHT - 1; j++) {
            grid[0][j] = BLOCK | NONE | SQUARE;
            grid[GRID_WIDTH - 1][j] = BLOCK | NONE | SQUARE;
        }

        // Add inner walls depending on current level.

        if (level == 2 || level == 4) {
            i = GRID_WIDTH / 4;
            for (j = GRID_HEIGHT / 4; j <= GRID_HEIGHT / 2 - 3; j++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
            j = GRID_HEIGHT / 4;
            for (i = GRID_WIDTH / 4; i <= GRID_WIDTH / 2 - 3; i++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
        }

        if (level == 3) {
            i = GRID_WIDTH / 2;
            for (j = 0; j <= 3; j++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
        }

        if (level == 3 || level == 5) {
            j = GRID_HEIGHT / 4;
            for (i = 5; i <= GRID_WIDTH / 4; i++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
            i = GRID_WIDTH / 4;
            for (j = GRID_HEIGHT / 4; j <= GRID_HEIGHT / 2; j++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
            j = GRID_HEIGHT / 2;
            for (i = 0; i <= GRID_WIDTH / 4 - 5; i++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
            }
        }

        if (level == 4) {
            i = 4;
            for (j = GRID_HEIGHT / 2 - 2; j <= GRID_HEIGHT / 2; j++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
            i = GRID_WIDTH / 2;
            for (j = 0; j <= 2; j++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
        }

        if (level == 5) {
            i = 3 * GRID_WIDTH / 8;
            for (j = 4; j <= GRID_HEIGHT / 3 - 1; j++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
            j = GRID_HEIGHT / 3 - 1;
            for (i = 3 * GRID_WIDTH / 8; i <= GRID_WIDTH / 2; i++) {
                grid[i][j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][j] = BLOCK | NONE | SQUARE;
                grid[i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
                grid[GRID_WIDTH - 1 - i][GRID_HEIGHT - 1 - j] = BLOCK | NONE | SQUARE;
            }
        }
    }

    public void initSnake() {

        int i, j;
        int m, n;

        // Create a new snake in the center of the grid and reset cell list.

        i = GRID_WIDTH / 2;
        j = GRID_HEIGHT / 2;

        snake[4].x = i - 2;
        snake[4].y = j;
        snake[3].x = i - 1;
        snake[3].y = j;
        snake[2].x = i - 0;
        snake[2].y = j;
        snake[1].x = i + 1;
        snake[1].y = j;
        snake[0].x = i + 2;
        snake[0].y = j;

        headPtr = 4;
        tailPtr = 0;

        grid[i - 2][j] = SNAKE | LEFT | SNAKEHEAD;
        grid[i - 1][j] = SNAKE | LEFT | SNAKEBODY;
        grid[i][j] = SNAKE | LEFT | SNAKEBODY;
        grid[i + 1][j] = SNAKE | LEFT | SNAKEBODY;
        grid[i + 2][j] = SNAKE | LEFT | SNAKETAIL;

        // Clear snake direction.

        direction = NONE;
        lastDirection = NONE;

        // Relocate any mice that were overwritten.

        for (m = 0; m < NUM_MICE; m++)
            for (n = -2; n <= 2; n++)
                if (mouse[m].x == i + n && mouse[m].y == j)
                    initMouse(m);

        // Relocate key if overwritten.

        if (keyActive)
            for (n = -2; n <= 2; n++)
                if (key.x == i + n && key.y == j)
                    initKey();
    }

    public void moveSnake() {

        Point pt;
        int i, j;
        int k;
        int c;
        int d;

        // Lock cursor keys to prevent the current direction from being changed
        // until we are done processing it.

        lockKeys = true;

        // Move snake's head into the next cell based on current direction.

        pt = snake[headPtr];
        i = pt.x;
        j = pt.y;
        switch (direction) {
        case LEFT:
            i--;
            break;
        case RIGHT:
            i++;
            break;
        case UP:
            j--;
            break;
        case DOWN:
            j++;
            break;
        default:
            lockKeys = false;
            return;
        }

        // Unlock cursor keys and save direction.

        lockKeys = false;
        lastDirection = direction;

        // Skip if no direction given.

        if (direction == NONE)
            return;

        // Get the type of the new cell.

        c = grid[i][j] & TYPE_MASK;

        // Check if we hit a wall or ourselves.

        if (c == BLOCK || c == SNAKE) {
            if (loaded && sound)
                bonkSound.play();
            endLife();
            return;
        }

        // Replace current head with the appropriate body part.

        d = grid[pt.x][pt.y] & DIR_MASK;
        if (d == direction)
            grid[pt.x][pt.y] = SNAKE | d | SNAKEBODY;
        else if (d == LEFT && direction == UP)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB1;
        else if (d == UP && direction == RIGHT)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB1;
        else if (d == RIGHT && direction == DOWN)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB1;
        else if (d == DOWN && direction == LEFT)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB1;
        else if (d == UP && direction == LEFT)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB2;
        else if (d == LEFT && direction == DOWN)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB2;
        else if (d == DOWN && direction == RIGHT)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB2;
        else if (d == RIGHT && direction == UP)
            grid[pt.x][pt.y] = SNAKE | direction | SNAKEELB2;

        // Change the new cell to a snake's head and set it in the cell list.

        grid[i][j] = SNAKE | direction | SNAKEHEAD;
        if (++headPtr >= snake.length)
            headPtr = 0;
        snake[headPtr].x = i;
        snake[headPtr].y = j;

        // If we got a mouse, bump the score and create a new one. Otherwise,
        // move the tail.

        if (c == MOUSE) {
            score += MOUSE_POINTS;
            if (loaded && sound)
                munchSound.play();
            for (k = 0; k < NUM_MICE; k++)
                if (mouse[k].x == i && mouse[k].y == j)
                    initMouse(k);
            miceEaten++;
        }
        else {
            pt = snake[tailPtr];
            grid[pt.x][pt.y] = EMPTY & NONE & SQUARE;
            if (++tailPtr >= snake.length)
                tailPtr = 0;
            pt = snake[tailPtr];
            d = grid[pt.x][pt.y] & DIR_MASK;
            grid[pt.x][pt.y] = SNAKE | d | SNAKETAIL;
        }

        // Check if we got the key. If so, end the current level.

        if (c == KEY) {
            score += LEVEL_POINTS;
            if (loaded && sound)
                advanceSound.play();
            endLevel();
        }
    }

    public void killSnake() {

        Point pt;
        int n;

        // Remove snake from the grid.

        n = headPtr + 1;
        if (n >= snake.length)
            n = 0;
        while (tailPtr != n) {
            pt = snake[tailPtr];
            grid[pt.x][pt.y] = EMPTY | NONE | SQUARE;
            if (++tailPtr >= snake.length)
                tailPtr = 0;
        }
    }

    public void initMouse(int n) {

        int i, j;
        int d;

        // Find an empty cell.

        do {
            i = (int) (Math.random() * GRID_WIDTH);
            j = (int) (Math.random() * GRID_HEIGHT);
        }
        while ((grid[i][j] & TYPE_MASK) != EMPTY);

        // Get a random direction.

        d = (int) (Math.random() * 4) + 1;
        d <<= 8;

        // Save mouse position.

        mouse[n].x = i;
        mouse[n].y = j;

        // Set the cell with mouse data.

        grid[i][j] = MOUSE | d | MOUSEBODY;
    }

    public void moveMouse(int n) {

        int i, j;
        int d;
        int m;

        // Skip move at random.

        if (Math.random() > 0.25)
            return;

        // Skip move if mouse is dead.

        if (mouse[n].x == -1 || mouse[n].y == -1)
            return;

        // Toss in a random squeak.

        if (loaded && sound && Math.random() > 0.975)
            squeakSound.play();

        // Get a random direction.

        d = (int) (Math.random() * 5);
        d <<= 8;

        // Don't allow a reversal of direction.

        m = grid[mouse[n].x][mouse[n].y] & DIR_MASK;
        if ((m == LEFT && d == RIGHT) || (m == RIGHT && d == LEFT) || (m == UP && d == DOWN) || (m == DOWN && d == UP))
            return;

        i = mouse[n].x;
        j = mouse[n].y;
        switch (d) {
        case LEFT:
            i--;
            break;
        case RIGHT:
            i++;
            break;
        case UP:
            j--;
            break;
        case DOWN:
            j++;
            break;
        default:
            return;
        }

        // See if the new cell is empty. If not, skip move.

        if ((grid[i][j] & TYPE_MASK) != EMPTY)
            return;

        // Clear mouse from old cell and move to the new one.

        grid[mouse[n].x][mouse[n].y] = EMPTY | NONE | SQUARE;
        mouse[n].x = i;
        mouse[n].y = j;
        grid[i][j] = MOUSE | d | MOUSEBODY;
    }

    public void killMouse(int n) {

        // Clear mouse from grid and set coordinates to (-1, -1) so we know it
        // is
        // dead.

        grid[mouse[n].x][mouse[n].y] = EMPTY | NONE | SQUARE;
        mouse[n].x = -1;
        mouse[n].y = -1;
    }

    public void initKey() {

        int i, j;

        // Find an empty cell.

        do {
            i = (int) (Math.random() * GRID_WIDTH);
            j = (int) (Math.random() * GRID_HEIGHT);
        }
        while ((grid[i][j] & TYPE_MASK) != EMPTY);

        // Save key position.

        key.x = i;
        key.y = j;

        // Set the cell with key data and set the flag to show it's been added.

        grid[i][j] = KEY | NONE | KEYSHAPE;
        keyActive = true;
    }

    public Polygon translate(Polygon p, int dx, int dy) {

        Polygon polygon;
        int i;

        // Returns the polygon created by translating the given shape.

        polygon = new Polygon();
        for (i = 0; i < p.npoints; i++)
            polygon.addPoint(p.xpoints[i] + dx, p.ypoints[i] + dy);

        return polygon;
    }

    public Polygon rotate(Polygon p, int d) {

        Polygon polygon;
        int i;

        // Returns the polygon created by rotating the given shape in 90 deg.
        // increments.

        polygon = new Polygon();
        for (i = 0; i < p.npoints; i++)
            switch (d) {
            case LEFT:
                polygon.addPoint(p.ypoints[i], (GRID_SIZE - 1) - p.xpoints[i]);
                break;
            case RIGHT:
                polygon.addPoint((GRID_SIZE - 1) - p.ypoints[i], p.xpoints[i]);
                break;
            case DOWN:
                polygon.addPoint((GRID_SIZE - 1) - p.xpoints[i], (GRID_SIZE - 1) - p.ypoints[i]);
                break;
            default:
                polygon.addPoint(p.xpoints[i], p.ypoints[i]);
                break;
            }

        return polygon;
    }

    public Polygon mirror(Polygon p) {

        Polygon polygon;
        int i;

        // Returns the polygon created by mirroring the given shape.

        polygon = new Polygon();
        for (i = 0; i < p.npoints; i++)
            polygon.addPoint((GRID_SIZE - 1) - p.xpoints[i], p.ypoints[i]);

        return polygon;
    }

    public Color fade(Color s, Color e, double pct) {

        int r, g, b;

        // Fade the starting color to the ending color by the given percentage.

        if (pct < 0.0)
            return e;
        if (pct > 1.0)
            return s;

        r = e.getRed() + (int) Math.round(pct * (s.getRed() - e.getRed()));
        g = e.getGreen() + (int) Math.round(pct * (s.getGreen() - e.getGreen()));
        b = e.getBlue() + (int) Math.round(pct * (s.getBlue() - e.getBlue()));

        return (new Color(r, g, b));
    }

    public void update(Graphics g) {

        paint(g);
    }

    public void paint(Graphics g) {

        Dimension d = getSize();
        int width, height;
        int xOff, yOff;
        int i, j;
        int m;
        int n;
        Polygon p;
        String s;
        int x, y;
        int w, h;

        // Create the off screen graphics context, if no good one exists.

        if (offGraphics == null || d.width != offDimension.width || d.height != offDimension.height) {
            offDimension = d;
            offImage = createImage(d.width, d.height);
            offGraphics = offImage.getGraphics();
        }

        // Fill in applet background.

        offGraphics.setColor(bgColor);
        offGraphics.fillRect(0, 0, d.width, d.height);

        // Center game area.

        width = GRID_WIDTH * GRID_SIZE;
        height = GRID_HEIGHT * GRID_SIZE;
        xOff = (d.width - width) / 2;
        yOff = (d.height - (height + 2 * fontHeight)) / 2;
        offGraphics.translate(xOff, yOff);

        // Fill in playing field.

        if (gameState == LEVEL)
            offGraphics.setColor(fade(fieldColor, bgColor, (double) levelCounter / (double) LEVEL_COUNT));
        else
            offGraphics.setColor(fieldColor);
        offGraphics.fillRect(0, 0, GRID_WIDTH * GRID_SIZE, GRID_HEIGHT * GRID_SIZE);

        // Fill in each grid cell with the appropriate shape.

        for (i = 0; i < GRID_WIDTH; i++)
            for (j = 0; j < GRID_HEIGHT; j++)
                switch (grid[i][j] & TYPE_MASK) {

                case EMPTY:
                    break;

                case BLOCK:
                    if (gameState == LEVEL)
                        offGraphics.setColor(fade(blockColor, bgColor, (double) levelCounter / (double) LEVEL_COUNT));
                    else
                        offGraphics.setColor(blockColor);
                    offGraphics.fillRect(i * GRID_SIZE, j * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                    offGraphics.setColor(bgColor);
                    offGraphics.drawRect(i * GRID_SIZE, j * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                    break;

                case SNAKE:
                    n = grid[i][j] & SHAPE_MASK;
                    switch (n) {
                    case SNAKEHEAD:
                        p = snakeHead;
                        break;
                    case SNAKEBODY:
                        p = snakeBody;
                        break;
                    case SNAKEELB1:
                        p = snakeElb1;
                        break;
                    case SNAKEELB2:
                        p = snakeElb2;
                        break;
                    case SNAKETAIL:
                        p = snakeTail;
                        break;
                    default:
                        p = snakeHead;
                        break;
                    }
                    p = translate(rotate(p, grid[i][j] & DIR_MASK), i * GRID_SIZE, j * GRID_SIZE);
                    if (gameState == LEVEL)
                        offGraphics.setColor(fade(snakeColor, bgColor, (double) levelCounter / (double) LEVEL_COUNT));
                    else if (gameState == END || gameState == OVER)
                        offGraphics.setColor(fade(snakeColor, fieldColor, (double) endCounter / (double) END_COUNT));
                    else
                        offGraphics.setColor(snakeColor);
                    offGraphics.fillPolygon(p);
                    offGraphics.drawPolygon(p);
                    offGraphics
                            .drawLine(p.xpoints[p.npoints - 1], p.ypoints[p.npoints - 1], p.xpoints[0], p.ypoints[0]);
                    if (gameState == END || gameState == OVER)
                        offGraphics.setColor(fade(bgColor, fieldColor, (double) endCounter / (double) END_COUNT));
                    else
                        offGraphics.setColor(bgColor);
                    if (n == SNAKEHEAD || n == SNAKETAIL)
                        for (m = 0; m < p.npoints - 1; m++)
                            offGraphics.drawLine(p.xpoints[m], p.ypoints[m], p.xpoints[m + 1], p.ypoints[m + 1]);
                    if (n == SNAKEBODY) {
                        offGraphics.drawLine(p.xpoints[0], p.ypoints[0], p.xpoints[1], p.ypoints[1]);
                        offGraphics.drawLine(p.xpoints[2], p.ypoints[2], p.xpoints[3], p.ypoints[3]);
                    }
                    if (n == SNAKEELB1 || n == SNAKEELB2) {
                        offGraphics.drawLine(p.xpoints[0], p.ypoints[0], p.xpoints[1], p.ypoints[1]);
                        offGraphics.drawLine(p.xpoints[1], p.ypoints[1], p.xpoints[2], p.ypoints[2]);
                        offGraphics.drawLine(p.xpoints[3], p.ypoints[3], p.xpoints[4], p.ypoints[4]);
                        offGraphics.drawLine(p.xpoints[4], p.ypoints[4], p.xpoints[5], p.ypoints[5]);
                        offGraphics.drawLine(p.xpoints[5], p.ypoints[5], p.xpoints[6], p.ypoints[6]);
                    }
                    break;

                case MOUSE:
                    if (gameState == LEVEL)
                        offGraphics.setColor(fade(mouseColor, bgColor, (double) levelCounter / (double) LEVEL_COUNT));
                    else
                        offGraphics.setColor(mouseColor);
                    p = translate(rotate(mouseBody, grid[i][j] & DIR_MASK), i * GRID_SIZE, j * GRID_SIZE);
                    offGraphics.fillPolygon(p);
                    offGraphics.setColor(bgColor);
                    offGraphics.drawPolygon(p);
                    offGraphics
                            .drawLine(p.xpoints[p.npoints - 1], p.ypoints[p.npoints - 1], p.xpoints[0], p.ypoints[0]);
                    break;

                case KEY:
                    p = translate(rotate(keyShape, grid[i][j] & DIR_MASK), i * GRID_SIZE, j * GRID_SIZE);
                    offGraphics.setColor(keyColor);
                    offGraphics.fillPolygon(p);
                    offGraphics.setColor(bgColor);
                    offGraphics.drawPolygon(p);
                    offGraphics
                            .drawLine(p.xpoints[p.npoints - 1], p.ypoints[p.npoints - 1], p.xpoints[0], p.ypoints[0]);
                    break;

                default:
                    break;
                }

        // Outline playing field.

        offGraphics.setColor(fieldColor);
        offGraphics.drawRect(0, 0, GRID_WIDTH * GRID_SIZE, GRID_HEIGHT * GRID_SIZE - 1);

        // Display status and messages.

        offGraphics.setFont(font);

        offGraphics.setColor(fieldColor);
        i = height - 1;
        j = height + 3 * fontHeight / 2;
        offGraphics.drawRect(0, i, width, 2 * fontHeight);

        offGraphics.setColor(fgColor);
        s = "Score: " + score;
        offGraphics.drawString(s, fontWidth, j);
        s = "Level: " + levelTotal;
        offGraphics.drawString(s, (width - fm.stringWidth(s)) / 4, j);
        s = "Lives: " + lives;
        offGraphics.drawString(s, 3 * (width - fm.stringWidth(s)) / 4, j);
        s = "High: " + highScore;
        offGraphics.drawString(s, width - (fontWidth + fm.stringWidth(s)), j);
        if (paused) {
            s = "Paused";
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, j);
        }
        else if (!sound) {
            s = "Muted";
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, j);
        }

        if (gameState == INIT || gameState == OVER) {
            s = copyName;
            offGraphics.setColor(bgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2 + 1, height / 3 - 3 * fontHeight + 1);
            offGraphics.setColor(fgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, height / 3 - 3 * fontHeight);
            s = copyVers;
            offGraphics.setColor(bgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2 + 1, height / 3 - 2 * fontHeight + 1);
            offGraphics.setColor(fgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, height / 3 - 2 * fontHeight);
            s = copyInfo;
            offGraphics.setColor(bgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2 + 1, height / 3 + 1);
            offGraphics.setColor(fgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, height / 3);
            s = copyLink;
            offGraphics.setColor(bgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2 + 1, height / 3 + fontHeight + 1);
            offGraphics.setColor(fgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, height / 3 + fontHeight);
            if (!loaded) {
                s = "Loading sounds...";
                w = 4 * fontWidth + fm.stringWidth(s);
                h = fontHeight;
                x = (width - w) / 2;
                y = 2 * height / 3 - fm.getMaxAscent();
                offGraphics.setColor(fieldColor);
                offGraphics.fillRect(x, y, w, h);
                offGraphics.setColor(fade(fieldColor, bgColor, 0.5));
                if (clipTotal > 0)
                    offGraphics.fillRect(x, y, (int) (w * clipsLoaded / clipTotal), h);
                offGraphics.setColor(bgColor);
                offGraphics.drawRect(x + 1, y + 1, w, h);
                offGraphics.drawString(s, x + 2 * fontWidth + 1, y + fm.getMaxAscent() + 1);
                offGraphics.setColor(fgColor);
                offGraphics.drawRect(x, y, w, h);
                offGraphics.drawString(s, x + 2 * fontWidth, y + fm.getMaxAscent());
            }
            else {
                s = "Game Over - 'S' to Start";
                offGraphics.setColor(bgColor);
                offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2 + 1, 2 * height / 3 + 1);
                offGraphics.setColor(fgColor);
                offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, 2 * height / 3);
            }
        }
        if (gameState == LEVEL) {
            s = "Advancing to Level " + (levelTotal + 1);
            offGraphics.setColor(bgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2 + 1, height / 2 + 1);
            offGraphics.setColor(fgColor);
            offGraphics.drawString(s, (width - fm.stringWidth(s)) / 2, height / 2);
        }

        // Copy the off screen buffer to the screen.

        offGraphics.translate(-xOff, -yOff);
        g.drawImage(offImage, 0, 0, this);
    }

}
