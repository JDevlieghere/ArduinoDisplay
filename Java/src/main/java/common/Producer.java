package common;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.concurrent.BlockingQueue;

public abstract class Producer {

    private final BlockingQueue queue;
    private final TwitterStream twitterStream;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
        this.twitterStream = new TwitterStreamFactory().getInstance();
    }

    public BlockingQueue getQueue() {
        return this.queue;
    }

    public TwitterStream getTwitterStream() {
        return this.twitterStream;
    }
}
