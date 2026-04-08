public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 5000.0; // Overdraft limit

    public CurrentAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        super(accountNumber, accountHolderName, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance + OVERDRAFT_LIMIT >= amount) {
            balance -= amount;
            addTransaction("Withdrew (Current): rs: " + amount);
            System.out.println("Successfully withdrew rs: " + amount + ". New balance: rs: " + balance);
        } else if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else {
            System.out.println("Overdraft limit exceeded. Current balance: rs: " + balance);
        }
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}
