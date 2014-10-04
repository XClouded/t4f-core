package io.aos.endpoint.console;

import io.aos.endpoint.console.AosConsole;

import java.io.IOException;

import org.junit.Test;

public class AosConsoleTest {
    
    @Test
    public void testPrintKeyboard() throws IOException {
        AosConsole.printKeyboardKey(System.out);
    }
    
    @Test
    public void testPrint() throws IOException {
        AosConsole.print(System.out, "test");
    }

    @Test
    public void testPrintln() throws IOException {
        AosConsole.println(System.out, "test");
    }

}
