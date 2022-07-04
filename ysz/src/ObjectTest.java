public class ObjectTest<Item> {

    private Item[] items;

    public ObjectTest()
    {
        items = (Item[]) new Object[1];
    }

    public void push(Item item)
    {
        items[0] = item;
    }

    public Item pop()
    {
        return items[0];
    }

    public static void main(String[] args) {

        // 十分疑惑
        // String[] s = (String[]) new Object[5];
        ObjectTest<String> test = new ObjectTest<String>();
        test.push("1");
        System.out.println(test.pop());
    }

}
