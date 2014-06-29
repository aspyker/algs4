package org.aws.sorting;

public class Main {
    private static int size = 4;
    public static void main(String args[]) {
        //Integer[] a = randomArray(size);
        Integer[] a = inOrderArray(size);
        
//        System.out.println();
        
        //SelectionSort<Integer> sorter = new SelectionSort<>();
        InsertionSort<Integer> sorter = new InsertionSort<>();
        
        sorter.sort(a);
        
//        for (int ii = 0; ii < size; ii++) {
//            System.out.print(a[ii] + " ");
//        }
        
        System.out.println();
    }
    
    public static Integer[] randomArray(int size) {
        Integer[] a = new Integer[size];
        for (int ii = 0; ii < size; ii++) {
            a[ii] = (int)(Math.random() * 10);
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
}
