
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String[] logs = {
            "10:05 alice Hello there",
            "10:10 bob How are you",
            "10:15 charlie",
        };

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        int count = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {
            String[] parts = ChatFilter.splitline(line);

            if (parts == null)
                continue;

            if (ChatFilter.hasKeyword(parts[2], keyword)) {
                count++;
                report.append(parts[0]).append(" ")
                      .append(parts[1]).append(": ")
                      .append(parts[2]).append("\n");
            }
        }

        System.out.println("Matches: " + count);
        System.out.print(report);

        sc.close();
    }
}
