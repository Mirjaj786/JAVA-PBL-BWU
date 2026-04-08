public class BankExceptions {
    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    public static class AccountNotFoundException extends Exception {
        public AccountNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidAmountException extends Exception {
        public InvalidAmountException(String message) {
            super(message);
        }
    }
}
