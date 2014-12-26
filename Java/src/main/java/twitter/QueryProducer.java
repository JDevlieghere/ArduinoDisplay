package twitter;

import common.Message;
import common.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import java.util.concurrent.BlockingQueue;

public class QueryProducer extends Producer {

    private final Logger log = LoggerFactory.getLogger(QueryProducer.class);

    public QueryProducer(BlockingQueue queue) {
        super(queue);
    }

    public void start(FilterQuery filterQuery){
        this.getTwitterStream().addListener(listener);
        this.getTwitterStream().filter(filterQuery);
    }

    private StatusListener listener = new StatusListener(){

        public void onStatus(Status status) {
            getQueue().offer(new Message("@" + status.getUser().getScreenName() + " " + status.getText()));
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {
            log.warn(stallWarning.toString());
            log.info("Queue size: " + getQueue().size());
        }

        public void onException(Exception ex) {
            ex.printStackTrace();
        }

    };

}
