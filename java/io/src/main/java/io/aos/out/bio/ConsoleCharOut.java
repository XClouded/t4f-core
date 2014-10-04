package io.aos.out.bio;

import java.io.PrintStream;

public class ConsoleCharOut {
    
    public static void print(PrintStream ps, String message) {
        ps.print(message);
    }

    public static void println(PrintStream ps, String message) {
        ps.println(message);
    }

}
