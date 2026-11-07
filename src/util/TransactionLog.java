package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TransactionLog
{
    public static void append(Path file, String line) throws IOException
    {
        if (file.getParent() != null)
        {
            Files.createDirectories(file.getParent());
        }

        Files.write(file, List.of(line), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
