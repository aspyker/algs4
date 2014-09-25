package org.spyker.netflixtest.scratch;
import java.util.ArrayList;

public class ArrayListOfArrayList {
    public static void main(String args[]) {
        int[] allNums = { 1, 2, 3};
        System.out.println(allSetsHC(allNums, 2));
        
    }
    
    public static ArrayList<ArrayList<Integer>> allSetsHC(int[] setOfNums, int setSize) {
        ArrayList<Integer> one = new ArrayList<Integer>();
        one.add(1);
        one.add(2);
        
        ArrayList<Integer> two = new ArrayList<Integer>();
        two.add(2);
        two.add(3);
        
        ArrayList<Integer> three = new ArrayList<Integer>();
        three.add(2);
        three.add(3);
        
        ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
        ret.add(one);
        ret.add(two);
        ret.add(three);
        
        return ret;
    }
    
}
