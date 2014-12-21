import twitter4j.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StatusProducer {

    private final BlockingQueue blockingQueue;
    private final TwitterStream twitterStream;

    public StatusProducer(BlockingQueue blockingQueue, FilterQuery filterQuery){
        this.blockingQueue = blockingQueue;
        this.twitterStream = new TwitterStreamFactory().getInstance();
        this.twitterStream.addListener(listener);
        this.twitterStream.filter(filterQuery);
    }

    private StatusListener listener = new StatusListener(){

        public void onStatus(Status status) {
            blockingQueue.offer(status);

        }
        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {

        }

        public void onException(Exception ex) {
            ex.printStackTrace();
        }

    };


}
