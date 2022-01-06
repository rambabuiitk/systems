import java.util.*;

/**
 * Cassandra is a NoSQL database (a.k.a key-value storage). One individual data entry in cassandra constructed by 3 parts:
 *
 * row_key. (a.k.a hash_key, partition key or sharding_key.)
 * column_key.
 * value
 * row_key is used to hash and can not support range query. Let's simplify this to a string.
 * column_key is sorted and support range query. Let's simplify this to integer.
 * value is a string. You can serialize any data into a string and store it in value.
 *
 * Implement the following methods:
 *
 * insert(row_key, column_key, value)
 * query(row_key, column_start, column_end) return a list of entries
 */

// Definition of Column:
class Column {
     public int key;
     public String value;
     public Column(int key, String value) {
         this.key = key;
         this.value = value;
    }
 }



public class MiniCassandra {

    private Map<String, TreeMap<Integer, String>> hash;

    public MiniCassandra() {
        hash = new HashMap<>();
    }

    /*
     * @param raw_key: a string
     * @param column_key: An integer
     * @param column_value: a string
     * @return: nothing
     */
    public void insert(String row_key, int column_key, String value) {
        if (!hash.containsKey(row_key)) {
            hash.put(row_key, new TreeMap<Integer, String>());
        }
        hash.get(row_key).put(column_key, value);
    }

    /*
     * @param row_key: a string
     * @param column_start: An integer
     * @param column_end: An integer
     * @return: a list of Columns
     */
    public List<Column> query(String row_key, int column_start, int column_end) {
        List<Column> rst = new ArrayList<>();
        if (!hash.containsKey(row_key)) {
            return rst;
        }

        for (Map.Entry<Integer, String> entry :
                hash.get(row_key).subMap(column_start, true, column_end, true).entrySet()) {
            rst.add(new Column(entry.getKey(), entry.getValue()));
        }
        return rst;
    }
}