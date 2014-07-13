package org.aws.sorting;

public class HeapSort<Item extends Comparable<Item>> {
    public void sort(Item[] array, Class<Item> itemType) {
        BinaryHeap bh = new BinaryHeap(array, itemType);
        Item[] arraySorted = (Item[])bh.sortAndDestroyHeap();
        for (int ii = 1; ii < arraySorted.length; ii++) {
            array[ii-1] = arraySorted[ii];
        }
    }    
}
