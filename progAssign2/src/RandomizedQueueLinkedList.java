import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueueLinkedList<Item> implements Iterable<Item> {
    private DDListNode<Item> first;
    private DDListNode<Item> last;
    private int size;
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueueLinkedList() {
        first = last = null;
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
    
    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        DDListNode<Item> newNode = new DDListNode(item);
        if (size == 0) {
            first = last = newNode;
            first.nextNode = null;
            first.prevNode = null;
            size++;
            return;
        }
        DDListNode<Item> curFirst = first;
        first = newNode;
        newNode.nextNode = curFirst;
        curFirst.prevNode = newNode;
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
        
        DDListNode<Item> curFirst = first;
        if (size == 1) {
            size = 0;
            first = last = null;
            return curFirst.item;
        }
        
        int itemNum = StdRandom.uniform(size);
        DDListNode<Item> toRemovePtr = first;
        for (int ii = 0; ii < itemNum; ii++) {
            toRemovePtr = toRemovePtr.nextNode;
        }
        
        if (toRemovePtr.prevNode != null) {
            toRemovePtr.prevNode.nextNode = toRemovePtr.nextNode;
        }
        else { // randomly selected first element
            first = first.nextNode;
        }
        if (toRemovePtr.nextNode != null) {
            toRemovePtr.nextNode.prevNode = toRemovePtr.prevNode;
        }
        else { // randomly selected last element
            last = last.prevNode;
        }
        
        toRemovePtr.nextNode = null;
        toRemovePtr.prevNode = null;
        size--;
        
        return toRemovePtr.item;
    }
    
    /**
     * 
     * @return (but do not delete) a random item
     */
    public Item sample() {
        // TODO:  Should write a common utility function between dequeue and sample, instead of cut/paste
        if (size == 0) {
            throw new NoSuchElementException();
        }
        
        DDListNode<Item> curFirst = first;
        if (size == 1) {
            return curFirst.item;
        }
        
        int itemNum = StdRandom.uniform(size);
        DDListNode<Item> toRemovePtr = first;
        for (int ii = 0; ii < itemNum; ii++) {
            toRemovePtr = toRemovePtr.nextNode;
        }
        
        return toRemovePtr.item;
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
        
    private class DDListNode<NodeItem> {
        public DDListNode(NodeItem item) {
            this.item = item;
        }
        
        public DDListNode<NodeItem> prevNode;
        public DDListNode<NodeItem> nextNode;
        public NodeItem item;
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        int index = 0;
        Object shuffled[] = new Object[size];
        
        public RandomizedQueueIterator() {
            DDListNode<Item> curPtr = first;
            for (int ii = 0 ; ii < size; ii++) {
                shuffled[ii] = curPtr;
                curPtr = curPtr.nextNode;
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
            
            Item i = ((DDListNode<Item>)shuffled[index]).item;
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
        
        RandomizedQueueLinkedList<Integer> d = new RandomizedQueueLinkedList<>();
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
