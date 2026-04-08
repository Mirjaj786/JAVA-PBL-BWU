import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;
    protected List<String> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Initial deposit: rs: " + initialDeposit);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(String message) {
        transactionHistory.add(java.time.LocalDateTime.now() + " - " + message);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposited: rs: " + amount);
            System.out.println("Successfully deposited rs: " + amount + ". New balance: rs: " + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public abstract void withdraw(double amount);

    public void displayAccountInfo() {
        System.out.println("---------------------------------------------");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + getAccountType());
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: rs: " + balance);
        System.out.println("---------------------------------------------");
    }

    public abstract String getAccountType();

    public String toCSV() {
        return getAccountType() + "," + accountNumber + "," + accountHolderName + "," + balance;
    }
}
