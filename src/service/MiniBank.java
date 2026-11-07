package service;

import java.util.Scanner;

record BankInfo(String name, String branch) {
}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
}

public class MiniBank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank", "Ahmedabad");

        System.out.println("\nWelcome to " + bank);

        int choice;

        do {
            System.out.println("\n----- MiniBank Menu -----");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
                choice = 0;
                continue;
            }

            choice = sc.nextInt();

            String message = switch (choice) {
                case 1 -> "Open Account - To be implemented later";
                case 2 -> "Deposit - To be implemented later";
                case 3 -> "Withdraw - To be implemented later";
                case 4 -> "Transfer - To be implemented later";
                case 5 -> "Thank you for using MiniBank. Goodbye!";
                default -> "Invalid choice. Try again.";
            };

            System.out.println(message);

        } while (choice != 5);

        sc.close();
    }
}
