/**
 * Use the set of < string, value > to build a tree structure, and please save the top 10 values of each node.
 */

import java.util.*;


//Definition of TrieNode:
class TrieNode {
    public NavigableMap<Character, TrieNode> children;
    public List<Integer> top10;

    public TrieNode() {
        children = new TreeMap<Character, TrieNode>();
        top10 = new ArrayList<Integer>();
    }
}

public class TrieService {

    private TrieNode root = null;

    public TrieService() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        // Return root of trie root, and
        // lintcode will print the tree struct.
        return root;
    }

    // @param word a string
    // @param frequency an integer
    public void insert(String word, int frequency) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
            }
            cur = cur.children.get(c);
            addFrequency(cur.top10, frequency);
        }
    }

    public void addFrequency(List<Integer> top10, int frequency) {
        top10.add(frequency);
        Collections.sort(top10, Collections.reverseOrder());
        if (top10.size() > 10) {
            top10.remove(top10.size() - 1);
        }
    }
}
