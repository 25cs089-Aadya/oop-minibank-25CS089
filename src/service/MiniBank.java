package service;

import exception.BankException;
import model.Account;
import util.Validator;

import java.util.Scanner;

record BankInfo(String name, String branch) {
}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, STATEMENT, LIST_ACCOUNTS, SEARCH_BY_OWNER, RUN_BATCH, REPORT, EXIT
}

public class MiniBank {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        BankInfo bank = new BankInfo("MiniBank", "Ahmedabad");
        BankService service = new BankService("data");

        try {
            service.load();
            System.out.println("Loaded " + service.listAccounts().size() + " saved account(s)");
        } catch (Exception e) {
            System.out.println("Could not load saved accounts: " + e.getMessage());
        }

        System.out.println("\nWelcome to " + bank);

        MenuOption option;

        do {
            System.out.println("\n----- MiniBank Menu -----");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Statement");
            System.out.println("6. List Accounts");
            System.out.println("7. Search by Owner");
            System.out.println("8. Run Batch");
            System.out.println("9. Report");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            int choice = readInt();

            if (choice < 1 || choice > 10) {
                System.out.println("Invalid choice. Try again.");
                option = null;
                continue;
            }

            option = MenuOption.values()[choice - 1];

            try {
                switch (option) {
                    case OPEN_ACCOUNT -> {
                        String type = ask("Type (SAVINGS/CURRENT/FD): ");
                        String name = ask("Owner name: ");
                        long amount = readAmount("Opening balance: ");
                        Account a = service.openAccount(type, name, amount);
                        System.out.println("Account opened: " + a.getAccountNumber());
                    }
                    case DEPOSIT -> {
                        String acc = ask("Account number: ");
                        long amount = readAmount("Amount: ");
                        service.deposit(acc, amount);
                        System.out.println("Deposited. Balance: " + service.find(acc).getBalance());
                    }
                    case WITHDRAW -> {
                        String acc = ask("Account number: ");
                        long amount = readAmount("Amount: ");
                        service.withdraw(acc, amount);
                        System.out.println("Withdrawn. Balance: " + service.find(acc).getBalance());
                    }
                    case TRANSFER -> {
                        String from = ask("From account: ");
                        String to = ask("To account: ");
                        long amount = readAmount("Amount: ");
                        service.transfer(from, to, amount);
                    }
                    case STATEMENT -> {
                        String acc = ask("Account number: ");
                        System.out.println(service.statement(acc));
                    }
                    case LIST_ACCOUNTS -> {
                        for (Account a : service.listAccounts()) {
                            System.out.println(a);
                        }
                    }
                    case SEARCH_BY_OWNER -> {
                        String name = ask("Owner name: ");
                        for (Account a : service.searchByOwner(name)) {
                            System.out.println(a);
                        }
                    }
                    case RUN_BATCH -> {
                        String acc = ask("Account number for batch: ");
                        String[] batch = new String[10];
                        for (int i = 0; i < batch.length; i++) {
                            batch[i] = (i % 2 == 0 ? "DEPOSIT " : "WITHDRAW ") + acc + " 100";
                        }
                        service.runBatch(batch);
                        System.out.println("Batch done. Balance: " + service.find(acc).getBalance());
                    }
                    case REPORT -> System.out.println(service.report());
                    case EXIT -> {
                        service.save();
                        System.out.println("Accounts saved. Thank you for using MiniBank. Goodbye!");
                    }
                }
            } catch (BankException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }

        } while (option != MenuOption.EXIT);

        sc.close();
    }

    private static String ask(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt() {
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static long readAmount(String prompt) throws BankException {
        String text = ask(prompt);
        if (!Validator.isValidAmount(text)) {
            throw new BankException("Enter a positive whole number");
        }
        return Long.parseLong(text);
    }
}
