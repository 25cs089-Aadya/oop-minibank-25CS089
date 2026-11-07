package model;

public interface InterestBearing
{
    double interestRate();

    long getBalance();

    // same formula for every account type
    default double yearlyInterest()
    {
        return getBalance() * interestRate() / 100;
    }
}
