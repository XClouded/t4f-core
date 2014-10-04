package io.aos.jni;

public class JavaJni {

    static {
        System.loadLibrary("t4f-essentials-c-jni");
    }
    
    private static final long X = 100000000;
    private static final long Y = 50;
    
    public static void main(String[] args) {

        JavaJni app = new JavaJni();

        /* C++ */
        long cppStartTime = System.nanoTime();
        app.zerocpp(X, Y);
        long cppEndTime = System.nanoTime();
        long cppTime = cppEndTime - cppStartTime;
        System.out.println("CPP  zero: " + cppTime);
    
        /* Java */
        long javaStartTime = System.nanoTime();
        app.zerojava(X, Y);
        long javaEndTime = System.nanoTime();
        long javaTime = javaEndTime - javaStartTime;
        System.out.println("JAVA zero: " + javaTime);

    }

    public native void zerocpp(long x, long y);

    private void zerojava(long x, long y) {
        while (y > 0) {
            long z = x;
            while (z > 0) {
                z--;
            }
            y--;
        }
    }

}
