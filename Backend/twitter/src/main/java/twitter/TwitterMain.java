package twitter;

import display.Producer;
import display.Server;

public class TwitterMain {

    public static void main(String[] args) {
        // Create Server
        Server server = new Server("COM8");

        // Create Producers
        Producer[] producers = new Producer[1];
        producers[0] = new TwitterProducer();

        // Start Server
        server.start(producers);
    }
}
