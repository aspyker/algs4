package org.spyker.netflixtest;

public class Chapter9Problem3 {

    public static void main(String[] args) {
        int array[] = { 0, 1, 2, 3, 4, 5};
        findMagicNumber(array, 0, array.length - 1);
    }
    
    private static void findMagicNumber(int a[], int start, int end) {
        int mid = end - start / 2;
        
        if (a[mid] == mid) {
            System.out.println("found magic number, index/val = " + mid);
        }
        
        if (a[mid] >= mid && mid - 1 >= start) {
            findMagicNumber(a, start, mid - 1);
        }
        
        if (a[mid] <= mid && mid + 1 <= end) {
            findMagicNumber(a, mid+1, end);
        }
    }

}
