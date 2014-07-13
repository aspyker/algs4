package org.aws.sorting;

public class Main {
    private static int size = 200000;
    //private static int MAX_RANDOM = Integer.MAX_VALUE;
    private static int MAX_RANDOM = 1000;
    
    public static void main(String args[]) {
        //Integer[] a = randomArray(size);
        //Integer[] a = inOrderArray(size);
        
        System.out.println("======================== selection");
        Integer[] a = randomArray(size);
        printArray(a);
        long startTime = System.currentTimeMillis();
        SelectionSort<Integer> sorter1 = new SelectionSort<Integer>();
        sorter1.sort(a);
        long endTime = System.currentTimeMillis();
        printArray(a);
        System.out.println("time = " + (endTime - startTime));
        
        System.out.println("======================== insertion");
        Integer[] b = randomArray(size);
        printArray(b);
        startTime = System.currentTimeMillis();
        InsertionSort<Integer> sorter2 = new InsertionSort<Integer>();
        sorter2.sort(b);
        endTime = System.currentTimeMillis();
        printArray(b);
        System.out.println("time = " + (endTime - startTime));
        
        System.out.println("======================== merge");
        Integer[] c = randomArray(size);
        printArray(c);
        startTime = System.currentTimeMillis();
        MergeSort<Integer> sorter3 = new MergeSort<Integer>();
        sorter3.sort(c, Integer.class);
        endTime = System.currentTimeMillis();
        printArray(c);
        System.out.println("time = " + (endTime - startTime));
        
        System.out.println("======================== heap");
        Integer[] d = randomArray(size);
        printArray(d);
        startTime = System.currentTimeMillis();
        HeapSort<Integer> sorter4 = new HeapSort<Integer>();
        sorter4.sort(d, Integer.class);
        endTime = System.currentTimeMillis();
        printArray(d);
        System.out.println("time = " + (endTime - startTime));
    }
    
    public static Integer[] randomArray(int size) {
        Integer[] a = new Integer[size];
        for (int ii = 0; ii < size; ii++) {
            a[ii] = (int)(Math.random() * MAX_RANDOM);
            //System.out.print(a[ii] + " ");
        }
        return a;
    }
    
    public static Integer[] inOrderArray(int size) {
        Integer[] a = new Integer[size];
        for (int ii = 0; ii < size; ii++) {
            a[ii] = ii;
            //System.out.print(a[ii] + " ");
        }
        return a;
    }
    
    public static void printArray(Integer[] a) {
      for (int ii = 0; ii < size; ii++) {
          //System.out.print(a[ii] + " ");
      }
      System.out.println();
    }
}
