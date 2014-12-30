package twitter;

import core.Component;
import core.Filter;
import core.Message;
import org.slf4j.Logger;
import twitter4j.*;


public class StatusProducer extends Component {

    public static final Filter TWITTER_FILTER = new TwitterFilter();

    private final Logger log = org.slf4j.LoggerFactory.getLogger(UserProducer.class);
    private final TwitterStream twitterStream;
    private final FilterQuery query;

    public StatusProducer(FilterQuery query) {
        this.twitterStream = new TwitterStreamFactory().getInstance();
        this.query = query;
    }

    StatusListener listener = new StatusListener() {
        @Override
        public void onException(Exception e) {
            log.error(e.toString());
        }

        @Override
        public void onStatus(Status status) {
            Message message = new Message("@" + status.getUser().getScreenName() + " " + status.getText(), TWITTER_FILTER);
            getQueue().offer(message);
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        @Override
        public void onTrackLimitationNotice(int i) {

        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {

        }
    };

        @Override
    public void run() {
        this.twitterStream.addListener(listener);
        this.twitterStream.filter(query);
    }
}
