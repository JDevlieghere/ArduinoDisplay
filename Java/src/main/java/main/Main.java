package main;

import serial.SerialConnection;
import serial.SerialConsumer;
import twitter.QueryProducer;
import twitter.UserProducer;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws TwitterException, IOException {

        // Create a queue for storing statusses
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);

        // Create the timeline producer
        UserProducer p = new UserProducer(queue);
        p.start();

        // Add another producer
        if(args.length > 0) {
            QueryProducer q = new QueryProducer(queue);
            FilterQuery fq = new FilterQuery();
            fq.track(args);
            q.start(fq);
        }

        // Connect to Serial Port
        SerialConnection sc = new SerialConnection();
        sc.initialize("COM8");

        // Create consumer
        SerialConsumer c = new SerialConsumer(queue, sc, 5);
        new Thread(c).start();

    }

}
