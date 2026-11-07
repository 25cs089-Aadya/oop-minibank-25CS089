package model;

// Anything that earns interest on its balance
public interface InterestBearing
{
    double interestRate();

    long getBalance();

    // Default method: one shared interest formula for every account type
    default double yearlyInterest()
    {
        return getBalance() * interestRate() / 100;
    }
}