package common;



public class Message {

    public static final long DEFAULT_DELAY = 5000;
    public static final long SECOND = 1000;

    private String text;
    private long delay;


    public Message(String text){
        this(text, DEFAULT_DELAY);
    }

    public Message(String text, long delay){
        this.text = text;
        this.delay = delay;
    }

    public String getText() {
        return text;
    }

    public long getDelay() {
        return delay;
    }
}
