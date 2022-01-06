import java.util.*;

/**
 * In the Master-Slave structure mode, the slave will send a ping request to the master every k seconds to indicate that it is still running.
 * If the master does not receive any ping request from the slave within 2 * k seconds, the master will send a warning to the administrator
 * (such as sending an email).
 *
 * Now let us simulate the master side, you need to implement the following three functions:
 *
 * initialize(slaves_ip_list, k), slaves_ip_list is the ip address list of all slaves, and k is a fixed value.
 * ping(timestamp, slave_ip), this method is executed when the master side receives a ping request from the slave side, timestamp refers to
 * the current timestamp, and slave_ip represents the ip address of the slave side currently sending the request
 * getDiedSlaves(timestamp), this method will be executed periodically (the time interval between two executions is uncertain), timestamp
 * represents the current timestamp, this method needs to return a list of all the ip addresses of the slave that is no longer running,
 * If it is not found, an empty set is returned.
 * You can assume that the timestamp is 0 when the master starts running, and all methods will run according to the globally increasing timestamp.mestamp.
 */

public class HeartBeat {

    public Map<String, Integer> slaves_ip_list;
    public int k;

    public HeartBeat() {
        slaves_ip_list = new HashMap<>();
    }

    /*
     * @param slaves_ip_list: a list of slaves'ip addresses
     * @param k: An integer
     * @return: nothing
     */
    public void initialize(List<String> slaves_ip_list, int k) {
        this.k = k;
        for (String slaveIp : slaves_ip_list) {
            this.slaves_ip_list.put(slaveIp, 0);
        }
    }

    /*
     * @param timestamp: current timestamp in seconds
     * @param slave_ip: the ip address of the slave server
     * @return: nothing
     */
    public void ping(int timestamp, String slave_ip) {
        if (!slaves_ip_list.containsKey(slave_ip)) {
            return;
        }

        slaves_ip_list.put(slave_ip, timestamp);
    }

    /*
     * @param timestamp: current timestamp in seconds
     * @return: a list of slaves'ip addresses that died
     */
    public List<String> getDiedSlaves(int timestamp) {
        List<String> results = new ArrayList<>();

        Iterator iter = slaves_ip_list.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            String ip = (String) entry.getKey();
            int time = (Integer) entry.getValue();
            if (time <= timestamp - 2 * k)
                results.add(ip);
        }
        return results;
    }
}
