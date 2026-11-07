package service;

import model.*;
import util.*;

import static util.Validator.isValidMobile;

// runs the demo for each practical, e.g. java -cp out service.LabDemos 8
public class LabDemos {

    public static void main(String[] args) throws Exception {

        int lab = args.length > 0 ? Integer.parseInt(args[0]) : 0;

        if (lab == 0 || lab == 2 || lab == 3 || lab == 5) testAccounts();
        if (lab == 0 || lab == 4) testValidation();
        if (lab == 0 || lab == 6) testInterfaces();
        if (lab == 0 || lab == 7) testAnnotations();
    }

    public static void testAccounts() throws Exception {
        Account[] accounts = {
            new SavingsAccount("Aadya", 5000, 1000),
            new CurrentAccount("Riya", 3000, 2000),
            new FixedDepositAccount("Aman", 10000)
        };

        System.out.println("----- Account Details -----");

        for (Account a : accounts) {
            System.out.println(a);
            System.out.println("Interest Rate: " + a.interestRate() + "%");
            System.out.println();
        }

        accounts[0].deposit(2000);

        System.out.println("Savings withdrawal: " + accounts[0].withdraw(1000));
        System.out.println("Current withdrawal: " + accounts[1].withdraw(4000));
        System.out.println("Fixed Deposit can withdraw: " + accounts[2].canWithdraw(1000));

        System.out.println("\nUpdated Balances:");

        for (Account a : accounts) {
            System.out.println(a.getOwnerName() + ": " + a.getBalance());
        }

        if (accounts[0] instanceof SavingsAccount s) {
            System.out.println("Minimum Balance: " + s.getMinBalance());
        }

        System.out.println("Account 1 equals Account 2: " + accounts[0].equals(accounts[1]));

        Object obj = accounts[0];

        if (obj instanceof Account) {
            System.out.println("Object is an Account");
        }

        Customer customer = new Customer("Aadya", "aadya@example.com", "9876543210");
        customer.setAddress(new Customer.Address("Main Road", "Ahmedabad", "380001"));

        Customer copiedCustomer = customer.clone();

        System.out.println("Customer: " + customer.getName());
        System.out.println("City: " + customer.getAddress().getCity());
        System.out.println("Cloned Customer: " + copiedCustomer.getName());
    }

    public static void testValidation() {

        System.out.println("\n----- Validation Tests -----");

        System.out.println("Mobile valid: " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile invalid: " + Validator.isValidMobile("12345"));
        System.out.println("Email valid: " + Validator.isValidEmail("aadya@gmail.com"));
        System.out.println("Email invalid: " + Validator.isValidEmail("aadya.com"));
        System.out.println("PAN valid: " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN invalid: " + Validator.isValidPan("ABC123"));
        System.out.println("IFSC valid: " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC invalid: " + Validator.isValidIfsc("SBIN123"));

        System.out.println("\n----- Command Test -----");

        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println("Type: " + cmd.type());
        System.out.println("Account Number: " + cmd.accountNumber());
        System.out.println("Amount: " + cmd.amount());

        System.out.println("\n----- Statement Test -----");

        Account account = new SavingsAccount("Aadya", 5000, 1000);
        System.out.println(StatementFormatter.buildStatement(account));
    }

    public static void testInterfaces() {
        System.out.println("\n--- Interfaces ---");

        Account a1 = new SavingsAccount("Aadya", 5000, 1000);
        Account a2 = new FixedDepositAccount("Aman", 10000);

        System.out.println("Savings yearly interest: " + a1.yearlyInterest());
        System.out.println("FD yearly interest: " + a2.yearlyInterest());

        WithdrawRule rule1 = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount <= 2000 && account.canWithdraw(amount);
            }
        };

        WithdrawRule rule2 = (account, amount) -> amount <= 2000 && account.canWithdraw(amount);

        System.out.println("Anonymous class: " + rule1.allow(a1, 1500));
        System.out.println("Lambda: " + rule2.allow(a1, 1500));

        if (a2 instanceof Premium) {
            System.out.println(a2.getOwnerName() + " has a premium account");
        }

        System.out.println("Mobile valid: " + isValidMobile("9876543210"));
    }

    public static void testAnnotations() {
        System.out.println("\n--- Annotations ---");

        Account bad = new SavingsAccount("Riya", -100, 0);
        Account good = new SavingsAccount("Aadya", 5000, 1000);
        Account longName = new CurrentAccount("Aadya Ramesh Patel Shah", 3000, 1000);

        System.out.println("Errors for bad account:");
        for (String e : AnnotationValidator.validate(bad)) {
            System.out.println("  " + e);
        }

        System.out.println("Errors for good account: " + AnnotationValidator.validate(good).length);

        System.out.println("Errors for long name:");
        for (String e : AnnotationValidator.validate(longName)) {
            System.out.println("  " + e);
        }
    }
}
