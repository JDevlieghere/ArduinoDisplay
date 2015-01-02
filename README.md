Arduino Display
===============

Display anything on your four-line LCD display connected to an Arduino microcontroller. The backend is written in java and uses [RXTX](http://rxtx.qbang.org/wiki/index.php/Main_Page) to communicate with the Arduino over the serial port. 

## Modules

### Twitter

The twitter module is based on [Twitter4j](http://twitter4j.org/en/index.html) and fetches tweets from your timeline to be displayed. You can also fetch tweets containing a specific keyword. These keywords need to be specified as a comma-separated lists.

### Reddit

Fetch the latest post from your favorite subreddit. The `where` variable allows you to fetch items from any subreddit. It's the part that comes after `https://www.reddit.com/` in the URL. 

For example, to get the newest post from r/news, set `where` to `r/news/new`. To get the first post from the frontpage leave it empty. 

