package reddit;

import core.Filter;

public class TwitterFilter
        implements Filter
{
    public String apply(String string)
    {
        return string.replaceAll("\n", " ").replaceAll("[^\\x00-\\x7F]", "").replaceAll("https?://\\S+\\s?", "");
    }
}
