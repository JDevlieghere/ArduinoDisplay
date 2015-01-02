package core;

import java.util.concurrent.BlockingQueue;

public abstract class Component implements Runnable
{
    private BlockingQueue queue;
    private boolean stopped = false;

    public void setQueue(BlockingQueue queue)
    {
        this.queue = queue;
    }

    public BlockingQueue getQueue(){
        return this.queue;
    }

    public boolean isStopped(){
        return this.stopped;
    }

    public void stop(){
        this.stopped = true;
    }

    public void init(){
        this.stopped = false;
    }

    @Override
    public String toString() {
        return "Component " + this.getClass().getSimpleName();
    }


}
