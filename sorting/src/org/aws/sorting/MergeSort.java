package org.aws.sorting;

import java.lang.reflect.Array;

public class MergeSort<Item extends Comparable<Item>> {
    public void sort(Item[] array, Class<Item> itemType) {
//        int arrayAccesses = 0;
        sortRec(array, 0, array.length - 1, itemType);
//        System.out.println("array accesses = " + arrayAccesses);
    }
    
    public void sortRec(Item[] array, int start, int end, Class<Item> itemType) {
        if (start == end) {
            return;
        }
        int mid = start + ((end - start) / 2);
        sortRec(array, start, mid, itemType);
        sortRec(array, mid + 1, end, itemType);
        merge(array, start, mid, end, itemType);
    }
    
    /**
     * Merge a left and right side of an array
     * @param array the array
     * @param start the starting position of the "left" side of the array
     * @param mid the ending position of the "left" side of the array, mid+1 is the "right" side of the array
     * @param end the end of the "right" side of the array
     */
    public void merge(Item[] array, int start, int mid, int end, Class<Item> itemType) {
        int k = start;
        
        Item[] tempArray = (Item [])Array.newInstance(itemType, end-start+1);
        System.arraycopy(array, start, tempArray, 0, end-start+1);
        int i = 0;
        mid = (tempArray.length) / 2 - 1;
        int j = mid + 1;
        end = tempArray.length - 1;
        
        while (i <= mid || j <= end) {
            if (j > end) { // right exhausted
                array[k] = tempArray[i++];
            }
            else if (i > mid) { // left exhausted
                array[k] = tempArray[j++];
            }
            else {
                if (tempArray[i].compareTo(tempArray[j]) <= 0) {
                    array[k] = tempArray[i++]; // left is less than or equal to right
                }
                else { // right is smaller than left
                    array[k] = tempArray[j++];
                }
            }
            k++;
        }
    }
}
