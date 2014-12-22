import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StatusMain {

    public static void main(String[] args) throws TwitterException, IOException {
        final FilterQuery fq = new FilterQuery();
        final String keywords[] = {"Belgium"};
        fq.track(keywords);

        BlockingQueue<Status> queue = new LinkedBlockingQueue<Status>(1000);
        StatusProducer p = new StatusProducer(queue, fq);
        StatusConsumer c = new StatusConsumer(queue);
        new Thread(c).start();

    }

}
