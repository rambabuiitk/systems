import java.util.*;

/**
 * In Consistent Hashing I we introduced a relatively simple consistency hashing algorithm. This simple version has two defects：
 *
 * After adding a machine, the data comes from one of the machines. The read load of this machine is too large, which will affect the normal service.
 * When adding to 3 machines, the load of each server is not balanced, it is 1:1:2
 * In order to solve this problem, the concept of micro-shards was introduced, and a better algorithm is like this：
 *
 * Divide the 360 ° interval into smaller ones.From 0~359 to a range of 0 ~ n-1, the interval is connected end to end and connected into a circle.
 * When joining a new machine, randomly choose to sprinkle k points in the circle, representing the k micro-shards of the machine.
 * Each data also corresponds to a point on the circumference, which is calculated by a hash function.
 * Which machine belongs to which data is to be managed is determined by the machine to which the first micro-shard point that is clockwise
 * touched on the circle is corresponding to the point on the circumference of the data.
 * n and k are typically 2^64 and 1000 in a real NoSQL database.
 *
 * Implement these methods of introducing consistent hashing of micro-shard.
 *
 * create(int n, int k)
 * addMachine(int machine_id) // add a new machine, return a list of shard ids.
 * getMachineIdByHashCode(int hashcode) // return machine id
 */
public class ConsistentHashingII {

    private TreeMap<Integer, Integer> ring;
    private Set<Integer> shardIds;
    private int n, k;
    private Random random;

    public ConsistentHashingII(int n, int k) {
        this.n = n;
        this.k = k;
        this.ring = new TreeMap<>();
        this.shardIds = new HashSet<>();
        this.random = new Random();
    }

    /*
     * @param n: a positive integer
     * @param k: a positive integer
     * @return: a Solution object
     */
    public static ConsistentHashingII create(int n, int k) {
        return new ConsistentHashingII(n, k);
    }

    /*
     * @param machine_id: An integer
     * @return: a list of shard ids
     */
    public List<Integer> addMachine(int machine_id) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0, shardId = 0; i < k; i++) {
            shardId = random.nextInt(n);
            while (shardIds.contains(shardId)) {
                shardId = random.nextInt(n);
            }

            shardIds.add(shardId);
            ring.put(shardId, machine_id);
            res.add(shardId);
        }

        return res;
    }

    /*
     * @param hashcode: An integer
     * @return: A machine id
     */
    public int getMachineIdByHashCode(int hashcode) {
        if (ring.ceilingEntry(hashcode) == null) {
            return ring.firstEntry().getValue();
        }
        return ring.ceilingEntry(hashcode).getValue();
    }
}