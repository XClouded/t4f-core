package io.aos.console;

import java.util.Random;

public class RandomConsole {
    private static final int MAX_X = 10;
    private static final int MAX_Y = 10;
    private static final Random random = new Random();
    private static final String MARKER = "*";

    public static void main(String[] args) {
        int x1 = 0;
        int x2 = 0;
        for (int i=0; i < MAX_X; i++) {
            x2 = random.nextInt(MAX_Y);
            print(x1, x2);
            x1 = x2;
        }

    }

    private static void print(int x1, int x2) {
        if (x1 < x2) {
            for (int i=x1; i < x2; i++) {
                print(i);
            }
        }
        else {
            for (int i=x1; i > x2; i--) {
                print(i);
            }
        }
    }

    private static void print(int x) {
        for (int i=0; i<x; i++) {
            System.out.print(MARKER);
        }
        System.out.println();
    }

}
