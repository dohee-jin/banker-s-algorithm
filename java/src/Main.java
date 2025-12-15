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

        int[][] need = new int[max.length][max[0].length];

        /*
        need = max - allocation
         */
        for(int i = 0; i < max.length; i++) {
            for(int j = 0; j < max[i].length; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        int[] request = {1, 0, 2};
        System.out.println("need = " + Arrays.deepToString(need));

        BankersAlgorithm bankers = new BankersAlgorithm(allocation, max, available);

        SafetyResult result1 = bankers.safety_check_bankers(allocation, available, need);
        System.out.println("safe state: "+ result1 + Arrays.toString(result1.safeSequence()));

        RequestResult result2 = bankers.resource_request_bankers(1, request, need);
        System.out.println("result = " + result2.state() + Arrays.toString(result2.safeSequence()));

    }

}