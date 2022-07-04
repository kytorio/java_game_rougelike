import ADT.QuickFind;
import ADT.QuickUnion;
import ADT.WeightQuickUnion;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UnionTest {

    public static void QuicKFindTest()
    {
        int N;
        N = StdIn.readInt();
        QuickFind U = new QuickFind(N);
        int times = 5;
        while(times --> 0)
        {
            int numOne = StdIn.readInt();
            int numTwo = StdIn.readInt();
            U.union(numOne, numTwo);
        }
        int numOne = StdIn.readInt();
        int numTwo = StdIn.readInt();
        StdOut.print(U.connected(numOne, numTwo));
    }

    public static void QuickUnionTest()
    {
        int N;
        N = StdIn.readInt();
        QuickUnion U = new QuickUnion(N);
        int times = 5;
        while(times --> 0)
        {
            int numOne = StdIn.readInt();
            int numTwo = StdIn.readInt();
            U.union(numOne, numTwo);
        }
        int numOne = StdIn.readInt();
        int numTwo = StdIn.readInt();
        StdOut.print(U.connected(numOne, numTwo));
    }

    public static void WeightQuickUnionTest()
    {
        int N;
        N = StdIn.readInt();
        WeightQuickUnion U = new WeightQuickUnion(N);
        int times = 5;
        while(times --> 0)
        {
            int numOne = StdIn.readInt();
            int numTwo = StdIn.readInt();
            U.union(numOne, numTwo);
        }
        int numOne = StdIn.readInt();
        int numTwo = StdIn.readInt();
        StdOut.print(U.connected(numOne, numTwo));
    }

    public static void main(String[] args)
    {
        QuicKFindTest();
        QuickUnionTest();
        WeightQuickUnionTest();
    }

}
