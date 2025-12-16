/**
 * Safe State 결과를 전달하는 DTO (Data Transfer Object)
 *
 * <DTO 패턴 적용 이유>
 * BankersAlgorithm의 내부 상태(allocation, need, available)를
 * Main에 직접 노출하지 않고, 필요한 결과값만 전달하여
 * 이를 통해 캡슐화, 정보 은닉, 계층 분리라는 자바의 객체지향 원칙을 지킬 수 있다.
 *
 * <Record>
 * Java 14부터 도입된 Record 클래스는 불변 데이터를 객체 간에 전달하는 작업을 간단하게 만들어준다.
 * Record 클래스를 사용하면 불필요한 코드를 제거할 수 있고, 적은 코드로도 명확한 의도를 표현할 수 있다.
 *
 * <Record의 특징>
 * - 멤버변수는 private final로 선언 (불변성 보장)
 * - 필드별 getter 자동 생성
 * - 모든 멤버변수를 인자로 하는 public 생성자 자동 생성
 * - equals, hashCode, toString 자동 생성
 * - 기본생성자는 제공하지 않음
 *
 * @param safeState
 * @param safeSequence
 */
public record SafetyResult(
        boolean safeState,
        int [] safeSequence
) {
    public static SafetyResult safe(int[] sequence) {
        return new SafetyResult(true, sequence);
    }

    public static SafetyResult unSafe() {
        return new SafetyResult(false, null);
    }
}
