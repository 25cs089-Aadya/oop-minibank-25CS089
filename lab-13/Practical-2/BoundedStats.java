
public class BoundedStats
{
    public static <N extends Number> double average(N[] values)
    {
        double sum = 0;

        for (N n : values)
        {
            sum += n.doubleValue();
        }

        return sum / values.length;
    }

    public static void main(String[] args)
    {
        Integer[] a = {10, 20, 30};
        Double[] b = {5.5, 10.5, 15.5};
        Long[] c = {100L, 200L, 300L};

        System.out.println("Integer Average: " + average(a));
        System.out.println("Double Average: " + average(b));
        System.out.println("Long Average: " + average(c));

        // String[] names = {"A", "B"};
        // average(names);  // Not allowed because String is not a Number
    }
}
