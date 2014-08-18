package io.aos.spl;

public class MultiplicationTable {
    private static final String SEPARATOR = "-----------------";

    public static void main(String[] args) {
        for (int i=1; i<10; i++) {
            printTable(i);
        }
    }
    
    public static void printTable(int i) {
        System.out.println("Table " + i);
        System.out.println(SEPARATOR);
        for (int j=1; j<10; j++) {
            System.out.println(i + "x" + j + "=" + i*j);
        }
        System.out.println(SEPARATOR);
    }

}
