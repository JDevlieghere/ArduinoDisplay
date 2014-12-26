package serial;

import common.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                Message message = (Message) blockingQueue.take();

                // Remove newlines and non ASCII characters
                String s = message.getText();
                String msg = s.replaceAll("\n", "").replaceAll("[^\\x00-\\x7F]", "").replaceAll("https?://\\S+\\s?", "");

                serialConnection.write(msg);

                // Debugging info
                log.debug("Message sent to COM: " + msg);

                // Sleep
                Thread.sleep(message.getDelay());
            }
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
    }

}
