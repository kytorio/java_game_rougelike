package ADT;

public class WeightQuickUnion {

    public int[] tree;
    public int[] count;

    public WeightQuickUnion(int N)
    {
        tree = new int[N];
        count = new int[N];
        for(int i = 0; i < tree.length; i++)
        {
            tree[i] = i;
            count[i] = 1;
        }
    }

    public int root(int id)
    {
        while(id != tree[id]) id = tree[tree[id]];
        return id;
    }

    public void union(int p, int q)
    {
        int pRoot = root(p);
        int qRoot = root(q);
        if(count[pRoot] < count[qRoot])
        {
            tree[qRoot] = pRoot;
            count[pRoot] += count[qRoot];
        }
        else
        {
            tree[pRoot] = qRoot;
            count[qRoot] += count[pRoot];
        }
    }

    public boolean connected(int p, int q)
    {
        return root(p)== root(q);
    }

}
