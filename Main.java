import java.util.Scanner;

public class Main {
    private static final String ADMIN_PASSCODE = "Mirjaj@123";

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        printHeader();

        while (running) {
            System.out.println("\n================ Main Menu ================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance / Info");
            System.out.println("5. Transfer Money");
            System.out.println("6. Transaction History");
            System.out.println("7. Admin Menu");
            System.out.println("8. Exit");
            System.out.print("Enter your choice (1-8): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    createAccountFlow(bank, scanner);
                    break;
                case 2:
                    depositFlow(bank, scanner);
                    break;
                case 3:
                    withdrawFlow(bank, scanner);
                    break;
                case 4:
                    checkBalanceFlow(bank, scanner);
                    break;
                case 5:
                    transferFlow(bank, scanner);
                    break;
                case 6:
                    historyFlow(bank, scanner);
                    break;
                case 7:
                    adminFlow(bank, scanner);
                    break;
                case 8:
                    running = false;
                    System.out.println("---------------------------------------------");
                    System.out.println("Thank you for using the Bank Management System. Goodbye!");
                    System.out.println("---------------------------------------------");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1-8.");
            }
        }
        scanner.close();
    }

    private static void printHeader() {
        System.out.println("*********************************************");
        System.out.println("*                                           *");
        System.out.println("*       BANK MANAGEMENT SYSTEM - PBL        *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
    }

    private static void createAccountFlow(Bank bank, Scanner scanner) {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter account type (Savings/Current): ");
        String type = scanner.nextLine();
        if (!type.equalsIgnoreCase("Savings") && !type.equalsIgnoreCase("Current")) {
            System.out.println("Invalid type. Must be 'Savings' or 'Current'.");
            return;
        }
        try {
            System.out.print("Enter initial deposit amount: rs: ");
            double initialDeposit = Double.parseDouble(scanner.nextLine());
            bank.createAccount(name, initialDeposit, type);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Account creation failed.");
        }
    }

    private static void depositFlow(Bank bank, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        try {
            System.out.print("Enter amount to deposit: rs: ");
            double amount = Double.parseDouble(scanner.nextLine());
            bank.deposit(accNum, amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    private static void withdrawFlow(Bank bank, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        try {
            System.out.print("Enter amount to withdraw: rs: ");
            double amount = Double.parseDouble(scanner.nextLine());
            bank.withdraw(accNum, amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    private static void checkBalanceFlow(Bank bank, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        bank.checkBalance(accNum);
    }

    private static void transferFlow(Bank bank, Scanner scanner) {
        System.out.print("Enter source account number: ");
        String from = scanner.nextLine();
        System.out.print("Enter destination account number: ");
        String to = scanner.nextLine();
        try {
            System.out.print("Enter amount to transfer: rs: ");
            double amount = Double.parseDouble(scanner.nextLine());
            bank.transfer(from, to, amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    private static void historyFlow(Bank bank, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        bank.showTransactionHistory(accNum);
    }

    private static void adminFlow(Bank bank, Scanner scanner) {
        System.out.print("Enter Admin Passcode: ");
        String pass = scanner.nextLine();
        if (!pass.trim().equals(ADMIN_PASSCODE)) {
            System.out.println("INCORRECT PASSCODE. Access Denied.");
            return;
        }

        System.out.println("\n--- ADMIN PANEL ---");
        System.out.println("1. List All Accounts");
        System.out.println("2. Back to Main Menu");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            bank.listAllAccounts();
        }
    }
}
