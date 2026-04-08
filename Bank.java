import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank {
    private Map<String, Account> accounts;
    private int nextAccountNumber;
    private static final String DATA_FILE = "bank_data.csv";

    public Bank() {
        this.accounts = new HashMap<>();
        this.nextAccountNumber = 1001; // Starting account number
        loadData();
    }

    public void createAccount(String holderName, double initialDeposit, String type) {
        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative.");
            return;
        }
        String accNum = "ACC" + nextAccountNumber++;
        Account newAccount;
        if (type.equalsIgnoreCase("Savings")) {
            newAccount = new SavingsAccount(accNum, holderName, initialDeposit);
        } else {
            newAccount = new CurrentAccount(accNum, holderName, initialDeposit);
        }
        accounts.put(accNum, newAccount);
        System.out.println("Account created successfully!");
        newAccount.displayAccountInfo();
        saveData();
    }

    public void deposit(String accountNumber, double amount) {
        Account acc = accounts.get(accountNumber);
        if (acc != null) {
            acc.deposit(amount);
            saveData();
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account acc = accounts.get(accountNumber);
        if (acc != null) {
            acc.withdraw(amount);
            saveData();
        } else {
            System.out.println("Account not found.");
        }
    }

    public void checkBalance(String accountNumber) {
        Account acc = accounts.get(accountNumber);
        if (acc != null) {
            acc.displayAccountInfo();
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAcc = accounts.get(fromAccountNumber);
        Account toAcc = accounts.get(toAccountNumber);

        if (fromAcc == null) {
            System.out.println("Source account not found.");
            return;
        }
        if (toAcc == null) {
            System.out.println("Destination account not found.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return;
        }
        if (fromAcc.getBalance() < amount) {
            System.out.println("Insufficient funds in the source account.");
            return;
        }

        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
        fromAcc.addTransaction("Transferred rs: " + amount + " to " + toAccountNumber);
        toAcc.addTransaction("Received rs: " + amount + " from " + fromAccountNumber);
        System.out.println(
                "Successfully transferred rs: " + amount + " from " + fromAccountNumber + " to " + toAccountNumber);
        saveData();
    }

    public void showTransactionHistory(String accountNumber) {
        Account acc = accounts.get(accountNumber);
        if (acc != null) {
            System.out.println("\nTransaction History for " + accountNumber + ":");
            for (String txn : acc.getTransactionHistory()) {
                System.out.println(txn);
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    // Admin Methods
    public void listAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found in the system.");
            return;
        }
        System.out.println("\n--- All Bank Accounts ---");
        for (Account acc : accounts.values()) {
            System.out.printf("Acc#: %s | Name: %-15s | Type: %-8s | Balance: rs: %.2f\n",
                    acc.getAccountNumber(), acc.getAccountHolderName(), acc.getAccountType(), acc.getBalance());
        }
        System.out.println("--------------------------");
    }

    // File I/O
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Account acc : accounts.values()) {
                writer.println(acc.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists())
            return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0];
                    String accNum = parts[1];
                    String name = parts[2];
                    double balance = Double.parseDouble(parts[3]);

                    Account acc;
                    if (type.equals("Savings")) {
                        acc = new SavingsAccount(accNum, name, balance);
                    } else {
                        acc = new CurrentAccount(accNum, name, balance);
                    }
                    accounts.put(accNum, acc);

                    // Update nextAccountNumber to avoid duplicates
                    int num = Integer.parseInt(accNum.substring(3));
                    if (num >= nextAccountNumber) {
                        nextAccountNumber = num + 1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
