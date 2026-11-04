import java.util.Objects;

public class Point
{
    private int x;
    private int y;

    Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof Point))
        {
            return false;
        }

        Point p = (Point)o;

        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }
}