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

        // BankersAlgorithm 객체 생성
        BankersAlgorithm bankers = new BankersAlgorithm(allocation, max, available);

        // Safety Check 테스트
        SafetyResult res = bankers.safetyCheckBankers();
        System.out.println("Safe State: " + res.safeState());
        System.out.println("Safe Sequence: " +
                Arrays.toString(res.safeSequence()));

        // Safe Resource Request 테스트 (P1이 [1, 0, 2] 요청)
        int[] req1 = {1, 0, 2};
        RequestResult res1 = bankers.resourceRequestBankers(1, req1);

        if(res1.state() == RequestResult.RequestStates.GRANTED) {
            System.out.println("P1 요청 " + Arrays.toString(req1) + " 승인, new safe sequence: "+ Arrays.toString(res1.safeSequence()));
        } else {
            System.out.println("P1 요청 " + Arrays.toString(req1) + "거절");
        }

        // Safe Resource Request 테스트 (P0이 [4, 0, 0] 요청)
        int[] req2 = {4, 0, 0};
        RequestResult res2 = bankers.resourceRequestBankers(0, req2);

        if(res2.state() == RequestResult.RequestStates.GRANTED) {
            System.out.println("P0요청 " + Arrays.toString(req2) + " 승인, new safe sequence: "+ Arrays.toString(res2.safeSequence()));
        } else {
            System.out.println("P0 요청 " + Arrays.toString(req2) + " 거절");
        }

        // Safe Resource Request 테스트 (P1이 [2, 0, 0] 요청)
        int[] req3 = {2, 0, 0};
        RequestResult res3 = bankers.resourceRequestBankers(1, req3);

        if(res3.state() == RequestResult.RequestStates.GRANTED) {
            System.out.println("P1요청 " + Arrays.toString(req3) + " 승인, new safe sequence: "+ Arrays.toString(res3.safeSequence()));
        } else {
            System.out.println("P1 요청 " + Arrays.toString(req3) + " 거절");
        }

    }

}