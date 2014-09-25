package org.spyker.netflixtest;

public class Chapter9Problem1 {
    
    private static int[] cached = new int[1000];

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println("20 = " + numPossibilities(20));
        System.out.println("30 = " + numPossibilities(30));
        System.out.println("35 = " + numPossibilities(35));
        System.out.println("36 = " + numPossibilities(36));
        System.out.println("37 = " + numPossibilities(37));
        System.out.println("40 = " + numPossibilities(40));
        System.out.println(numPossibilities(50));
        System.out.println(numPossibilities(60));
    }
    
    private static int numPossibilities(int numStairs) {
        if (numStairs <= 0) throw new UnsupportedOperationException("please use a positive number");
        if (numStairs == 1) return 1;
        if (numStairs == 2) return 2;
        if (numStairs == 3) return 4;
        
        int m1 = cachedVal(numStairs - 1);
        int m2 = cachedVal(numStairs - 2);
        int m3 = cachedVal(numStairs - 3);
        
        return m1 + m2 + m3;
    }
    
    private static int cachedVal(int numStairs) {
        if (cached[numStairs] == 0) {
            int numPoss = numPossibilities(numStairs);
            cached[numStairs] = numPoss;
        }
        return cached[numStairs];
    }

}
