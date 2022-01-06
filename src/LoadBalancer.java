import java.util.*;


/**
 * mplement a load balancer for web servers. It provide the following functionality:
 *
 * Add a new server to the cluster => add(server_id).
 * Remove a bad server from the cluster => remove(server_id).
 * Pick a server in the cluster randomly with equal probability => pick().
 * At beginning, the cluster is empty. When pick() is called you need to randomly return a server_id in the cluster.
 */
public class LoadBalancer {
    int n = 0;
    Map<Integer, Integer> pos = new HashMap<>();
    List<Integer> array = new ArrayList<>();
    Random rand = new Random();

    public LoadBalancer() {
        // do intialization if necessary
    }

    /*
     * @param server_id: add a new server to the cluster
     * @return: nothing
     */
    public void add(int server_id) {
        if (!pos.containsKey(server_id)) {
            array.add(server_id);
            pos.put(server_id, n);
            n++;
        }
    }

    /*
     * @param server_id: server_id remove a bad server from the cluster
     * @return: nothing
     */
    public void remove(int server_id) {
        if (pos.containsKey(server_id)) {
            int lastItem = array.get(n - 1);
            int removeIdx = pos.get(server_id);

            pos.put(lastItem, removeIdx);
            array.set(removeIdx, lastItem);

            pos.remove(server_id);
            array.remove(n - 1);
            n--;
        }
    }

    /*
     * @return: pick a server in the cluster randomly with equal probability
     */
    public int pick() {
        return array.get(rand.nextInt(n));
    }
}