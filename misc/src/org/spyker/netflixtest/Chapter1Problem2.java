package org.spyker.netflixtest;

public class Chapter1Problem2 {

    public static void main(String[] args) {
        String initial = "abcd";
        System.out.println(reverseString(initial));
    }

    public static String reverseString(String s) {
        if (s == null)
            return null;
        StringBuffer ret = new StringBuffer(s.length());
        for (int ii = s.length() - 1; ii >= 0; ii--) {
            ret.append(s.charAt(ii));
        }
        return ret.toString();
    }
}
