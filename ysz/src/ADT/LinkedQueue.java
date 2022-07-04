package ADT;

import java.util.NoSuchElementException;

public class LinkedQueue<Item> {

    private Node first, last;
    private int n = 0;

    private class Node<Item>
    {
        Item item;
        Node next;
    }
    
    public boolean isEmpty()
    {
        return  first == null;
    }

    public void enqueue(Item item)
    {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }
    
    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
        Item item = (Item) first.item;
        first = first.next;
        if(isEmpty()) last = null;
        n--;
        return item;
    }

    public Item peek()
    {
        return (Item) first.item;
    }

    public int size()
    {
        return n;
    }
    
    public static void main(String[] args)
    {
        LinkedQueue<Integer> q = new LinkedQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        System.out.println(q.size());
        System.out.println(q.dequeue());
        System.out.println(q.peek());
        System.out.println(q.dequeue());
        System.out.println(q.size());
    }

}
