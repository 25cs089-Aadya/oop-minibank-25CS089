  package util;
    import model.*;
public class CommandParser
{
    public static Command parse(String line)
    {
        if (line == null)
        {
            throw new IllegalArgumentException("Invalid command");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3)
        {
            throw new IllegalArgumentException("Invalid command format");
        }

        TransactionType type =
            TransactionType.valueOf(parts[0].toUpperCase());

        String accountNumber = parts[1];

        long amount = Long.parseLong(parts[2]);

        if (amount <= 0)
        {
            throw new IllegalArgumentException("Amount must be positive");
        }

        return new Command(type, accountNumber, amount);
    }
}
