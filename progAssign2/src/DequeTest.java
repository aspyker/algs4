

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class DequeTest {

    @Test
    public void testIterator() {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(3);
        d.addLast(4);
        assert d.size() == 4;
        assert d.removeFirst() == 1;
        assert d.removeFirst() == 2;
        assert d.removeLast() == 4;
        assert d.removeLast() == 3;
        assert d.size() == 0;

        d.addLast(3);
        d.addFirst(2);
        d.addFirst(1);
        //d.removeLast();

        Iterator<Integer> it = d.iterator();
        for (int ii = 1; ii < 4; ii++) {
            assert it.next() == ii;
        }
    }

}
