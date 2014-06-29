import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_ARRAY_SIZE = 2;
    private Item items[];
    private int indexForInserts;
    private int size;
    
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[])new Object[INITIAL_ARRAY_SIZE];
        indexForInserts = 0;
        size = 0;
    }
    
    /**
     *
     * @return is the queue empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 
     * @return the number of items on the queue
     */
    public int size() {
        return size;
    }
    
    
    private boolean isFull() {
        return indexForInserts == items.length;
    }
    
    private boolean isSparse() {
        int quarterSize = items.length / 4;
        return indexForInserts <= quarterSize;
    }

    private void resizeArray(int newSize) {
        Item newItems[] = (Item[])new Object[newSize];
        for (int ii = 0; ii < indexForInserts; ii++) {
            newItems[ii] = items[ii];
        }
        items = newItems;
    }
    
    private void doubleArray() {
        resizeArray(items.length*2);
    }
    
    private void halfArray() {
        if (items.length > INITIAL_ARRAY_SIZE) {
            resizeArray(items.length/2);
        }
    }
    
    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (isFull()) {
            doubleArray();
        }
        items[indexForInserts] = item;
        indexForInserts++;
        size++;
    }
    
    /**
     * delete and return a random item
     * @return
     */
    public Item dequeue() {        
        if (size == 0) {
            throw new NoSuchElementException();
        }
        
        int itemNum = StdRandom.uniform(size);
        Item i = items[itemNum];
        
        items[itemNum] = items[indexForInserts - 1];
        items[indexForInserts - 1] = null;
        indexForInserts--;
        size--;
        
        if (isSparse()) {
            halfArray();
        }
        return i;
    }
    
    /**
     * 
     * @return (but do not delete) a random item
     */
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        
        int itemNum = StdRandom.uniform(size);
        Item i = items[itemNum];
        return i;
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
        
    private class RandomizedQueueIterator implements Iterator<Item> {
        int index = 0;
        Object shuffled[] = new Object[size];
        
        public RandomizedQueueIterator() {
            for (int ii = 0 ; ii < size; ii++) {
                shuffled[ii] = items[ii];
            }
            StdRandom.shuffle(shuffled);
        }
        
        @Override
        public boolean hasNext() {
            return index <= shuffled.length - 1;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            Item i = (Item)shuffled[index];
            index++;
            return i;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args) {
        int numEntries = 3;
        
        RandomizedQueue<Integer> d = new RandomizedQueue<>();
        for (int ii = 0; ii < numEntries; ii++) {
            d.enqueue(ii);
        }
        for (Integer i : d) {
            System.out.print(i  + " ");
        }
        System.out.println();
        for (int ii = 0; ii < numEntries; ii++) {
            System.out.print(ii + ":" + d.dequeue() + "(" + d.size() + ")" + " ");
        }
    }
}
