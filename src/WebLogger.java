/**
 * Implement a web logger, which provide two methods:
 *
 * hit(timestamp), record a hit at given timestamp.
 * get_hit_count_in_last_5_minutes(timestamp), get hit count in last 5 minutes.
 * the two methods will be called with non-descending timestamp (in sec).
 */

import java.util.*;

public class WebLogger {

    private LinkedList<Integer> timestamps;

    public WebLogger() {
        timestamps = new LinkedList<Integer>();
    }

    /*
     * @param timestamp: An integer
     * @return: nothing
     */
    public void hit(int timestamp) {
        timestamps.add(timestamp);
    }

    /*
     * @param timestamp: An integer
     * @return: An integer
     */
    public int get_hit_count_in_last_5_minutes(int timestamp) {
        while (!timestamps.isEmpty() && timestamps.getFirst()  + 300 <= timestamp)
            timestamps.removeFirst();
        return timestamps.size();
    }
}