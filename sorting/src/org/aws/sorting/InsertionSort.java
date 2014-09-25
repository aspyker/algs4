package org.aws.sorting;

public class InsertionSort<Item extends Comparable<Item>> {
    
    public void sort(Item[] array, int start, int end) {
//      int arrayAccesses = 0;
      for (int ii = start + 1 ; ii <= end; ii++) {
          for (int jj = ii; jj > 0; jj--) {
              Item jjItem = array[jj];
              Item leftOfJJItem = array[jj - 1];
//              arrayAccesses+=2;
              if (jjItem.compareTo(leftOfJJItem) < 0) {
                  swap(array, jj, jj - 1);
              }
              else {
                  break;
              }
          }
      }
//      System.out.println("array accesses = " + arrayAccesses);
    }
    
    public void sort(Item[] array) {
        sort(array, 0, array.length - 1);
    }
    
    private void swap(Item[] array, int first, int second) {
        Item temp = array[first];
        array[first] = array[second];
        array[second] = temp;
        
    }
}
