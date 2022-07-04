import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class RunTime {

    public static void main(String[] args)
    {
        int a = StdIn.readInt();
        Stopwatch stopwatch = new Stopwatch();
        StdOut.println(a);
        StdOut.println(stopwatch.elapsedTime());
    }
}
