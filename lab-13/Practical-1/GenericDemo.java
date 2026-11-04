
class Box<T>
{
    private T value;

    public void set(T value)
    {
        this.value = value;
    }

    public T get()
    {
        return value;
    }
}

class Pair<A, B>
{
    A first;
    B second;

    Pair(A first, B second)
    {
        this.first = first;
        this.second = second;
    }
}

public class GenericDemo
{
    public static <T> T firstElement(T[] arr)
    {
        return arr[0];
    }

    public static void main(String[] args)
    {
        Box<Integer> b1 = new Box<>();
        b1.set(100);

        Box<String> b2 = new Box<>();
        b2.set("Hello");

        System.out.println("Integer Box: " + b1.get());
        System.out.println("String Box: " + b2.get());

        Pair<String, Integer> p1 = new Pair<>("Aadya", 89);
        Pair<Integer, String> p2 = new Pair<>(1, "Java");

        System.out.println("Pair 1: " + p1.first + ", " + p1.second);
        System.out.println("Pair 2: " + p2.first + ", " + p2.second);

        String[] names = {"Aadya", "Riya", "Aman"};
        Integer[] numbers = {10, 20, 30};

        System.out.println("First name: " + firstElement(names));
        System.out.println("First number: " + firstElement(numbers));
    }
}
