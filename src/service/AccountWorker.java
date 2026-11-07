package service;

import exception.InvalidAmountException;
import model.Account;

public class AccountWorker implements Runnable
{
    private Account account;
    private int times;
    private long amount;
    private boolean safe;

    public AccountWorker(Account account, int times, long amount, boolean safe)
    {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.safe = safe;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < times; i++)
        {
            if (safe)
            {
                try
                {
                    account.deposit(amount);
                }
                catch (InvalidAmountException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                account.depositWithoutLock(amount);
            }
        }

        System.out.println(Thread.currentThread().getName() + " finished");
    }
}
