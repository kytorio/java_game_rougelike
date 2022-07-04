package ADT;

import java.util.Iterator;

public class ArrayBaseStack<Item> implements Iterable<Item>
{

    private static final int INIT_CAPACITY = 8;

    private Item[] s;
    private int N;

    public ArrayBaseStack()
    {
        N = 0;
        s = (Item[]) new Object[INIT_CAPACITY];
    }

    public boolean isEmpty()
    {
        return N == 0;
    }

    public int size()
    {
        return N;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[INIT_CAPACITY];
        for(int i = 0; i < N; i++)
        {
            copy[i] = s[i];
        }
        s = copy;
    }

    public void push(Item item)
    {
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    public Item pop()
    {
        // 第N个的索引是 N-1, 所以是 --N .
        Item item =  s[--N];
        // 注意这里 N 已经减一.
        // 清空对已出栈的对象的引用
        // 让java对其进行垃圾回收, 提高内存利用率
        s[N] = null;
        if(N < s.length / 4) resize(s.length / 2);
        return item;
    }

    public Iterator<Item> iterator()
    {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item>
    {
        private int i = N;

        public boolean hasNext()
        {
            return i > 0;
        }

        public  Item next()
        {
            return s[--i];
        }

        public void remove() { }
    }

}
