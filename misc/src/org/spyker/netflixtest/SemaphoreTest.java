package org.spyker.netflixtest;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    Semaphore first = new Semaphore(1, true);
    Semaphore second = new Semaphore(1, true);
    Semaphore third = new Semaphore(1, true);

    public static void main(String[] args) throws Exception {
        new SemaphoreTest().doit();
    }
    
    public void doit() throws Exception {
        Thread t1 = new Thread(new OneThread());
        Thread t2 = new Thread(new TwoThread());
        Thread t3 = new Thread(new ThreeThread());
        
        t3.start();
        t2.start();
        t1.start();
        
        t3.join();
        t2.join();
        t1.join();
    }

    class OneThread implements Runnable {
        public OneThread() throws Exception {
            first.acquire();
        }
        
        @Override
        public void run() {
            try {
                for (int ii = 0; ii < 10; ii++) {
                    if (ii != 0) {
                        third.acquire();
                        third.release();
                    }
                    System.out.println("thread 1 running");
                    first.release();
                    Thread.yield();
                    first.acquire();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    class TwoThread implements Runnable {
        public TwoThread() throws Exception {
            second.acquire();
        }
        
        @Override
        public void run() {
            try {
                for (int ii = 0; ii < 10; ii++) {
                    first.acquire();
                    first.release();
                    System.out.println("thread 2 running");
                    second.release();
                    Thread.yield();
                    second.acquire();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    class ThreeThread implements Runnable {
        public ThreeThread() throws Exception {
            third.acquire();
        }
        
        @Override
        public void run() {
            try {
                for (int ii = 0; ii < 10; ii++) {
                    second.acquire();
                    second.release();
                    System.out.println("thread 3 running");
                    third.release();
                    Thread.yield();
                    third.acquire();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}