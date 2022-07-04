import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {

    public static int rank(int key, int[] array)
    {
        int low = 0;
        int high = array.length - 1;
//        Counter count = new Counter("BinarySearch");

        while(low <= high)
        {
//            count.increment();
            int mid = low + (high - low) / 2;
            if(key > array[mid]) low = mid + 1;
            else if(key < array[mid]) high = mid - 1;
            else return mid;
        }
//        System.out.print(count.toString());
        return -1;
    }

    public static void main(String[] args) {
        int N;
        Scanner input = new Scanner(System.in);
        N = input.nextInt();

        int[] array = new int[N];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = input.nextInt();
        }

        Arrays.sort(array);

        while(input.hasNext())
        {
            int key = input.nextInt();
            if(rank(key, array) < 0)
            {
                System.out.println(key);
            }
        }
        input.close();
    }
}
