package main;

import serial.SerialConnection;
import twitter.TwitterConsumer;
import twitter.TwitterProducer;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws TwitterException, IOException {

        // Create a queue for storing statusses
        BlockingQueue<Status> queue = new LinkedBlockingQueue<Status>(1000);

        // Create the producer
        TwitterProducer p = new TwitterProducer(queue);
        p.start();

        // Connect to Serial Port
        SerialConnection sc = new SerialConnection();
        sc.initialize("COM8");

        // Create consumer
        TwitterConsumer c = new TwitterConsumer(queue, sc.getSerialPort(), 5);
        new Thread(c).start();

    }

}
