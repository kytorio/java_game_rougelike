package ADT;

public class QuickUnion {

    public int[] tree;

    public QuickUnion(int N)
    {
        tree = new int[N];
        for(int i = 0; i < tree.length; i++) tree[i] = i;
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
        tree[pRoot] = qRoot;
    }

    public boolean connected(int p, int q)
    {
        return root(p)== root(q);
    }
}
