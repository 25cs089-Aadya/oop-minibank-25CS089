package service;

import model.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

public class AccountStore
{
    private HashMap<String, Account> accounts = new HashMap<>();
    private ArrayList<String> history = new ArrayList<>();

    public void add(Account account)
    {
        accounts.put(account.getAccountNumber(), account);
        history.add("ADDED " + account.getAccountNumber());
    }

    public Account get(String accountNumber)
    {
        return accounts.get(accountNumber);
    }

    public Collection<Account> getAll()
    {
        return accounts.values();
    }

    public ArrayList<String> getHistory()
    {
        return history;
    }

    public ArrayList<Account> sortedByBalance()
    {
        ArrayList<Account> list = new ArrayList<>(accounts.values());
        list.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return list;
    }

    public ArrayList<Account> balanceAbove(long amount)
    {
        ArrayList<Account> list = new ArrayList<>();

        for (Account a : accounts.values())
        {
            if (a.getBalance() > amount)
            {
                list.add(a);
            }
        }

        return list;
    }
}
