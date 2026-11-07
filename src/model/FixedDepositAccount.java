package model;

public class FixedDepositAccount extends Account implements Premium
{
    public FixedDepositAccount(String name, long balance)
    {
        super(name, balance);
    }

    @Override
    public double interestRate()
    {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount)
    {
        return false;
    }

    @Override
    public long availableBalance()
    {
        return 0;
    }
}
