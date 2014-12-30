package core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serial.SerialConnection;
import serial.SerialConsumer;

public class Server
{
    public static final String ENABLE = "_ENABLE";
    public static final String DISABLE = "_DISABLE";
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private final BlockingQueue<Message> queue;
    private final SerialConnection serialConnection;

    public Server(String com)
    {
        this.queue = new LinkedBlockingQueue(1000);
        this.serialConnection = new SerialConnection();
        this.serialConnection.initialize(com);
    }

    public void start(Producer[] producers)
    {
        try
        {
            this.serialConnection.write("_ENABLE");
            for (Producer producer : producers)
            {
                producer.setQueue(this.queue);
                new Thread(producer).start();
            }
            SerialConsumer c = new SerialConsumer(this.queue, this.serialConnection, 5);
            new Thread(c).start();
        }
        catch (Exception e)
        {
            log.error(e.toString());
            System.exit(1);
        }
    }

    public void stop()
    {
        this.serialConnection.write("_DISABLE");
        this.serialConnection.close();
    }
}
