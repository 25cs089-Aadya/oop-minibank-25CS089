package util;

public class AuditSession implements AutoCloseable
{
    private String name;

    public AuditSession(String name)
    {
        this.name = name;
        System.out.println("Audit session opened: " + name);
    }

    @Override
    public void close()
    {
        System.out.println("Audit session closed: " + name);
    }
}
