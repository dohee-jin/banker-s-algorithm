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
     * 1. work = available;
     * 2. finish[i] = false;
     * 3. finish[i] = false && need[i] < work
     * 4. i번 프로세스 종료 후 work = work + allocation, finish[i] = true;
     * 5. finish[i] 가 모두 true 이면 safe state, safe sequence를 반환
     * @return safe state 여부를 판단하여 SafetyResult를 반환
     */
    public SafetyResult safety_check_bankers() {
        int [] sequence = new int[max.length];
        boolean progress;
        int idx = 0;

        // 1. work = available;
        int [] work = available.clone();

        // 2. finish[i] = false;
        int row = max.length;
        for (int i = 0; i < row; i++) {
            finish[i] = false;
        }

        // 3. finish[i] = false, need[i] < work;
        while(true) {
            progress = false;
            for(int i = 0; i < max.length; i++) {
                boolean canFinish = true;

                if (finish[i] == true) continue;
                for (int j = 0; j < max[i].length; j++) {
                    if(need[i][j] > work[j]) {
                        canFinish = false;
                        break;
                    }
                }
                if(canFinish) {
                    finish[i] = true;
                    sequence[idx++] = i;
                    for(int k = 0; k < work.length; k++) {
                        work[k] += allocation[i][k];
                    }
                    progress = true;
                }
            }

            if(!progress) break;
            if(idx == max.length) break;
        }


        if(idx == max.length) return SafetyResult.safe(sequence);
        else return SafetyResult.unSafe();
    }
}
