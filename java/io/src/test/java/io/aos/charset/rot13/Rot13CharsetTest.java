package io.aos.charset.rot13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.junit.Ignore;
import org.junit.Test;

public class Rot13CharsetTest {

    /**
     * Unit test for the Rot13 Charset. This main() will open and read an input
     * file if named on the command line, or stdin if no args are provided, and
     * write the contents to stdout via the X-ROT13 charset encoding. The
     * "encryption" implemented by the Rot13 algorithm is symmetrical. Feeding
     * in a plain-text file, such as Java source code for example, will output a
     * scrambled version. Feeding the scrambled version back in will yield the
     * original plain-text document.
     */
    @Test
    @Ignore
    public static void test() throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    
        // Create a PrintStream which uses the Rot13 encoding
        PrintStream outputStream = new PrintStream(System.out, false, "X-ROT13");
    
        String s = null;
    
        // Read all input and write it to the output
        // As the data passes through the PrintStream
        // it will be Rot13 encoded.
        while ((s = bufferedReader.readLine()) != null) {
            outputStream.println(s);
        }
    
        outputStream.flush();
    
    }

}
