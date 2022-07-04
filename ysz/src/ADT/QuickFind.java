package ADT;

public class QuickFind {

    private int[] tree;
    public QuickFind(int N)
    {
        tree = new int[N];
        for (int i = 0; i < N; i++) tree[i] = i;
    }

    public void union(int p, int q)
    {
        int pid = tree[p];
        int qid = tree[q];
        if(pid == qid) return;
        for (int i = 0; i < tree.length; i++)
            if(tree[i] == pid)
                tree[i] = qid;
    }

    public boolean connected(int p, int q)
    {
        return tree[p] == tree[q];
    }
}
