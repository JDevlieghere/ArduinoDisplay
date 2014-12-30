package core;

public class Message
{
    public static final long DEFAULT_DELAY = 5000L;
    public static final long SECOND = 1000L;
    private String text;
    private long delay;
    private Filter filter;

    public Message(String text, Filter filter)
    {
        this(text, filter, 5000L);
    }

    public Message(String text, Filter filter, long delay)
    {
        this.text = text;
        this.delay = delay;
        this.filter = filter;
    }

    public String getText()
    {
        if (this.filter == null) {
            return this.text;
        }
        return this.filter.apply(this.text);
    }

    public long getDelay()
    {
        return this.delay;
    }
}
