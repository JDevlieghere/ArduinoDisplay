package common;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serial.SerialConnection;
import serial.SerialConsumer;
import twitter.UserProducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final String DEFAULT_COM = "COM1";

    public static void main(String[] args) throws Exception {

        try{
            // Create options
            Options options = new Options();
            options.addOption("c", true, "The COM port");

            // Parse arguments
            CommandLineParser parser = new PosixParser();
            CommandLine cmd = parser.parse(options, args);
            String com = cmd.getOptionValue("c");

            // Create a queue for storing statusses
            BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>(1000);

            // Create the timeline producer
            UserProducer p = new UserProducer(queue);
            p.start();

            // Connect to Serial Port
            SerialConnection sc = new SerialConnection();
            if(com != null){
                sc.initialize(com);
            }else{
                sc.initialize(DEFAULT_COM);
            }

            // Create consumer
            SerialConsumer c = new SerialConsumer(queue, sc, 5);
            new Thread(c).start();

            // Enable display
            sc.write("_ENABLE");

        } catch (Exception e) {
            log.error(e.toString());
            System.exit(1);
        }

    }

}
