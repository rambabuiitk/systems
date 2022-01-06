/**
 * Implement a webpage Crawler to crawl webpages of http://www.wikipedia.org/. To simplify the question, let's use url instead of the the webpage content.
 *
 * Your crawler should:
 *
 * Call HtmlHelper.parseUrls(url) to get all urls from a webpage of given url.
 * Only crawl the webpage of wikipedia which simulated by LintCode.
 * Do not crawl the same webpage twice.
 * Start from the homepage of wikipedia: http://www.wikipedia.org/
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.net.*;

class HtmlHelper {
    public static List<String> parseUrls(String url) {
        return null;
    }
    // Get all urls from a webpage of given url.
}

public class WebCrawler {

    ExecutorService pool = Executors.newFixedThreadPool(3);
    AtomicLong numTasks = new AtomicLong(0);  // wait for all task to finish
    Lock lock = new ReentrantLock();  // to protect ans::List<String> and visited::Set<String>. 
    Set<String> visited = new HashSet<>();

    private class crawlTask implements Runnable {
        String url;
        public crawlTask (String u) {
            url = u;
        }

        @Override
        public void run () {
            try {
                for (String neighbor : HtmlHelper.parseUrls(url)) {
                    URL neighborURL = new URL(neighbor);
                    if (!neighborURL.getHost().endsWith("wikipedia.org")) continue;  // may throw exception
                    lock.lock();
                    if (!visited.contains(neighbor)) {  // found new URL to crawl
                        visited.add(neighbor);
                        pool.execute(new crawlTask(neighbor));
                        numTasks.incrementAndGet();
                    }
                    lock.unlock();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                numTasks.decrementAndGet();
            }
        }
    }

    /**
     * @param url: a url of root page
     * @return: all urls
     */
    public List<String> crawler (String url) {
        visited.add(url);
        pool.execute(new crawlTask(url));
        numTasks.incrementAndGet();
        try {
            while (numTasks.get() != 0) Thread.sleep(30);;  // wait until no more tasks
        } catch (Exception e) { e.printStackTrace(); }
        pool.shutdown();  // otherwise program won't stop
        return new ArrayList<>(visited);
    }
}