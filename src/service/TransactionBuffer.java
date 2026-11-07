package service;

import model.Command;

// small shared buffer for the producer-consumer demo
public class TransactionBuffer
{
    private Command[] items = new Command[3];
    private int count = 0;
    private int putIndex = 0;
    private int takeIndex = 0;

    public synchronized void put(Command cmd) throws InterruptedException
    {
        while (count == items.length)
        {
            wait();
        }

        items[putIndex] = cmd;
        putIndex = (putIndex + 1) % items.length;
        count++;
        notifyAll();
    }

    public synchronized Command take() throws InterruptedException
    {
        while (count == 0)
        {
            wait();
        }

        Command cmd = items[takeIndex];
        takeIndex = (takeIndex + 1) % items.length;
        count--;
        notifyAll();
        return cmd;
    }
}
