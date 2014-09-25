package org.aws.sorting;

public class QuickSort<Item extends Comparable<Item>> {
    private boolean useInsertionForSmallerArrays;
    private static int CUTOFF_FOR_INSERTION = 10;
    private InsertionSort<Item> iSorter; 
    
    public QuickSort(boolean useInsertionForSmallerArrays) {
        this.useInsertionForSmallerArrays = useInsertionForSmallerArrays;
        if (useInsertionForSmallerArrays) {
            iSorter = new InsertionSort<>();
        }
    }
    
    public void sort(Item[] array) {
        quicksort(array, 0, array.length-1);
    }
    
    private void quicksort(Item[] array, int begin, int end) {
        // TODO:  This isn't speeding things up, why?
        if (useInsertionForSmallerArrays && (end - begin) <= CUTOFF_FOR_INSERTION) {
            iSorter.sort(array, begin, end);
            return;
        }
        
        if (begin < end) {
            int p = partition(array, begin, end);
            // TODO:  optimize this by going into the smaller side first
            // TODO:  could optimize for repeated values (would require returning start of equal to and end of equal to p)
            quicksort(array, begin, p - 1);
            quicksort(array, p + 1, end);
        }
    }
    
    private int partition(Item[] array, int left, int right) {
        int pivotIndex = left + (right - left) / 2;
        Item pivotVal = array[pivotIndex];
        swap(array, pivotIndex, right);
        int storeIndex = left;
        for (int compareIndex = left; compareIndex <= right - 1; compareIndex++) {
            if (array[compareIndex].compareTo(pivotVal) < 0) {
                swap(array, compareIndex, storeIndex);
                storeIndex++;
            }
        }
        swap(array, storeIndex, right);
        return storeIndex;
    }
    
    private void swap(Item[] array, int a, int b) {
        Item temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }    
}
