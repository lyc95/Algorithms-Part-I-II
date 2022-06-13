import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node
    {
        Item val;
        Node next;
        Node prev;
    }

    private Node head;
    private Node tail;
    private int size;
    // construct an empty deque
    public Deque()
    {
        this.size = 0;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }
    // is the deque empty?
    public boolean isEmpty()
    {
        return size == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return size;
    }


    // add the item to the front
    public void addFirst(Item item)
    {
        if(item != null)
        {
            Node node = new Node();
            node.val = item;
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            size++;
        }
        else
        {
            throw new IllegalArgumentException("Illegal Input");
        }

    }

    // add the item to the back
    public void addLast(Item item)
    {
        if(item != null)
        {
            Node node = new Node();
            node.val = item;
            node.prev = tail.prev;
            node.next = tail;
            tail.prev.next = node;
            tail.prev = node;
            size++;
        }
        else
        {
            throw new IllegalArgumentException("Illegal Input");
        }

    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("The Deque is Empty!");
        }
        else
        {
            Node node = head.next;
            head.next = node.next;
            node.next.prev = head;
            size--;
            return node.val;
        }
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("The Deque is Empty!");
        }
        else
        {
            Node node = tail.prev;
            tail.prev = node.prev;
            node.prev.next = tail;
            size--;
            return node.val;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node curr = head.next;
        private Node end = tail;
        public boolean hasNext()
        {
            return curr != end;
        }
        public Item next()
        {
            if (hasNext()){
                Item item = curr.val;
                curr = curr.next;
                return item;
            }
            else {
                throw new NoSuchElementException("No element");
            }

        }

        public void remove() {
            throw new UnsupportedOperationException("The remove method can not be called");
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<Integer> dq = new Deque<>();
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addLast(5);
        dq.addLast(10);
        StdOut.println("All of the elements are as shown below");
        for(Integer x : dq){
            StdOut.println(x);
        }


        StdOut.println("The Size of Deque is " + dq.size());
        StdOut.println("Now remove First element " + dq.removeFirst());
        StdOut.println("Now remove Last element " + dq.removeLast());
        StdOut.println("All of the elements are as shown below");
        for(Integer x : dq){
            StdOut.println(x);
        }
        StdOut.println("The Size of Deque is " + dq.size());

        StdOut.println("Now remove First element " + dq.removeFirst());
        StdOut.println("Now remove First element " + dq.removeFirst());

        StdOut.println(dq.isEmpty());
        StdOut.println("The Size of Deque is " + dq.size());
    }

}
