import java.util.*;

class DivideByZeroException extends Exception
{
    DivideByZeroException(String message)
    {
        super(message);
    }
}

class GuardedCalculator
{
    public static void main(String[] args)
    {
        boolean success = false;
        int attempt = 0;

        Scanner sc = new Scanner(System.in);

while (!success)
        {
            try
            {
                    attempt++;

                System.out.print("first number: ");
                double n1 = Double.parseDouble(sc.nextLine());

                System.out.print(" operator: ");
                String op = sc.nextLine().trim();
                System.out.print(" second number: ");
                double n2 = Double.parseDouble(sc.nextLine());

                if (n2 == 0 && op.equals("/"))
                {
                    throw new DivideByZeroException( "Error: Cannot divide by zero");
                }

                double result;
  switch (op)
                {
                    case "+" -> result = n1 + n2;
                    case "-" -> result = n1 - n2;
                    case "*" -> result = n1 * n2;
                    case "/" -> result = n1 / n2;
                    default -> throw new IllegalArgumentException("invalid op");
                }

                System.out.println("result: " + result);
                System.out.println("attempts: " + attempt);

                success = true;
            }
 catch (NumberFormatException e)
            {
                System.out.println("invalid no ");
            }
            
    catch (DivideByZeroException e)
            {
                System.out.println(e.getMessage());
            }
catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
finally
            {
                System.out.println("Attempt " + attempt + " logged");
            }
        }

        sc.close();
    }
}