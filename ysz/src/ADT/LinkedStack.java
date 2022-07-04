package ADT;

import java.util.NoSuchElementException;

public class LinkedStack<Item> {

    private int n = 0;
    private Node first = null;

    class Node<Item>
    {
        Item item;
        Node next;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public void push(Item item)
    {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public Item pop()
    {
        if(isEmpty()) throw new NoSuchElementException("Stack is empty.");
        Item item = (Item) first.item;
        first = first.next;
        n--;
        return item;
    }

    public int size()
    {
        return n;
    }

    public Item top()
    {
        return (Item) first.item;
    }

}
