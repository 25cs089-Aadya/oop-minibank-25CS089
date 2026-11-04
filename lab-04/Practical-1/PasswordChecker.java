
public class PasswordChecker
{
    public static String strength(String pw)
    {
        int count = 0;

        if (pw.length() >= 8)
        {
            count++;
            System.out.println("Length >= 8: Passed");
        }
        else
        {
            System.out.println("Length >= 8: Failed");
        }

        if (pw.matches(".*[A-Z].*"))
        {
            count++;
            System.out.println("Uppercase: Passed");
        }
        else
        {
            System.out.println("Uppercase: Failed");
        }

        
        if (pw.matches(".*[0-9].*"))
        {
            count++;
            System.out.println("Digit: Passed");
        }
        else
        {
            System.out.println("Digit: Failed");
        }

        if (pw.matches(".*[^a-zA-Z0-9].*"))
        {
            count++;
            System.out.println("Special character: Passed");
        }
        else
        {
            System.out.println("Special character: Failed");
        }

        if (count <= 1)
        {
            return "Weak";
        }
        else if (count <= 3)
        {
            return "Medium";
        }
        else
        {
            return "Strong";
        }
    }
}