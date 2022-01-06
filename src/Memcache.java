import java.util.HashMap;
import java.util.Map;

/**
 * mplement a memcache which support the following features:
 *
 * get(curtTime, key) Get the key's value, return 2147483647 if key does not exist.
 * set(curtTime, key, value, ttl) Set the key-value pair in memcache with a time to live (ttl). The key will be valid from
 * curtTime to curtTime + ttl - 1 and it will be expired after ttl seconds. if ttl is 0, the key lives forever until out of memory.
 * delete(curtTime, key) Delete the key.
 * incr(curtTime, key, delta) Increase the key's value by delta return the new value. Return 2147483647 if key does not exist.
 * decr(curtTime, key, delta) Decrease the key's value by delta return the new value. Return 2147483647 if key does not exist.
 * It's guaranteed that the input is given with increasing curtTime.
 */
public class Memcache {

    private class Node {
        public int value;
        public int expired;
        public Node(int value, int expired) {
            this.value = value;
            this.expired = expired;
        }
    }

    Map<Integer, Node> client;

    public Memcache() {
        client = new HashMap<>();
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @return: An integer
     */
    public int get(int curtTime, int key) {
        if (client.containsKey(key)) {
            Node n = client.get(key);
            if (n.expired >= curtTime || n.expired == -1) {
                return n.value;
            }
        }
        return Integer.MAX_VALUE;
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param value: An integer
     * @param ttl: An integer
     * @return: nothing
     */
    public void set(int curtTime, int key, int value, int ttl) {
        client.put(key, new Node(value, ttl == 0 ? -1 : curtTime + ttl - 1));
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @return: nothing
     */
    public void delete(int curtTime, int key) {
        if (client.containsKey(key)) {
            client.remove(key);
        }
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param delta: An integer
     * @return: An integer
     */
    public int incr(int curtTime, int key, int delta) {
        if (get(curtTime, key) == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        client.get(key).value += delta;
        return client.get(key).value;
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param delta: An integer
     * @return: An integer
     */
    public int decr(int curtTime, int key, int delta) {
        if (get(curtTime, key) == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        client.get(key).value -= delta;
        return client.get(key).value;
    }
}
