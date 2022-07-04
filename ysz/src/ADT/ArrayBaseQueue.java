package ADT;

import java.util.NoSuchElementException;

public class ArrayBaseQueue<Item> {

    private static final int INIT_CAPACITY = 8;

    private Item[] q;
    private int first, last;
    private int n; // 记录队列中元素的个数

    public ArrayBaseQueue()
    {
        q = (Item[]) new Object[INIT_CAPACITY];
        first = 0;
        last = 0;
        n = 0;
    }

    public boolean isEmpty()
    {
        return n == 0;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
        {
            // 通过取余, 将队列映射到数组的最开头
            // 实现内存最大化利用, 减少resize的次数
            copy[i] = q[(first + i) % q.length];
        }
        q = copy;
        // 将两个指针指向映射后的位置
        first = 0;
        last  = n;
    }

    public void enqueue(Item item)
    {
        if(n == q.length) resize(2 * q.length);
        q[last++] = item;
        if(last == q.length) last = 0;
        n++;
    }

    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = q[first];
        q[first] = null;
        n--;
        first++;
        if(first == q.length) first = 0;
        if(n > 0 && n < q.length / 4) resize(q.length / 2);
        return item;
    }

    public int size()
    {
        return n;
    }

    public Item peek()
    {
        return q[first];
    }

    public static void main(String[] args)
    {
        ArrayBaseQueue<String> a = new ArrayBaseQueue<>();
        a.enqueue("1");

    }
}
