package org.spyker.netflixtest;

import java.util.HashSet;

public class Chapter1Problem1 {
    public static boolean hasAllUniqueChars1(String input) {
        if (input == null) {
            return true;
        }
        HashSet<Character> seen = new HashSet<>();
        for (int ii = 0; ii < input.length(); ii++) {
            char c = input.charAt(ii);
            if (!seen.contains(c)) {
                seen.add(c);
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    public static boolean hasAllUniqueChars2(String input) {
        if (input == null) {
            return true;
        }
        for (int ii = 0; ii < input.length() - 1; ii++) {
            char c = input.charAt(ii);
            int index = input.indexOf(c, ii + 1);
            if (index != -1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(hasAllUniqueChars1(null));
        System.out.println(hasAllUniqueChars1("abcdefghijklmnopqrstuvwxyz"));
        System.out.println(hasAllUniqueChars1("aaaaaaaaaaaaaaaaaaaaaaaaaa"));
        
        System.out.println(hasAllUniqueChars2(null));
        System.out.println(hasAllUniqueChars2("abcdefghijklmnopqrstuvwxyz"));
        System.out.println(hasAllUniqueChars2("aaaaaaaaaaaaaaaaaaaaaaaaaa"));
        System.out.println(hasAllUniqueChars2("abcdefghijklmnopqrstuvwxyzz"));
    }

}
