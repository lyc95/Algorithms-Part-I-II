import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int capacity;
    private int currSize;
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        capacity = 1;
        currSize = 0;
    }

    private void resize(int newSize)
    {
        Item[] newArr = (Item[]) new Object[newSize];
        for (int i = 0; i < currSize; i++)
        {
            newArr[i] = arr[i];
        }
        this.arr = newArr;
        this.capacity = newSize;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return currSize == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return currSize;
    }

    // add the item
    public void enqueue(Item item){
        if (item != null){
            if(currSize >= capacity)
            {
                resize(capacity * 2);
            }
            arr[currSize++] = item;
        }
        else{
            throw new IllegalArgumentException("Illegal Input");
        }

    }

    // remove and return a random item
    public Item dequeue(){
        if (!isEmpty()){
            int random = getRandom();
            Item tmp = arr[random];
            arr[random] = arr[--currSize];
            arr[currSize] = null;
            if (currSize <= capacity/4 && capacity/2 > 0){
                resize(capacity/2);
            }
            return tmp;
        }
        else{
            throw new NoSuchElementException("No element in the queue");
        }

    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (!isEmpty())
        {
            return arr[getRandom()];
        }
        else{
            throw new IllegalArgumentException("Illegal Input");
        }

    }

    private int getRandom(){
        return StdRandom.uniform(0, currSize);
    }

    // return an independent iterator over items in random order
    // how to create a random iterator??
    public Iterator<Item> iterator() {
        return new dequeArrayIterator();
    }

    private class dequeArrayIterator implements Iterator<Item>{

        private int currIndex;
        private int end;
        private Item[] container;

        dequeArrayIterator(){
            currIndex = 0;
            end = currSize;
            container = (Item[]) new Object[end];
            if(end != 0){
                container[0] = arr[0];
                for (int i = 0; i < end; i++)
                {
                    container[i] = arr[i];
                    int num = StdRandom.uniform(0, i) + 1;
                    swap(container, num, i);
                }
            }
        }
        public boolean hasNext(){
            return currIndex < end;
        }

        public Item next() {
            if(hasNext()){
                return container[currIndex++];
            }
            else{
                throw new NoSuchElementException("No Element Existed");
            }

        }

        public void remove() {
            throw new UnsupportedOperationException("The remove method can not be called");
        }

        private void swap(Item[] arr, int i, int j){
            Item tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> rdq = new RandomizedQueue<>();
        for(int i = 0; i < 10; i++)
        {
            rdq.enqueue(i);
        }
        StdOut.println("The current Size is " + rdq.size());
        StdOut.println("Show random element" + rdq.sample());
        StdOut.println("Show random element" + rdq.sample());
        StdOut.println("Now remove item" + rdq.dequeue());
        StdOut.println("Now remove item" + rdq.dequeue());
        StdOut.println("The current Size is " + rdq.size());
        for (Integer x : rdq){
            StdOut.println("The current element " + x);
        }
    }

}
