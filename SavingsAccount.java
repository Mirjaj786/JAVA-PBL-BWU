public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.03; // 3% annual interest

    public SavingsAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        super(accountNumber, accountHolderName, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            addTransaction("Withdrew (Savings): rs: " + amount);
            System.out.println("Successfully withdrew rs: " + amount + ". New balance: rs: " + balance);
        } else if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else {
            System.out.println("Insufficient funds in Savings Account. Current balance: rs: " + balance);
        }
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public void applyInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        addTransaction("Interest applied: rs: " + interest);
        System.out.println("Interest applied: rs: " + interest + ". New balance: rs: " + balance);
    }
}
