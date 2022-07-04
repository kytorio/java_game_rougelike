import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;

public class BagTest {

    public static void main(String[] args)
    {
        Bag<Double> B = new Bag<>();
        while(!StdIn.isEmpty())
        {
            B.add(StdIn.readDouble());
        }
        int N = B.size();
        double sum = 0.0;
        for(double i: B)
        {
            sum += i;
        }
        double mean = sum / N;
        for(double i: B)
        {
            sum += (i - mean) * (i - mean);
        }
        double std = Math.sqrt(sum / N - 1);
        StdOut.printf("mean = %.2f.", mean);
        StdOut.printf("std = %.2f.", std);
    }
}
