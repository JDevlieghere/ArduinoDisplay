package reddit;

import core.Filter;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;

public class RedditFilter implements Filter
{
    private final Logger log = org.slf4j.LoggerFactory.getLogger(RedditFilter.class);

    public String apply(String string)
    {
        try {
            String s1 = Normalizer.normalize(string, Normalizer.Form.NFKD);
            String regex = "[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+";
            return new String(s1.replaceAll(regex, "").getBytes("ascii"), "ascii");
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
            return string;
        }
    }
}
