package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;

import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account>
{
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;

    @MaxLength(20)
    private String ownerName;

    @Positive
    private long balance;

    private boolean active;

    private static long accountCounter = 0;

    private static String generateAccountNumber()
    {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long openingBalance)
    {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName)
    {
        this(ownerName, 0);
    }

    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    // how much can be taken out right now
    public long availableBalance()
    {
        return balance;
    }

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(amount);
        }

        balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(amount);
        }

        if (!canWithdraw(amount))
        {
            throw new InsufficientFundsException(amount - availableBalance());
        }

        balance -= amount;
        return true;
    }

    // not synchronized on purpose, only used to show the race condition
    public void depositWithoutLock(long amount)
    {
        long current = balance;
        Thread.yield();
        balance = current + amount;
    }

    public void transfer(Account to, long amount) throws BankException
    {
        try
        {
            withdraw(amount);
            to.deposit(amount);
            System.out.println("Transferred " + amount + " from " + accountNumber + " to " + to.getAccountNumber());
        }
        catch (BankException e)
        {
            System.out.println("Transfer failed: " + e.getMessage());
            throw e;
        }
        finally
        {
            System.out.println("Transfer attempt finished");
        }
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    @Override
    public long getBalance()
    {
        return balance;
    }

    public boolean isActive()
    {
        return active;
    }

    @Override
    public int compareTo(Account other)
    {
        return accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public String toString()
    {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof Account))
            return false;

        Account a = (Account) o;
        return accountNumber.equals(a.accountNumber);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(accountNumber);
    }
}
