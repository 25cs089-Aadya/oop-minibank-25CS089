package model;

// Anything that money can be paid into or taken out of
public interface Transactable
{
    void deposit(long amount);

    boolean withdraw(long amount);
}