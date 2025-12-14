import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /*
           available, allocation, max 초기값
         */
        int[] available = {3, 3, 2};
        int[][] allocation = {
                {0, 1, 0},
                {2, 0, 0},
                {3, 0, 2},
                {2, 1, 1},
                {0, 0, 2}
        };
        int[][] max = {
                {7, 5, 3},
                {3, 2, 2},
                {9, 0, 2},
                {2, 2, 2},
                {4, 3, 3}
        };

        BankersAlgorithm bankers = new BankersAlgorithm(allocation, max, available);
        SafetyResult result = bankers.safety_check_bankers();

        System.out.println(result + Arrays.toString(result.safeSequence()));
    }

}