package service;

import exception.*;
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
        if (lab == 0 || lab == 8) testExceptions();
        if (lab == 0 || lab == 9) testThreads();
        if (lab == 0 || lab == 10) testThreadPool();
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

    public static void testExceptions() {
        System.out.println("\n--- Exceptions ---");

        Account acc = new CurrentAccount("Aadya", 1000, 0);
        Account other = new SavingsAccount("Riya", 2000, 500);

        try {
            acc.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: short by " + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Balance is still " + acc.getBalance());
        }

        try {
            acc.deposit(-50);
        } catch (InvalidAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }

        try {
            acc.transfer(other, 300);
            acc.transfer(other, 9000);
        } catch (BankException e) {
            System.out.println("Caught in main: " + e.getMessage());
        }

        try (AuditSession session = new AuditSession("daily check")) {
            System.out.println("Checking balances...");
            other.withdraw(100000);
        } catch (BankException e) {
            System.out.println("Inside try-with-resources: " + e.getMessage());
        }
    }

    public static void testThreads() throws InterruptedException {
        System.out.println("\n--- Threads ---");

        for (int round = 1; round <= 2; round++) {
            boolean safe = round == 2;
            Account acc = new SavingsAccount("Aadya", 0, 0);
            Thread[] threads = new Thread[10];

            System.out.println(safe ? "\nWith synchronization:" : "Without synchronization:");

            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(new AccountWorker(acc, 1000, 1, safe), "Worker-" + (i + 1));
            }

            System.out.println("Worker-1 state before start: " + threads[0].getState());

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }

            System.out.println("Worker-1 state after join: " + threads[0].getState());
            System.out.println("Expected balance: 10000, Actual balance: " + acc.getBalance());
        }
    }

    public static void testThreadPool() throws InterruptedException {
        System.out.println("\n--- Thread Pool ---");

        TransactionProcessor processor = new TransactionProcessor();
        Account acc = new SavingsAccount("Aadya", 5000, 1000);
        processor.addAccount(acc);

        for (int i = 1; i <= 8; i++) {
            String type = i % 2 == 0 ? "WITHDRAW" : "DEPOSIT";
            processor.submit(CommandParser.parse(type + " " + acc.getAccountNumber() + " 200"));
        }

        processor.stop();
        System.out.println("Balance after batch: " + acc.getBalance());

        System.out.println("\n--- Producer Consumer ---");

        TransactionBuffer buffer = new TransactionBuffer();
        Account target = new SavingsAccount("Riya", 1000, 0);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Command cmd = new Command(TransactionType.DEPOSIT, target.getAccountNumber(), i * 100);
                    buffer.put(cmd);
                    System.out.println("Produced deposit of " + cmd.amount());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Command cmd = buffer.take();
                    target.deposit(cmd.amount());
                    System.out.println("Consumed deposit of " + cmd.amount());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("Riya's balance: " + target.getBalance());

        System.out.println("\n--- Deadlock ---");

        Account a = new SavingsAccount("A", 5000, 0);
        Account b = new SavingsAccount("B", 5000, 0);

        Thread t1 = new Thread(() -> unsafeTransfer(a, b, 100));
        Thread t2 = new Thread(() -> unsafeTransfer(b, a, 100));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        t1.join(2000);
        t2.join(2000);

        if (t1.isAlive() && t2.isAlive()) {
            System.out.println("Deadlock: both threads are stuck waiting for each other");
        }

        Account c = new SavingsAccount("C", 5000, 0);
        Account d = new SavingsAccount("D", 5000, 0);

        Thread t3 = new Thread(() -> safeTransfer(c, d, 100));
        Thread t4 = new Thread(() -> safeTransfer(d, c, 300));
        t3.start();
        t4.start();
        t3.join();
        t4.join();

        System.out.println("Fixed version finished. C: " + c.getBalance() + ", D: " + d.getBalance());
    }

    // locks in the order given, so two opposite transfers can deadlock
    private static void unsafeTransfer(Account from, Account to, long amount) {
        synchronized (from) {
            sleep(100);
            synchronized (to) {
                move(from, to, amount);
            }
        }
    }

    // always locks the smaller account number first
    private static void safeTransfer(Account from, Account to, long amount) {
        Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = first == from ? to : from;

        synchronized (first) {
            sleep(100);
            synchronized (second) {
                move(from, to, amount);
                System.out.println(Thread.currentThread().getName() + " moved " + amount + " from " + from.getOwnerName() + " to " + to.getOwnerName());
            }
        }
    }

    private static void move(Account from, Account to, long amount) {
        try {
            from.withdraw(amount);
            to.deposit(amount);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
