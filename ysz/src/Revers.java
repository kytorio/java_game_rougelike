import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Revers {

    public static void main(String[] args)
    {
        Stack<Integer> stack = new Stack<>();
        int times = 10;
        while(times --> 0)
        {
            stack.push(StdIn.readInt());
        }
        for(int i: stack)
        {
            StdOut.println(i);
        }
    }
}
