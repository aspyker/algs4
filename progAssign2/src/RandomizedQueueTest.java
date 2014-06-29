

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class RandomizedQueueTest {
    @Test
    public void testToZeroSize() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        assert rq.dequeue() == 1;
        rq.enqueue(2);
        assert rq.dequeue() == 2;
    }

    @Test
    public void testIterator() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        assert rq.size() == 6;
        rq.dequeue(); // 1
        rq.dequeue(); // 2
        rq.dequeue(); // 3
        rq.dequeue(); // 4
        rq.dequeue(); // 5
        rq.dequeue(); // 6
        rq.enqueue(1);
        rq.enqueue(2);
        
        Iterator<Integer> it = rq.iterator();
        for (int ii = 0; ii < 2; ii++) {
            int val = it.next();
        }
        assert(it.hasNext() == false);
        
        assert rq.size() == 2;
    }

    @Test
    public void testTiming() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        int n = 1024*1024*40;
        
        for (int ii = 0; ii < n ; ii++) {
            int op = StdRandom.uniform(5);
            
            if (op == 0) { // enqueue
                rq.enqueue(0);
            }
            else if (op == 1) { // sample
                try {
                    assert rq.sample() == 0;
                }
                catch (NoSuchElementException nsee) {
                    // this is ok
                }
            }
            else if (op == 2) { // dequeue
                try {
                    assert rq.dequeue() == 0;
                }
                catch (NoSuchElementException nsee) {
                    // this is ok
                }
            }
            else if (op == 3) { // isEmpty
                boolean empty = rq.isEmpty();
            }
            else if (op == 4) { // size
                int size = rq.size();
            }
        }
    }
}
