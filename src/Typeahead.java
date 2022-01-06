/**
 * Implement typeahead. Given a string and a dictionary, return all words that contains the string as a substring.
 * The dictionary will give at the initialize method and won't be changed. The method to find all words with given
 * substring would be called multiple times.
 */

import java.util.*;

public class Typeahead {

    private HashMap<String, List<String>> map = new HashMap<>();

    /*
     * @param dict: A dictionary of words dict
     */
    public Typeahead(Set<String> dict) {
        for (String str : dict) {
            int len = str.length();
            for (int i = 0; i < len; ++i) {
                for (int j = i + 1; j <= len; ++j) {
                    String tmp = str.substring(i, j);
                    if (!map.containsKey(tmp)) {
                        map.put(tmp, new ArrayList<String>());
                        map.get(tmp).add(str);
                    } else {
                        List<String> index = map.get(tmp);
                        if (!str.equals(index.get(index.size() - 1))) {
                            index.add(str);
                        }
                    }
                }
            }
        }
    }

    /*
     * @param str: a string
     * @return: a list of words
     */
    public List<String> search(String str) {
        if (!map.containsKey(str)) {
            return new ArrayList<String>();
        } else {
            return map.get(str);
        }
    }
}