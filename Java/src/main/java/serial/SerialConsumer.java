package serial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;

import java.util.concurrent.BlockingQueue;

public class SerialConsumer implements Runnable {

    public static final long SECOND = 1000;

    private final Logger log = LoggerFactory.getLogger(SerialConsumer.class);

    private final BlockingQueue blockingQueue;
    private final int rate;
    private final SerialConnection serialConnection;

    public SerialConsumer(BlockingQueue blockingQueue, SerialConnection serialConnection, int rate) {
        this.blockingQueue = blockingQueue;
        this.rate = rate;
        this.serialConnection = serialConnection;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Get status
                Status s = (Status) blockingQueue.take();
                String status = "@" + s.getUser().getScreenName() + " " + s.getText();

                // Remove line breaks
                status = status.replace("\n", "");

                serialConnection.write(status);

                // Debugging info
                log.debug("Message sent to COM: " + status);
                log.debug("Messages remaning in queue: " + blockingQueue.size());

                // Sleep
                Thread.sleep(SECOND*rate);
            }
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
    }

}
