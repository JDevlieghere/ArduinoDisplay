import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;

import java.util.concurrent.BlockingQueue;

public class StatusConsumer implements Runnable {

    private final BlockingQueue blockingQueue;
    private final long delay;
    private final Logger logger = LoggerFactory.getLogger(StatusConsumer.class);


    public StatusConsumer(BlockingQueue blockingQueue, long delay) {
        this.blockingQueue = blockingQueue;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Status s = (Status) blockingQueue.take();
                logger.info("@" + s.getUser().getScreenName() + " " + s.getText());
                logger.info("Remaining" + blockingQueue.size());
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
