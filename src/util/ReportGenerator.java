package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator
{
    public static String generate(Path logsFolder, Path reportFile) throws IOException
    {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("----- End of Day Report -----\n");

        if (Files.exists(logsFolder))
        {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(logsFolder))
            {
                for (Path file : files)
                {
                    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                    // skip empty files and anything that is not a .log file
                    if (!file.toString().endsWith(".log") || attr.size() == 0)
                    {
                        continue;
                    }

                    sb.append("File: ").append(file.getFileName())
                      .append(", Size: ").append(attr.size()).append(" bytes")
                      .append(", Last modified: ").append(attr.lastModifiedTime()).append("\n");

                    try (BufferedReader reader = Files.newBufferedReader(file))
                    {
                        String line;

                        while ((line = reader.readLine()) != null)
                        {
                            String[] parts = line.trim().split("\\s+");

                            if (parts.length < 3)
                                continue;

                            long amount = Long.parseLong(parts[parts.length - 1]);

                            if (parts[0].equals("DEPOSIT"))
                                totalDeposits += amount;
                            else if (parts[0].equals("WITHDRAW"))
                                totalWithdrawals += amount;
                            else if (parts[0].equals("TRANSFER"))
                            {
                                totalWithdrawals += amount;
                                totalDeposits += amount;
                            }
                        }
                    }
                }
            }
        }

        sb.append("Total deposits: ").append(totalDeposits).append("\n");
        sb.append("Total withdrawals: ").append(totalWithdrawals).append("\n");
        sb.append("Net change: ").append(totalDeposits - totalWithdrawals).append("\n");
        sb.append("-----------------------------");

        if (reportFile.getParent() != null)
        {
            Files.createDirectories(reportFile.getParent());
        }

        Files.writeString(reportFile, sb.toString());

        return sb.toString();
    }
}
