package org.spyker.netflixtest;

public class Chapter2Problem5 {
    class Node {
        int data;
        Node next;
        
        public Node(int data) {
            this.data = data;
        }
    }
    
    class LLints {
        Node head;
        
        public LLints() {
            head = null;
        }
        
        public LLints(int[] ints) {
            Node last = new Node(ints[0]);
            head = last;
            for (int ii = 1; ii < ints.length; ii++) {
                Node n = new Node(ints[ii]);
                last.next = n;
                last = n;
            }
        }
        
        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            Node cur = head;
            while (cur != null) {
                sb.append(cur.data + " -> ");
                cur = cur.next;
            }
            sb.append("NULL");
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
        new Chapter2Problem5().doIt();
    }
    
    public void doIt() {
        int[] first = { 7, 1, 6};
        int[] second = { 5, 9, 2};
        LLints firstLL = new LLints(first);
        LLints secondLL = new LLints(second);
        System.out.println(firstLL);
        System.out.println(secondLL);
        LLints added = addLinkedLists(firstLL, secondLL);
        System.out.println(added);
    }
    
    public LLints addLinkedLists(LLints one, LLints two) {
        Node oneCur = one.head;
        Node twoCur = two.head;
        LLints added = new LLints();
        Node addedCur = added.head;
        
        int lastRemainder = 0;
        
        while (oneCur != null && twoCur != null) {
            int oneVal = (oneCur == null) ? 0 : oneCur.data;
            int twoVal = (twoCur == null) ? 0 : twoCur.data;
            
            int add = oneVal + twoVal + lastRemainder;
            Node n = new Node(add % 10);
            if (addedCur == null) {
                added.head = n;
                addedCur = added.head;
            }
            else {
                addedCur.next = n;
                addedCur = n;
            }
            lastRemainder = add / 10;
            
            oneCur = oneCur.next;
            twoCur = twoCur.next;
        }
        return added;
        
    }

}
