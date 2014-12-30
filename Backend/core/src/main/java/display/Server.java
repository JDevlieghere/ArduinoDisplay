package display;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serial.SerialConnection;
import serial.SerialConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private final BlockingQueue<Message> queue;
    private final SerialConnection serialConnection;

    public Server(String com){
        this.queue = new LinkedBlockingQueue<>(1000);
        this.serialConnection = new SerialConnection();
        this.serialConnection.initialize(com);
    }

    public void start(Producer[] producers){
        try{

            // Create producers
            for(Producer producer : producers){
                producer.setQueue(this.queue);
                new Thread(producer).start();
            }

            // Create consumer
            SerialConsumer c = new SerialConsumer(queue, serialConnection, 5);
            new Thread(c).start();

        } catch (Exception e) {
            log.error(e.toString());
            System.exit(1);
        }
    }
}
