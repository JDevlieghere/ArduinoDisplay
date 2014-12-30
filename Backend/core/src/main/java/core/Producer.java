package core;

import java.util.concurrent.BlockingQueue;

public abstract class Producer
        implements Runnable
{
    private BlockingQueue queue;

    public void setQueue(BlockingQueue queue)
    {
        this.queue = queue;
    }

    public BlockingQueue getQueue()
    {
        return this.queue;
    }
}
