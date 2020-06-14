import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node<Item>{
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    private class DequeIterator<Item> implements Iterator<Item>{
        private Node<Item> current;

        private DequeIterator(Node<Item> item)
        {
            current = item;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()){
                throw new NoSuchElementException("No more elements");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private Node<Item> front;
    private Node<Item> back;
    // Deque size
    private int size;


    // construct an empty deque
    public Deque(){
        size = 0;
        front = back = null;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null){
            throw new NullPointerException("Invalid input");
        }
        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = front;
        newNode.prev = null;
        if(isEmpty()){
            back = newNode;
        }else{
            front.prev = newNode;
        }
        front = newNode;
        size++;
    }
    // add the item to the back
    public void addLast(Item item){
        if(item == null){
            throw new NullPointerException("Invalid input");
        }
        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = null;
        newNode.prev = back;

        if(isEmpty()){
            front = newNode;
        }else{
            back.next = newNode;
        }
        back = newNode;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(isEmpty()){
            throw new NoSuchElementException("Deque is empty");
        }
        size--;
        Item item = front.item;
        front = front.next;
        if(isEmpty()){
            back = null;
        }else{
            front.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(isEmpty()){
            throw new NoSuchElementException("Deque is empty");
        }
        size--;
        Item item = back.item;
        back = back.prev;
        if(isEmpty()){
            back = null;
        }else{
            back.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator<>(front);
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<String> deque = new Deque<String>();

        String text = "World";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = ", ";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = "Hello";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        StdOut.println("Iterating deque...");
        for (String item: deque) {
            StdOut.println("Iterate element: " + item);
        }

    }

}
