import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Matrix
{
    public static void main(String[] args) throws IOException {
        int[][] A = {{1,2,3}, {1,2,3}, {1,2,3}};
        int[][] B = {{1,2,3}, {1,2,3}, {1,2,3}};
        int[][] C = new int[A.length][B[0].length];

        for (int i = 0; i < B[0].length; i++) {
            for (int j = 0; j < B.length; j++) {
                for (int k = 0; k < A.length; k++) {
                    C[k][j] += A[k][j] * B[j][i];
                }
            }
        }

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                System.out.printf("%d ", C[i][j]);
            }
            System.out.println();
        }

    }
}