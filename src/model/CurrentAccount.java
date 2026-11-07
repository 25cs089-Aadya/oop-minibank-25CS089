package model;

public class CurrentAccount extends Account
{
    private long overdraftLimit;

    public CurrentAccount(String name, long balance, long overdraftLimit)
    {
        super(name, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate()
    {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount)
    {
        return amount > 0 && getBalance() - amount >= -overdraftLimit;
    }

    @Override
    public long availableBalance()
    {
        return getBalance() + overdraftLimit;
    }

    public long getOverdraftLimit()
    {
        return overdraftLimit;
    }
}
