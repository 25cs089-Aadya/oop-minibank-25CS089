
import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
            "Khushi", "Aarav", "Priya", "Khushi",
            "Rahul", "Aarav", "Neha", "Priya"
        );

        HashSet<String> uniqueUsers = new HashSet<>(logins);

        LinkedHashSet<String> orderedUsers = new LinkedHashSet<>(logins);

        System.out.println("Total Unique Visitors: " + uniqueUsers.size());

        System.out.println("First Three Distinct Visitors:");

        int count = 0;

        for (String user : orderedUsers) {
            if (count == 3) {
                break;
            }

            System.out.println(user);
            count++;
        }
    }
}