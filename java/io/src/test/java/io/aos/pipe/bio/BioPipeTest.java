package io.aos.pipe.bio;

import io.aos.pipe.bio.BioPipe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * Usage: java StreamTube [-ahdsilfx] [-little] [-gzip|-deflated] [-password
 * password] file1...")
 */
public class BioPipeTest {

    @Test
    public void test() {

        String[] args = new String[] {};

        boolean bigEndian = true;
        int firstFile = 0;
        int mode = BioPipe.ASC;
        boolean deflated = false;
        boolean gzipped = false;
        String password = null;

        // Process command-line switches.
        for (firstFile = 0; firstFile < args.length; firstFile++) {
            if (!args[firstFile].startsWith("-"))
                break;
            if (args[firstFile].equals("-h"))
                mode = BioPipe.HEX;
            else if (args[firstFile].equals("-d"))
                mode = BioPipe.DEC;
            else if (args[firstFile].equals("-s"))
                mode = BioPipe.SHORT;
            else if (args[firstFile].equals("-i"))
                mode = BioPipe.INT;
            else if (args[firstFile].equals("-l"))
                mode = BioPipe.LONG;
            else if (args[firstFile].equals("-f"))
                mode = BioPipe.FLOAT;
            else if (args[firstFile].equals("-x"))
                mode = BioPipe.DOUBLE;
            else if (args[firstFile].equals("-little"))
                bigEndian = false;
            else if (args[firstFile].equals("-deflated") && !gzipped)
                deflated = true;
            else if (args[firstFile].equals("-gzip") && !deflated)
                gzipped = true;
            else if (args[firstFile].equals("-password")) {
                password = args[firstFile + 1];
                firstFile++;
            }
        }

        for (int i = firstFile; i < args.length; i++) {
            try {
                InputStream inputStream = new FileInputStream(args[i]);
                BioPipe.transport(inputStream, System.out, mode, bigEndian, deflated, gzipped, password);
                if (i < args.length - 1) { // more files to dump
                    System.out.println();
                    System.out.println("--------------------------------------");
                    System.out.println();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}
