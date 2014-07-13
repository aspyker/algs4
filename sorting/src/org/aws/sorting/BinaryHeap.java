package org.aws.sorting;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class BinaryHeap<Item extends Comparable<Item>> {
    Item[] array;
    Class<Item> itemType;
    int k; // k will point the last item (starts at 1 and goes to array.length - 1)
    
    public BinaryHeap(Item[] initialArrayNotInHeapOrder, Class<Item>itemType) {
        this.itemType = itemType;
        array = (Item[])Array.newInstance(itemType, initialArrayNotInHeapOrder.length + 1);
        for (int ii = 0; ii < initialArrayNotInHeapOrder.length; ii++) {
            array[ii+1] = initialArrayNotInHeapOrder[ii];
        }
        k = initialArrayNotInHeapOrder.length;
        
        for (int j = (k / 2) + 1; j >= 1; j--) {
            sink(j);
        }
    }
    
    public Item[] sortAndDestroyHeap() {
        while (k >= 1) {
            exchange(1, k); // biggest always at top, swap to end
            k--;
            sink(1);
        }
        return array;
    }
    
    public BinaryHeap(Class<Item> itemType) {
        this.itemType = itemType;
        array = (Item[])Array.newInstance(itemType, 2);
        k = 0;
    }
    
    public void add(Item item) {
        if (k + 1 >= array.length) {
            resizeArray(array.length * 2);
        }
        k++;
        array[k] = item;
        swim(k); // move item up tree as needed
    }
    
    public Item max() {
        if (k == 0) {
            throw new NoSuchElementException();
        }
        return array[1];
    }
    
    public boolean isEmpty() {
        return k == 0;
    }
    
    public Item delMax() {
        if (k == 0) {
            throw new NoSuchElementException();
        }
        if (k == 1) {
            array[1] = null;
            k--;
        }
        Item item = array[1];
        exchange(1, k);
        array[k] = null;
        k--;
        sink(1);
        
        if (k < array.length / 4) {
            resizeArray(array.length / 2);
        }
        
        return item;
    }
    
    private void swim(int index) {
        while (index > 1 && less(index / 2, index)) {
            exchange(index / 2, index);
            index = index / 2;
        }
    }
    
    private void sink(int index) {
        while (index*2 <= k) {
            int left = index*2;
            int right = index*2+1;
            int whichChildLess = left;
            if (right <= k) {
                whichChildLess = less(left, right) ? right : left;
            }
            if (!lessThanEqual(index, whichChildLess)) {
                return;
            }
            exchange(index, whichChildLess);
            index = whichChildLess;
        }
    }
    
    private boolean less(int index1, int index2) {
        return array[index1].compareTo(array[index2]) < 0;
    }
    
    private boolean lessThanEqual(int index1, int index2) {
        return array[index1].compareTo(array[index2]) <= 0;
    }
    
    private void exchange(int index1, int index2) {
        Item temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    
    private void resizeArray(int newSize) {
        Item[] newArray = (Item[])Array.newInstance(itemType, newSize);
        for (int ii = 0; ii < array.length; ii++) {
            newArray[ii] = array[ii];
        }
        array = newArray;
    }    

    public static void main(String[] args) {
        BinaryHeap<Integer> bh = new BinaryHeap<Integer>(Integer.class);
        // NULL
        bh.add(10);
        // NULL 10(1)
        bh.add(15);
        // NULL 15(1) 10(2) - float returns immediately
        bh.add(5);
        // NULL 15(1) 10(2) 5(3) - float return immediately
        bh.add(12);
        // NULL 15(1) 10(2) 5(3) 12(4) - float against 4/2=2 (12 is bigger than 10)
        // NULL 15(1) 12(2) 5(3) 10(4)
        bh.add(13);
        // NULL 15(1) 12(2) 5(3) 10(4) 13(5) - float against 5/2=2 (13 is bigger than 12)
        // NULL 15(1) 13(2) 5(3) 10(4) 12(5)
        bh.add(20);
        // NULL 15(1) 13(2) 5(3) 10(4) 12(5) 20(6) - float against 6/3=3 (20 is bigger than 5)
        // NULL 15(1) 13(2) 20(3) 10(4) 12(5) 5(6) - float against 3/1=1 (20 is bigger than 1)
        // NULL 20(1) 13(2) 15(3) 10(4) 12(5) 5(6)
        //         20
        //    13        15
        // 10    12  6
        Integer max = bh.max();
        max = bh.delMax();
        // NULL 20(1) 13(2) 15(3) 10(4) 12(5) 5(6)
        // NULL  5(1) 13(2) 15(3) 10(4) 12(5) 20(1) // exchange 1 and k
        // NULL  5(1) 13(2) 15(3) 10(4) 12(5) // NULL out k, k--
        // NULL  15(1) 13(2) 5(3) 10(4) 12(5) // sink 5(1) (children are 13(2) and 15(3) - 15 is largest, so swap)
        // NULL  15(1) 13(2) 5(3) 10(4) 12(5) // sink 5(3) (children would be (6) and (7) that don't exist, so at bottom now)
        //          15
        //       13     5
        //    10   12
        System.out.println("done");
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[0]=NULL");
        for (int ii = 1; ii <= k; ii++) {
            sb.append("[" + ii + "]=" + array[ii]);
            if (ii < k) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
