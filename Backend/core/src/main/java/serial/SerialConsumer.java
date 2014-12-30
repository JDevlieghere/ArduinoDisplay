package serial;

import core.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class SerialConsumer implements Runnable {


    private final Logger log = LoggerFactory.getLogger(SerialConsumer.class);

    private final BlockingQueue blockingQueue;
    private final SerialConnection serialConnection;

    public SerialConsumer(BlockingQueue blockingQueue, SerialConnection serialConnection, int rate) {
        this.blockingQueue = blockingQueue;
        this.serialConnection = serialConnection;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Get status
                Message message = (Message) blockingQueue.take();

                String msg = message.getText();
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
