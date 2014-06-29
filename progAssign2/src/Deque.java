import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private DDListNode<Item> first;
    private DDListNode<Item> last;
    private int size;
    
    /**
     * construct an empty deque
     */
    public Deque() {
        first = last = null;
        size = 0;
    }

    /**
     * 
     * @return is the deque empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 
     * @return the number of items on the deque
     */
    public int size() {
        return size;
    }

    /**
     * insert the item at the front
     * @param item
     */
    public void addFirst(Item item) {
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
     * insert the item at the end
     * @param item
     */
    public void addLast(Item item) {
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
        DDListNode<Item> curLast = last;
        last = newNode;
        newNode.prevNode = curLast;
        curLast.nextNode = newNode;
        size++;
    }
    
    /**
     * delete and return the item at the front
     * @return
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        DDListNode<Item> curFirst = first;
        if (size == 1) {
            size = 0;
            first = last = null;
            return curFirst.item;
        }
        first = curFirst.nextNode;
        first.prevNode = null;
        curFirst.nextNode = null;
        size--;
        
        return curFirst.item;
    }
    
    /**
     * delete and return the item at the end
     * @return
     */
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }        
        DDListNode<Item> curLast = last;
        if (size == 1) {
            size = 0;
            first = last = null;
            return curLast.item;
        }
        last = curLast.prevNode;
        last.nextNode = null;
        curLast.prevNode = null;
        size--;
        
        return curLast.item;
    }
    
    /**
     * return an iterator over items in order from front to end
     * @return
     */
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {
            DDListNode<Item> cur = first;
            
            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                
                Item i = cur.item;
                cur = cur.nextNode;
                return i;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
    
    private class DDListNode<NodeItem> {
        public DDListNode(NodeItem item) {
            this.item = item;
        }
        
        public DDListNode<NodeItem> prevNode;
        public DDListNode<NodeItem> nextNode;
        public NodeItem item;
    }
    
    public static void main(String[] args) {
        for (int jj = 0 ; jj < 1000000000; jj++) {
        Deque<Integer> d = new Deque<>();
        for (int ii = 0; ii < 100; ii++) {
            d.addFirst(ii);
        }
        for (Integer i : d) {
            //System.out.print(i  + " ");
        }
        //System.out.println();
        for (int ii = 0; ii < 100; ii++) {
            /*System.out.print(*/d.removeLast()/* + " ")*/;
        }
        }
    }
}