public class BankersAlgorithm {

    int [][] allocation;
    int [][] max;
    int [] available;

    public BankersAlgorithm(int[][] allocation, int[][] max, int[] available) {
        int row = max.length;
        int column = max[0].length;

        this.max = max;
        this.allocation = allocation;

        this.available = available;
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
    public SafetyResult safety_check_bankers(int[][] allocation, int[] available, int[][] need) {
        int [] sequence = new int[max.length];
        boolean[] finish = new boolean[max.length];

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
            if(idx == max.length) return SafetyResult.safe(sequence.clone());
        }

        return SafetyResult.unSafe();
    }

    /**
     * request 요청 시 safe state를 확인하고 요청을 허용할 지 거부할 지 판단하는 함수
     * @param process 몇 번 프로세스의 요청인지 확인하기 위한 파라미터
     * @param request 프로세스의 자원 요청 값
     * 1. request[i] <= need[i];
     * 2. request[i] <= available[i];
     * 3. available = available - request (복사본이어야함)
     * 4. allocation = allocation + request
     * 5. need = need - request
     * 6. safety_check_bankers() 실행
     * @return RequestResult
     */
    public RequestResult resource_request_bankers(int process, int[] request, int[][] need) {

        // 0. available, allocation, need 배열 복사
        int[] availableCopy = available.clone();
        int[][] allocationCopy = new int[allocation.length][allocation[0].length];
        int[][] needCopy = new int[need.length][need[0].length];

        for(int i = 0; i < allocation.length; i++) {
            allocationCopy[i] = allocation[i].clone();
            needCopy[i] = need[i].clone();
        }

        // 1. request[i] <= need[i];
        for(int i = 0; i < request.length; i++) {
            if(request[i] > needCopy[process][i]) {
                return new RequestResult(RequestResult.RequestStates.INVALID, null);
            }
        }

        // 2. request[i] <= available[i]
        for(int i = 0; i < request.length; i++) {
            if(request[i] > available[i]) {
                return new RequestResult(RequestResult.RequestStates.WAIT, null);
            }
            // 3. available = available - request
            availableCopy[i] = availableCopy[i] - request[i];
        }

        // 4. allocation = allocation + request
        for(int i = 0; i < request.length; i++) {
            allocationCopy[process][i] = allocationCopy[process][i] + request[i];
        }

        // 5. need = need - request
        for(int i = 0; i < request.length; i++) {
            needCopy[process][i] -= request[i];
        }

        // 6. safety_check_bankers()
        SafetyResult safetyResult = safety_check_bankers(allocationCopy, availableCopy, needCopy);

        return safetyResult.safeState() ? RequestResult.granted(RequestResult.RequestStates.GRANTED, safetyResult.safeSequence().clone()) : new RequestResult(RequestResult.RequestStates.DENIED, null);
    }
}
