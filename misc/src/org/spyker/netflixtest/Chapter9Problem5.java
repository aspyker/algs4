package org.spyker.netflixtest;

import java.util.HashSet;
import java.util.Set;

public class Chapter9Problem5 {
    public static void main(String args[]) {
        System.out.println(perm("foorbar"));
    }
    
    private static Set<String> perm(String input) {
        if (input == null || input.length() == 0) {
            return new HashSet<String>();
        }
        
        if (input.length() == 1) {
            HashSet<String> set = new HashSet<>();
            set.add(input);
            return set;
        }
        
        String minusOneS = input.substring(0, input.length() - 1);
        String lastChar = input.substring(input.length() - 1, input.length());
        
        Set<String> minusOneSets = perm(minusOneS);
        
        Set<String> retStrings = new HashSet<String>();
        
        for (String s : minusOneSets) {
            Set<String> ss = getAll(s, lastChar);
            retStrings.addAll(ss);
        }
        
        return retStrings;
    }
    
    private static Set<String> getAll(String base, String addedChar) {
        HashSet<String> set = new HashSet<>();
        for (int ii = 0; ii <= base.length(); ii++) {
            String first = base.substring(0, ii);
            String second = base.substring(ii, base.length());
            String toAdd = first + addedChar + second;
            set.add(toAdd);
        }
        return set;
    }
}
