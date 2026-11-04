
public class Driver
{
    public static void main(String[] args)
    {
        String[] passwords = {
            "abc",
            "abcdefghi",
            "Abc123",
            "Abcd1234!",
            "hello@123"
        };

        for (String pw : passwords)
        {
            System.out.println("Password: " + pw);

            String result = PasswordChecker.strength(pw);

            System.out.println("Strength: " + result);
            System.out.println();
        }
    }
}

