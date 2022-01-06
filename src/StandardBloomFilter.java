import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Implement a standard bloom filter. Support the following method:
 *
 * StandardBloomFilter(k) The constructor and you need to create k hash functions.
 * add(string) Add a string into bloom filter.
 * contains(string) Check a string whether exists in bloom filter.
 */
public class StandardBloomFilter {

    class HashFunction {
        public int cap, seed;

        public HashFunction(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value) {
            int ret = 0;
            int n = value.length();
            for (int i = 0; i < n; ++i) {
                ret += seed * ret + value.charAt(i);
                ret %= cap;
            }
            return ret;
        }
    }

    public BitSet bits;
    public int k;
    public List<HashFunction> hashFunc;
    /*
     * @param k: An integer
     */public StandardBloomFilter(int k) {
        this.k = k;
        hashFunc = new ArrayList<>();
        for (int i = 0; i < k; ++i)
            hashFunc.add(new HashFunction(100000 + i, 2 * i + 3));
        bits = new BitSet(100000 + k);
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void add(String word) {
        for (int i = 0; i < k; ++i) {
            int position = hashFunc.get(i).hash(word);
            bits.set(position);
        }
    }

    /*
     * @param word: A string
     * @return: True if contains word
     */
    public boolean contains(String word) {
        for (int i = 0; i < k; ++i) {
            int position = hashFunc.get(i).hash(word);
            if (!bits.get(position))
                return false;
        }
        return true;
    }
}