/**
 * Resource Request 처리 결과를 전달하는 DTO (Data Transfer Object)
 *
 * <DTO 패턴 적용 이유>
 * BankersAlgorithm의 내부 상태(allocation, need, available)를
 * Main에 직접 노출하지 않고, 필요한 결과값만 전달하여
 * 캡슐화와 정보 은닉을 구현한다.
 *
 * <Record 사용 이유>
 * Java 14부터 도입된 Record 클래스는 불변 데이터를 객체 간에 전달하는
 * 작업을 간단하게 만들어준다.
 *
 * <Record의 특징>
 * - 멤버변수는 private final로 선언 (불변성 보장)
 * - 필드별 getter 자동 생성
 * - 모든 멤버변수를 인자로 하는 public 생성자 자동 생성
 * - equals, hashCode, toString 자동 생성
 * - 기본생성자는 제공하지 않음
 *
 * <RequestStates Enum 설명>
 * 요청 처리 결과를 타입 안전하게 관리하기 위한 열거형
 * - GRANTED: 요청 승인 (시스템이 Safe State 유지)
 * - WAIT: 자원 부족으로 대기 필요 (request > available)
 * - INVALID: 최대 요구량 초과 (request > need)
 * - DENIED: 요청 거부 (허용 시 Unsafe State 발생)
 *
 *
 * @param state 요청 처리 결과 상태 (GRANTED, WAIT, INVALID, DENIED)
 * @param safeSequence 요청 승인 시 새로운 안전 실행 순서 (거부 시 null)
 */
public record RequestResult (
        RequestStates state,
        int[] safeSequence

){
    /**
     * 요청 처리 결과 상태를 나타내는 열거형
     */
   public enum RequestStates {
       GRANTED, WAIT, INVALID, DENIED
   }

    /**
     * 요청 승인 결과를 생성하는 정적 팩토리 메서드
     *
     * @param state 요청 상태 (일반적으로 GRANTED)
     * @param sequence 새로운 안전 실행 순서
     * @return RequestResult 객체
     */
   public static RequestResult granted(RequestStates state, int[] sequence) {
       return new RequestResult(state, sequence);
   }
}
