/**
 * Implement a rate limiter, provide one method: is_ratelimited(timestamp, event, rate, increment).
 *
 * timestamp: The current timestamp, which is an integer and in second unit.
 * event: The string to distinct different event. for example, "login" or "signup".
 * rate: The rate of the limit. 1/s (1 time per second), 2/m (2 times per minute), 10/h (10 times per hour), 100/d (100 times per day).
 * The format is [integer]/[s/m/h/d].
 * increment: Whether we should increase the counter. (or take this call as a hit of the given event)、
 * The method should return true or false to indicate the event is limited or not.
 * Note: Login failure is not recorded in login times.
 */

import java.util.*;

public class RateLimiter {
    private Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
    private static Map<String, Integer> durationMap = new HashMap<String, Integer>();

    static {
        durationMap.put("s", 1);
        durationMap.put("m", 60);
        durationMap.put("h", 3600);
        durationMap.put("d", 86400);
    }

    /*
     * @param timestamp: the current timestamp
     * @param event: the string to distinct different event
     * @param rate: the format is [integer]/[s/m/h/d]
     * @param increment: whether we should increase the counter
     * @return: true or false to indicate the event is limited or not
     */
    public boolean isRatelimited(int timestamp, String event, String rate, boolean increment) {
        int index = rate.indexOf("/");
        int limit = Integer.parseInt(rate.substring(0, index));
        int duration = durationMap.get(rate.substring(index + 1));
        int start = timestamp - duration + 1;
        if (!map.containsKey(event)) {
            map.put(event, new ArrayList<Integer>());
        }
        int count = getCount(map.get(event), start);
        if (increment && count < limit) {
            map.get(event).add(timestamp);
        }
        return count >= limit;
    }

    private int getCount(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (list.get(mid) >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return list.size() - left;
    }
}