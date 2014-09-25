package org.spyker.netflixtest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Chapter9Problem4 {

    public static void main(String[] args) {
        LinkedHashSet<String> initialSet = new LinkedHashSet<>(Arrays.asList("a", "b", "c", "d", "e"));
        
        for (HashSet<String> set : getAllSubsets(initialSet)) {
            System.out.println(set);
        }
    }

    // ["a", "b", "c"]
    private static LinkedHashSet<LinkedHashSet<String>> getAllSubsets(LinkedHashSet<String> inSet) {
        if (inSet == null || inSet.size() == 0) {
            return new LinkedHashSet<LinkedHashSet<String>>();
        }
        
        if (inSet.size() == 1) {
            LinkedHashSet<LinkedHashSet<String>> ret = new LinkedHashSet<LinkedHashSet<String>>();
            ret.add(inSet);
            return ret;
        }
        
        LinkedHashSet<String> inSetMinusOne = new LinkedHashSet<String>();
        Iterator<String> it = inSet.iterator();
        String lastItem = null;
        while (it.hasNext()) {
            String item = it.next();
            if (it.hasNext()) {
                inSetMinusOne.add(item);
            }
            else {
                // on the last item, don't copy it
                lastItem = item;
            }
        }
        // inSetsMinusOne = ["a", "b"]
        // lastItem = "c"
        
        LinkedHashSet<String> lastItemAloneSet = new LinkedHashSet<String>();
        lastItemAloneSet.add(lastItem);
        // lastItemAlineSet = ["c"]
        
        LinkedHashSet<LinkedHashSet<String>> inSetMinusOneSets = getAllSubsets(inSetMinusOne);
        // inSetsMinusOneSets = [ [ "a", "b" ], [ "a" ], [ "b" ] ]
        
        LinkedHashSet<LinkedHashSet<String>> retSets = new LinkedHashSet<LinkedHashSet<String>>();
        
        retSets.add(lastItemAloneSet);
        // returnSets = ["c"]
        for (LinkedHashSet<String> set : inSetMinusOneSets) {
            retSets.add(set);
        }
        // returnSets = [ ["c"], [ "a", "b" ], [ "a" ], [ "b" ] ]

        for (LinkedHashSet<String> set : inSetMinusOneSets) {
            // [ "a", "b" ]
            LinkedHashSet<String> newSet = new LinkedHashSet<String>(set);
            newSet.add(lastItem);
            // [ "a", "b", "c" ]
            retSets.add(newSet);
        }
        
        return retSets;
    }
}
