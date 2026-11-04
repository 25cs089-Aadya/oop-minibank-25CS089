interface Switchable
{
    void on();
    void off();
    boolean Ison();

    default void toggle()
    {
        if (Ison())
        {
            off();
        }
        else
        {
            on();
        }
    }
}

@FunctionalInterface
interface SwitchOnRule
{
    boolean MaySwitchOn(int hour, Switchable device);
}

class Fan implements Switchable
{
    boolean state;

    public void on()
    {
        state = true;
        System.out.println("Fan is ON");
    }

    public void off()
    {
        state = false;
        System.out.println("Fan is OFF");
    }

    public boolean Ison()
    {
        return state;
    }
}

class Light implements Switchable
{
    boolean status;

    public void on()
    {
        status = true;
        System.out.println("Light is ON");
    }

    public void off()
    {
        status = false;
        System.out.println("Light is OFF");
    }

    public boolean Ison()
    {
        return status;
    }
}

public class RemoteControl
{
    public static void main(String[] args)
    {
        Switchable fan = new Fan();
        Switchable light = new Light();

        Switchable[] devices = { fan, light };

       
        for (Switchable d : devices)
        {
            d.toggle();
        }

       
        SwitchOnRule rule = new SwitchOnRule()
        {
            public boolean MaySwitchOn(int hour, Switchable device)
            {
                return hour >= 6 && hour <= 22;
            }
        };

        if (rule.MaySwitchOn(7, light))
        {
            light.on();
        }
        else
        {
            System.out.println("Cannot switch on light");
        }

   
        SwitchOnRule rule2 =
            (hour, device) -> hour >= 6 && hour <= 22;

        if (rule2.MaySwitchOn(23, fan))
        {
            fan.on();
        }
        else
        {
            System.out.println("Cannot switch on fan");
        }
    }
}