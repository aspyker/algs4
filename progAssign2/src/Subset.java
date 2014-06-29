import java.util.Iterator;

public class Subset {
    public static void main(String[] args) {
        int numDequeues = Integer.valueOf(args[0]);
        
        String inputs[] = StdIn.readAllStrings();
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        for (int ii = 0; ii < inputs.length; ii ++) {
            rq.enqueue(inputs[ii]);
        }
        Iterator<String> it = rq.iterator();
        for (int ii = 0; ii < numDequeues; ii++) {
            System.out.println(it.next());
        }
    }
}
