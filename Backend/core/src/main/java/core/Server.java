package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serial.SerialConnection;
import serial.SerialConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server
{
    public static final String ENABLE = "_ENABLE";
    public static final String DISABLE = "_DISABLE";

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private final BlockingQueue<Message> queue;
    private final SerialConnection serialConnection;
    private final List<Thread> components;

    public Server()
    {
        this.queue = new LinkedBlockingQueue();
        this.components = new ArrayList<>();
        this.serialConnection = new SerialConnection();
    }

    public void init(String com){
        // Initialize COM
        this.serialConnection.initialize(com);

        // Add Consumer Component
        SerialConsumer serialConsumer = new SerialConsumer(this.serialConnection, 5);
        this.addComponent(serialConsumer);
    }

    public void start(){
        // Start All Threads
        components.forEach(java.lang.Thread::start);
    }

    public void addComponent(Component component){
        component.setQueue(this.queue);
        components.add(new Thread(component));
    }

    public void stop(){
        // Stop all components
        components.forEach(java.lang.Thread::interrupt);
        // Disconnect
        this.serialConnection.write(DISABLE);
        this.serialConnection.close();
    }
}
