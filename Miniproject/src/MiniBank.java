
import java.util.Scanner;

record BankInfo(String name, String branch) {
}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
}

public class MiniBank {

    public static void testAccounts() {

        Account[] accounts = {
            new Account("Aadya", 5000),
            new Account("Riya", 3000),
            new Account("Aman")
        };

        accounts[0].deposit(2000);
        accounts[1].withdraw(1000);
        accounts[2].deposit(4000);

        System.out.println("----- Account Details -----");

        for (Account a : accounts) {
            System.out.println(a.toString());
        }

        System.out.println("\nComparing Accounts:");

        System.out.println("Account 1 equals Account 2: "
                + accounts[0].equals(accounts[1]));

        Object obj = accounts[0];

        if (obj instanceof Account) {
            System.out.println("Object is an Account");
        }

        Customer customer = new Customer(
                "Aadya", "aadya@example.com", "9876543210"
        );

        customer.setAddress(
                new Customer.Address("Main Road", "Ahmedabad", "380001")
        );

        Customer copiedCustomer = customer.clone();

        System.out.println("Customer: " + customer.getName());
        System.out.println("City: " + customer.getAddress().getCity());
        System.out.println("Cloned Customer: " + copiedCustomer.getName());
    }

    public static void testValidation() {

        System.out.println("\n----- Validation Tests -----");

        System.out.println("Mobile valid: " +
                Validator.isValidMobile("9876543210"));
        System.out.println("Mobile invalid: " +
                Validator.isValidMobile("12345"));

        System.out.println("Email valid: " +
                Validator.isValidEmail("aadya@gmail.com"));
        System.out.println("Email invalid: " +
                Validator.isValidEmail("aadya.com"));

        System.out.println("PAN valid: " +
                Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN invalid: " +
                Validator.isValidPan("ABC123"));

        System.out.println("IFSC valid: " +
                Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC invalid: " +
                Validator.isValidIfsc("SBIN123"));

        System.out.println("\n----- Command Test -----");

        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println("Type: " + cmd.type());
        System.out.println("Account Number: " + cmd.accountNumber());
        System.out.println("Amount: " + cmd.amount());

        System.out.println("\n----- Statement Test -----");

        Account account = new Account("Aadya", 5000);

        System.out.println(StatementFormatter.buildStatement(account));
    }

    public static void main(String[] args) {

        testAccounts();
        testValidation();

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
