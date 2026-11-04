
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class LogAnalyzer
{
    public static void main(String[] args) throws Exception
    {
        Path[] files = {
            Paths.get("log1.txt"),
            Paths.get("log2.txt"),
            Paths.get("log3.txt")
        };

        Files.write(files[0], Arrays.asList("Login successful", "Error found"));
        Files.write(files[1], Arrays.asList("User logged out", "Error in system"));
        Files.write(files[2], Arrays.asList("Server started", "No errors"));

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        int totalLines = 0;
        int matches = 0;

        for (Path p : files)
        {
            System.out.println("\nFile: " + p.getFileName());

            try (BufferedReader br = Files.newBufferedReader(p))
            {
                String line;

                while ((line = br.readLine()) != null)
                {
                    totalLines++;

                    if (line.toLowerCase().contains(keyword.toLowerCase()))
                    {
                        matches++;
                    }
                }
            }

            System.out.println("Size: " + Files.readAttributes(
                p, java.nio.file.attribute.BasicFileAttributes.class).size() + " bytes");
        }

        System.out.println("\nTotal lines: " + totalLines);
        System.out.println("Keyword matches: " + matches);

        sc.close();
    }
}
