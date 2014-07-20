package org.aws.sorting;

public class SelectionSort<Item extends Comparable<Item>> {
    public void sort(Item[] array) {
//        int arrayAccesses = 0;
        for (int ii = 0 ; ii < array.length - 1; ii++) {
            Item smallest = array[ii];
//            arrayAccesses++;
            int currentSmallestIndex = ii;
            for (int jj = ii + 1; jj < array.length; jj++) {
                Item current = array[jj];
//                arrayAccesses++;
                if (current.compareTo(smallest) < 0) {
                    smallest = current;
                    currentSmallestIndex = jj;
                }
            }
            if (currentSmallestIndex > ii) {
                swap(array, currentSmallestIndex, ii);
                //arrayAccesses = arrayAccesses + 2;
            }
        }
//        System.out.println("array accesses = " + arrayAccesses);
    }
    
    private void swap(Item[] array, int first, int second) {
        Item temp = array[first];
        array[first] = array[second];
        array[second] = temp;
        
    }
}
