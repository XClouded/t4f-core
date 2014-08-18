package io.aos.filesystem.scanner;

import java.util.Scanner;

public class FileScanner {

    public String getFileAsString(String fileName) {
            return new Scanner(ClassLoader.getSystemResourceAsStream(fileName)).useDelimiter("\\Z").next();
    }
    
}
