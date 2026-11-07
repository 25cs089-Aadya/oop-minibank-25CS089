package service;

import exception.BankException;
import model.Account;
import model.Command;
import model.TransactionType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor
{
    private ExecutorService pool = Executors.newFixedThreadPool(4);
    private Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account)
    {
        accounts.put(account.getAccountNumber(), account);
    }

    public void submit(Runnable task)
    {
        pool.execute(task);
    }

    // runs one command (deposit or withdraw) on the pool
    public void submit(Command cmd)
    {
        submit(() -> {
            Account account = accounts.get(cmd.accountNumber());

            if (account == null)
            {
                System.out.println("No account " + cmd.accountNumber());
                return;
            }

            try
            {
                if (cmd.type() == TransactionType.DEPOSIT)
                    account.deposit(cmd.amount());
                else if (cmd.type() == TransactionType.WITHDRAW)
                    account.withdraw(cmd.amount());

                System.out.println(Thread.currentThread().getName() + " -> " + cmd.type() + " " + cmd.amount() + " on " + cmd.accountNumber());
            }
            catch (BankException e)
            {
                System.out.println(Thread.currentThread().getName() + " -> failed: " + e.getMessage());
            }
        });
    }

    public void stop() throws InterruptedException
    {
        pool.shutdown();

        if (!pool.awaitTermination(30, TimeUnit.SECONDS))
        {
            pool.shutdownNow();
        }
    }
}
