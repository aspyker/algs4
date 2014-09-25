package org.spyker.netflixtest.scratch;

public class ArrayStoreExceptionExample {
    public static void main(String args[]) {
        String[] strings = new String[10];
        strings[0] = "hello";
        Object[] objects = strings;
        objects[1] = "world";
        objects[2] = new Object();
    }
}
