package service;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class BankServiceTest {

    private BankService newService() throws Exception {
        Path folder = Files.createTempDirectory("minibank-test");
        return new BankService(folder.toString());
    }

    @Test
    void depositIncreasesBalance() throws Exception {
        BankService service = newService();
        Account a = service.openAccount("SAVINGS", "Aadya", 5000);

        service.deposit(a.getAccountNumber(), 2000);

        assertEquals(7000, a.getBalance());
    }

    @Test
    void withdrawOverLimitThrows() throws Exception {
        BankService service = newService();
        Account a = service.openAccount("SAVINGS", "Aadya", 5000);

        assertThrows(InsufficientFundsException.class, () -> service.withdraw(a.getAccountNumber(), 10000));
        assertEquals(5000, a.getBalance());
    }

    @Test
    void negativeDepositThrows() throws Exception {
        BankService service = newService();
        Account a = service.openAccount("CURRENT", "Riya", 1000);

        assertThrows(InvalidAmountException.class, () -> service.deposit(a.getAccountNumber(), -50));
    }

    @Test
    void concurrentDepositsKeepBalanceCorrect() throws Exception {
        BankService service = newService();
        Account a = service.openAccount("SAVINGS", "Aadya", 0);

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new AccountWorker(a, 1000, 1, true));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        assertEquals(10000, a.getBalance());
    }

    @Test
    void saveAndLoadKeepsData() throws Exception {
        Path folder = Files.createTempDirectory("minibank-test");

        BankService first = new BankService(folder.toString());
        Account a = first.openAccount("SAVINGS", "Aadya", 5000);
        first.save();

        BankService second = new BankService(folder.toString());
        second.load();

        assertEquals(5000, second.find(a.getAccountNumber()).getBalance());
        assertEquals("Aadya", second.find(a.getAccountNumber()).getOwnerName());
    }
}
