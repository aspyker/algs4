package org.spyker.netflixtest;

import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {
    long counter;
    boolean finished;
    ReentrantReadWriteLock rwLock;
    static int READTHREADS = 10;

    public static void main(String[] args) throws Exception {
        (new TestReadWriteLock()).doit();
    }

    public TestReadWriteLock() {
        counter = 0;
        finished = false;
        rwLock = new ReentrantReadWriteLock();
    }
    
    public void doit() throws Exception {
        Thread writer = new Thread(new WriteThread());
        Thread reader = new Thread(new ReadThread());
        reader.setDaemon(true);

        reader.start();
        writer.start();
        
        writer.join();
        //reader.join();
    }
    
    class ReadThread implements Runnable {
        @Override
        public void run() {
            System.out.println(new Date() + " reader about to sleep for 2 seconds");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.out.println(new Date() + " reader woke up after 2 seconds");
            
            while (true) {
                try {
                    rwLock.readLock().lock();
                    System.out.println(new Date() + " reader grabbed read lock, about to sleep for 1 second");
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    System.out.println(new Date() + " reader woke up after 1 seconds");
                } finally {
                    rwLock.readLock().unlock();
                    System.out.println(new Date() + " reader unlocked read lock");
                }
            }
        }
    }
    
    class WriteThread implements Runnable {
        @Override
        public void run() {
            System.out.println(new Date() + " writer about to sleep for 10 seconds");
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.out.println(new Date() + " writer woke up after 10 seconds");
            
            try {
                rwLock.writeLock().lock();
                System.out.println(new Date() + " writer grabbed write lock, about to sleep for 10 seconds");
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                    
            } finally {
                rwLock.writeLock().unlock();
                System.out.println(new Date() + " writer unlocked write lock");
            }
        }
    }
}
