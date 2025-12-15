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
