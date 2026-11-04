
import java.util.*;

class VendingMachine
{
    enum Coin { ONE, TWO, FIVE, TEN }

    public static void main(String[] a)
    {
        final int SNACKPRICE = 15;
        int total = 0;

        Scanner sc = new Scanner(System.in);

        while (total < SNACKPRICE)
        {
            String input = sc.next().toUpperCase();
            Coin c;

            try {
                c = Coin.valueOf(input);
            }
            catch (Exception e) {
                System.out.println("Not a Valid Coin. Try Again !!");
                continue;
            }

            int value = switch (c) {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
            };

            total += value;

            System.out.println("Inserted so far : " + total);
        }

        sc.close();

        int change = total - SNACKPRICE;

        System.out.println("Paid. Change: " + change);
    }
}
