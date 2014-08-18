package io.aos.in.bio;

import java.io.IOException;

public class ConsoleCharIn {
    
    public static int key() throws IOException {
       return System.in.read(); 
    }

    public static int waitKey() throws IOException {
        System.out.println("Press a key and after press on ENTER");
        int key = 0;
        while (true) {
            int keyPressed = System.in.read();
            if (keyPressed == 10) {
                break;
            }
            key = keyPressed;
        }
        return key;
    }

}
