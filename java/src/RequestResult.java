public record RequestResult (
        RequestStates state,
        int[] safeSequence

){
   public enum RequestStates {
       GRANTED, WAIT, INVALID, DENIED
   }

   public static RequestResult granted(RequestStates state, int[] sequence) {
       return new RequestResult(state, sequence);
   }
}
