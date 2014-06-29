package org.aws.sorting;

public class InsertionSort<Item extends Comparable<Item>> {
    public void sort(Item[] array) {
        int arrayAccesses = 0;
        for (int ii = 1 ; ii < array.length; ii++) {
            for (int jj = ii; jj > 0; jj--) {
                Item jjItem = array[jj];
                Item leftOfJJItem = array[jj - 1];
                arrayAccesses+=2;
                if (jjItem.compareTo(leftOfJJItem) < 0) {
                    swap(array, jj, jj - 1);
                }
                else {
                    break;
                }
            }
        }
        System.out.println("array accesses = " + arrayAccesses);
    }
    
    private void swap(Item[] array, int first, int second) {
        Item temp = array[first];
        array[first] = array[second];
        array[second] = temp;
        
    }
}
