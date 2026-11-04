@FunctionalInterface
interface Notifier
{
    void send(String msg);
}

interface Urgent
{
}

class UrgentNotifier implements Notifier, Urgent
{
    private Notifier notifier;

    UrgentNotifier(Notifier notifier)
    {
        this.notifier = notifier;
    }

    public void send(String msg)
    {
        notifier.send(msg);
    }
}

public class Broadcast
{
    public static void main(String[] args)
    {
        Notifier email = (msg) ->
        {
            System.out.println("Email: " + msg);
        };

        Notifier sms = (msg) ->
        {
            System.out.println("SMS: " + msg);
        };

        Notifier urgentSms = new UrgentNotifier(sms);

        Notifier[] senders = { email, urgentSms };

        String msg = "Server down";

        for (Notifier s : senders)
        {
            s.send(msg);

            if (s instanceof Urgent)
            {
                s.send(msg);
            }
        }
    }
}