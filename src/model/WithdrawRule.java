package model;

// Functional interface: a single method that decides if a withdrawal is allowed
public interface WithdrawRule
{
    boolean allow(Account account, long amount);
}