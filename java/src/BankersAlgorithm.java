public class BankersAlgorithm {

    boolean[] finish;
    int [][] allocation;
    int [][] max;
    int [][] need;
    int [] available;

    public BankersAlgorithm(int[][] allocation, int[][] max, int[] available) {
        int row = max.length;
        int column = max[0].length;

        this.max = max;
        this.allocation = allocation;
        this.need = new int[row][column];

        this.available = available;
        this.finish = new boolean[max.length];

        /*
        need = max - allocation
         */
        for(int i = 0; i < max.length; i++) {
            for(int j = 0; j < max[i].length; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    /**
     * Safe State를 판단하는 함수
     * @return safe state 여부를 판단하여 SafetyResult를 반환
     */
    public SafetyResult safety_check_bankers() {

        return null;
    }
}
