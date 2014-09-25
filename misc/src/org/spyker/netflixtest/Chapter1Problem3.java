package org.spyker.netflixtest;

import java.util.HashMap;
import java.util.HashSet;

public class Chapter1Problem3 {
    public static boolean isPermutation(String in, String comp) {
        if (in == null && comp != null) {
            return false;
        }
        if (in == null && comp == null) {
            return true;
        }
        if (in != null && comp == null) {
            return false;
        }
        if (comp.length() > in.length()) {
            return false;
        }
        
        HashMap<Character, Integer> ccI = new HashMap<>();
        
        for (int ii = 0; ii < in.length(); ii++) {
            Integer count = ccI.get(in.charAt(ii));
            if (count == null) {
                count = 0;
            }
            count = count + 1;
            ccI.put(in.charAt(ii), count);
        }
        
        for (int jj = 0; jj < comp.length(); jj++) {
            char c = comp.charAt(jj);
            Integer count = ccI.get(c);
            
            if (count == null || count == 0) {
                return false;
            }
            
            count = count - 1;
            ccI.put(c, count);
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println(isPermutation(null, "a"));
        System.out.println(isPermutation(null, null));
        System.out.println(isPermutation("a", null));
        System.out.println(isPermutation("a", "a"));
        System.out.println(isPermutation("abc", "bca"));
        System.out.println(isPermutation("aabc", "baca"));
        System.out.println(isPermutation("aabc", "bacaz"));
        System.out.println(isPermutation("aabc", "bacz"));
    }
}
