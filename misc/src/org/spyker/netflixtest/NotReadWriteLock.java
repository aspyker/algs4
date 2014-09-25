package org.spyker.netflixtest;

import java.text.NumberFormat;
import java.util.Locale;

public class NotReadWriteLock {
    long counter;
    boolean finished;
    Object NOTrwLock;
    static int READTHREADS = 10;

    public static void main(String[] args) throws Exception {
        (new NotReadWriteLock()).doit();
    }

    public NotReadWriteLock() {
        counter = 0;
        finished = false;
        NOTrwLock = new Object();
    }
    
    public void doit() throws Exception {
        Thread writer = new Thread(new WriteThread());
        
        Thread[] readerThreads = new Thread[READTHREADS];
        ReadThread[] readers = new ReadThread[READTHREADS];
        for (int ii = 0; ii < READTHREADS; ii++) {
            ReadThread rt = new ReadThread();
            Thread reader = new Thread(rt);
            readers[ii] = rt;
            readerThreads[ii] = reader;
            reader.start();
        }
        writer.start();
        
        writer.join();
        for (int ii = 0; ii < READTHREADS; ii++) {
            readerThreads[ii].join();
        }
        
        long totalReads = 0;
        for (int ii = 0; ii < READTHREADS; ii++) {
            totalReads += readers[ii].numReads;
        }
        System.out.println("done with numReads = " + NumberFormat.getNumberInstance(Locale.US).format(totalReads));
        System.out.println("counter = " + counter);
    }
    
    class ReadThread implements Runnable {
        public long numReads = 0;
        @Override
        public void run() {
            while(!finished) {
                synchronized(NOTrwLock) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    //rwLock.readLock().lock();
                    long copy = counter;
                    doSomethingWithCopy(copy);
                    //System.out.println("counter value is = " + copy);
                    numReads++;
                    //rwLock.readLock().unlock();
                }
                Thread.yield();
            }
        }
        
        private void doSomethingWithCopy(long copy) {
        }
    }
    
    class WriteThread implements Runnable {
        @Override
        public void run() {
            while (counter < 100) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                synchronized(NOTrwLock) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
//                    rwLock.writeLock().lock();
                    counter++;
                    //System.out.println("updated counter to = " + counter);
                    //rwLock.writeLock().unlock();
                }
                Thread.yield();
            }
            finished = true;
        }
    }
}
