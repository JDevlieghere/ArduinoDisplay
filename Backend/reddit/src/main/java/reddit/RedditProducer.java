package reddit;

import core.Component;
import core.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Jonas
 * @version 1.0
 * @since 2/01/2015
 */

public class RedditProducer extends Component {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(RedditProducer.class);

    public static final RedditFilter REDDIT_FILTER = new RedditFilter();

    public static final long MESSAGE_DELAY  = 10000;
    public static final long FETCH_DELAY    = 30000;

    public static final String URL_PRE  = "https://www.reddit.com/";
    public static final String URL_POST = ".json?limit=1";

    public static final String KEY_DATA         = "data";
    public static final String KEY_CHILDREN     = "children";
    public static final String KEY_ID           = "id";
    public static final String KEY_TITLE        = "title";
    public static final String KEY_SUBREDDIT    = "subreddit";
    
    private String lastId;
    private int backoff;
    private String where;

    public RedditProducer(String where){
        this.where = where;
        this.backoff = 1;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    private JSONObject getFirstItem(String where) throws IOException {
        String URL = URL_PRE + where + URL_POST;
        JSONObject root = readJsonFromUrl(URL);
        JSONArray children = root.getJSONObject(KEY_DATA).getJSONArray(KEY_CHILDREN);
        return children.getJSONObject(0).getJSONObject(KEY_DATA);
    }

    @Override
    public void run() {
        log.info(this.toString() + " started.");
        while (!isStopped()) {
            try {
                try {
                    JSONObject item = getFirstItem(where);
                    // Add item to queue if not equal to the last
                    if(!item.getString(KEY_ID).equals(this.lastId)){
                        this.lastId = item.getString(KEY_ID);
                        String text = "r/" + item.getString(KEY_SUBREDDIT) + " " + item.getString(KEY_TITLE);
                        getQueue().offer(new Message(text, REDDIT_FILTER, MESSAGE_DELAY));
                    }
                    // Reset backoff
                    backoff = 1;
                } catch (IOException e) {
                    log.error(e.toString());
                    // Exponential backoff
                    backoff *= 2;
                }
                Thread.sleep(backoff * FETCH_DELAY);
            } catch (InterruptedException ignored) {
                // Do nothing
            }
        }
    }
}
