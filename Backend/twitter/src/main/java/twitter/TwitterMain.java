package twitter;

import core.Server;

public class TwitterMain {

    public static void main(String[] args) {
        Server server = new Server();
        server.init("COM8");
        server.addComponent(new TwitterProducer());
        server.start();
    }
}
