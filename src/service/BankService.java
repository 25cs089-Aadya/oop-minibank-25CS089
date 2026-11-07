package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import model.*;
import util.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class BankService
{
    private Repository<Account> repo = new Repository<>();
    private Path accountsFile;
    private Path logFile;
    private Path logsFolder;
    private Path reportFile;

    public BankService(String dataFolder)
    {
        accountsFile = Path.of(dataFolder, "accounts.dat");
        logsFolder = Path.of(dataFolder, "logs");
        logFile = logsFolder.resolve("transactions.log");
        reportFile = Path.of(dataFolder, "report.txt");
    }

    public void load() throws IOException, ClassNotFoundException
    {
        for (Account a : StatePersister.load(accountsFile))
        {
            repo.save(a);
            Account.updateCounter(a.getAccountNumber());
        }
    }

    public void save() throws IOException
    {
        StatePersister.save(repo.findAll().toArray(new Account[0]), accountsFile);
    }

    public Account openAccount(String type, String name, long openingBalance) throws BankException
    {
        if (name == null || name.isBlank())
        {
            throw new BankException("Name cannot be empty");
        }

        if (openingBalance < 0)
        {
            throw new InvalidAmountException(openingBalance);
        }

        Account account = switch (type.toUpperCase())
        {
            case "SAVINGS" -> new SavingsAccount(name, openingBalance, 1000);
            case "CURRENT" -> new CurrentAccount(name, openingBalance, 5000);
            case "FD" -> new FixedDepositAccount(name, openingBalance);
            default -> throw new BankException("Unknown account type: " + type);
        };

        repo.save(account);
        log("OPEN " + account.getAccountNumber() + " " + openingBalance);
        return account;
    }

    public Account find(String accountNumber) throws AccountNotFoundException
    {
        Account account = repo.findById(accountNumber);

        if (account == null)
        {
            throw new AccountNotFoundException(accountNumber);
        }

        return account;
    }

    public void deposit(String accountNumber, long amount) throws BankException
    {
        find(accountNumber).deposit(amount);
        log("DEPOSIT " + accountNumber + " " + amount);
    }

    public void withdraw(String accountNumber, long amount) throws BankException
    {
        find(accountNumber).withdraw(amount);
        log("WITHDRAW " + accountNumber + " " + amount);
    }

    public void transfer(String from, String to, long amount) throws BankException
    {
        Account a = find(from);
        Account b = find(to);
        a.transfer(b, amount);
        log("TRANSFER " + from + " " + to + " " + amount);
    }

    public String statement(String accountNumber) throws AccountNotFoundException
    {
        return StatementFormatter.buildStatement(find(accountNumber));
    }

    public ArrayList<Account> listAccounts()
    {
        ArrayList<Account> list = repo.findAll();
        list.sort(null);
        return list;
    }

    public ArrayList<Account> searchByOwner(String name)
    {
        ArrayList<Account> list = new ArrayList<>();

        for (Account a : repo.findAll())
        {
            if (a.getOwnerName().equalsIgnoreCase(name))
            {
                list.add(a);
            }
        }

        return list;
    }

    // runs a batch of commands on the thread pool
    public void runBatch(String[] lines) throws InterruptedException
    {
        TransactionProcessor processor = new TransactionProcessor();

        for (String line : lines)
        {
            Command cmd = CommandParser.parse(line);

            processor.submit(() -> {
                try
                {
                    if (cmd.type() == TransactionType.DEPOSIT)
                        deposit(cmd.accountNumber(), cmd.amount());
                    else if (cmd.type() == TransactionType.WITHDRAW)
                        withdraw(cmd.accountNumber(), cmd.amount());

                    System.out.println(Thread.currentThread().getName() + " -> " + line + " done");
                }
                catch (BankException e)
                {
                    System.out.println(Thread.currentThread().getName() + " -> " + line + " failed: " + e.getMessage());
                }
            });
        }

        processor.stop();
    }

    public String report() throws IOException
    {
        return ReportGenerator.generate(logsFolder, reportFile);
    }

    private synchronized void log(String line)
    {
        try
        {
            TransactionLog.append(logFile, line);
        }
        catch (IOException e)
        {
            System.out.println("Could not write log: " + e.getMessage());
        }
    }
}
