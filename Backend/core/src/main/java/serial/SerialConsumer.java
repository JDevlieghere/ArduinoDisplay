package serial;

import core.Component;
import core.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialConsumer extends Component {

    private final Logger log = LoggerFactory.getLogger(SerialConsumer.class);

    private final SerialConnection serialConnection;

    public SerialConsumer(SerialConnection serialConnection, int rate) {
        this.serialConnection = serialConnection;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Send message
                Message message = (Message) getQueue().take();
                String text = message.getText();
                serialConnection.write(text);

                // Debugging
                log.debug("Message sent to COM: " + text);

                // Sleep
                Thread.sleep(message.getDelay());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
