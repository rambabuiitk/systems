import java.util.*;

/**
 * A general database method for performing a horizontal shard is to take the id against the total number of database
 * servers n and then to find out which machine it is on. The downside of this approach is that as the data continues to
 * increase, we need to increase the database server. When n is changed to n+1, almost all of the data has to be moved,
 * which is not consistent. In order to reduce the defects caused by this naive's hash method (%n), a new hash algorithm emerges:
 * Consistent Hashing. There are many ways to implement this algorithm. Here we implement a simple Consistent Hashing.
 *
 * Take id to 360. If there are 3 machines at the beginning, then let 3 machines be responsible for the three parts of
 * 0~119, 120~239, 240~359. Then, how much is the model, check which zone you are in, and which machine to go to.
 * When the machine changes from n to n+1, we find the largest one from the n intervals, then divide it into two and give
 * half to the n+1th machine.
 * For example, when changing from 3 to 4, we find the third interval 0~119 is the current largest interval, then we divide
 * 0~119 into 0~59 and 60~119. 0~59 is still given to the first machine, 60~119 to the fourth machine.
 * Then change from 4 to 5, we find the largest interval is the third interval 120~239, after splitting into two, it becomes
 * 120~179, 180~239.
 * Suppose all the data is on one machine at the beginning. When adding to the nth machine, what is the distribution of the
 * interval and the corresponding machine number?
 */

public class ConsistentHashing {
    /*
     * @param n: a positive integer
     * @return: n x 3 matrix
     */
    public List<List<Integer>> consistentHashing(int n) {
        List<List<Integer>> result = new ArrayList<>();

        //init
        List<Integer> init = new ArrayList<>();
        init.add(0);
        init.add(359);
        init.add(1);
        result.add(init);
        if (n == 1) {
            return result;
        }

        for (int i = 2; i <= n; i++) {
            //find the max interval
            int max = 0;
            int maxPos = 0;
            for (int j = 0; j < result.size(); j++) {
                List<Integer> current = result.get(j);
                int temp = current.get(1) - current.get(0);
                if (temp > max) {
                    max = temp;
                    maxPos = j;
                }
            }

            //split the max interval
            List<Integer> maxInterval = result.get(maxPos);
            int maxEnd = maxInterval.get(1);
            int midVal = (maxInterval.get(0) + maxEnd)/2;
            maxInterval.set(1, midVal);

            List<Integer> newItem = new ArrayList<>();
            newItem.add(midVal+1);
            newItem.add(maxEnd);
            newItem.add(i);
            result.add(newItem);
        }

        return result;
    }
}