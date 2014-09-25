package org.spyker.netflixtest;

import java.util.HashMap;
import java.util.HashSet;

public class Chapter1Problem4 {
    public static void replace(char[] in, int size) {
        int spaces = 0;
        for (int ii = 0; ii < size - 1; ii++) {
            if (in[ii] == ' ') {
                spaces++;
            }
        }
        
        int newLength = size + spaces*2;
        assert(newLength <= size);
        int orig = size - 1;
        int ii = newLength - 1;
        while (ii >= 0) {
            boolean isSpace = in[orig] == ' ';
            
            if (!isSpace) {
                in[ii] = in[orig];
                orig--;
                ii--;
            }
            else {
                in[ii] = '0';
                in[ii-1] = '2';
                in[ii-2] = '%';
                orig--;
                ii -= 3;
            }
        }
    }
    
    public static void main(String[] args) {
        runtest("a b  ", 3);
        runtest(" a b    ", 4);
    }
    
    private static void runtest(String test, int size) {
        char[] testString = test.toCharArray();
        System.out.print("\"" + new String(testString) + "\" -> ");
        replace(testString, size);
        System.out.println("\"" + new String(testString) + "\"");
    }
}
