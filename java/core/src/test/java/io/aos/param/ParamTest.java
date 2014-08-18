package io.aos.param;

import org.junit.Test;

/**
 * <p>
 * This class demonstrates the usage of methods simple parameters
 * and the returned values. It uses String and primitive types.
 * </p>
 */
public class ParamTest {
    
    /**
     * Give a string as parameter.
     */
    @Test
    public void testParam() {
        say("hello");
        say("hello again");
    }
    
    /**
     * Give primitive integers as parameter and print 
     * the returned value.
     */
    @Test
    public void testSum() {
        int x = 1;
        int y = 3;
        int z = sum(x, y);
        System.out.println(x + " + " + y + " = " + z);
    }
    
    /**
     * Give primitive integers as parameter and print 
     * the returned value.
     */
    @Test
    public void testDiv() {
        int x = 1;
        int y = 3;
        int z = div(x, y);
        System.out.println(x + " / " + y + " = " + z);
    }
    
    /**
     * Simple method that receives a message {@ String} as parameter
     * and prints it to the Console.
     * 
     * @param message
     */
    private void say(String message) {
        System.out.println(message);
    }

    /**
     * Simple method that receives two int, computes the sum
     * of these and returns the result.
     * 
     * @param x
     * @param y
     * @return the sum of x and y
     */
    private int sum(int x, int y) {
        return x + y;
    }

    /**
     * Simple method that receives two int, computes the division
     * of these and returns the result.
     * 
     * @param x
     * @param y
     * @return the division of x by y
     */
    private int div(int x, int y) {
        if (y == 0) {
            throw new IllegalArgumentException("The divisor may not be null.");
        }
        return x + y;
    }

}
