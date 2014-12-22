package twitter;

import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class TwitterConsumer implements Runnable {

    public static final long SECOND = 1000;

    private final Logger log = LoggerFactory.getLogger(TwitterConsumer.class);

    private final BlockingQueue blockingQueue;
    private final int rate;
    private final SerialPort serialPort;

    public TwitterConsumer(BlockingQueue blockingQueue, SerialPort serialPort, int rate) {
        this.blockingQueue = blockingQueue;
        this.rate = rate;
        this.serialPort = serialPort;
    }

    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(this.serialPort.getOutputStream());
            while (true) {
                // Get status
                Status s = (Status) blockingQueue.take();
                String status = "@" + s.getUser().getScreenName() + " " + s.getText();

                // Remove line breaks
                status = status.replace("\n", "");

                // Write to serial port
                pw.println(status);
                pw.flush();

                // Sleep
                Thread.sleep(SECOND*rate);
            }
        } catch (InterruptedException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

}
