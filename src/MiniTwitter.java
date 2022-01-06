/**
 * Implement a simple twitter. Support the following method:
 *
 * postTweet(user_id, tweet_text). Post a tweet.
 * getTimeline(user_id). Get the given user's most recently 10 tweets posted by himself, order by timestamp from most recent to least recent.
 * getNewsFeed(user_id). Get the given user's most recently 10 tweets in his news feed (posted by his friends and himself).
 * Order by timestamp from most recent to least recent.
 * follow(from_user_id, to_user_id). from_user_id followed to_user_id.
 * unfollow(from_user_id, to_user_id). from_user_id unfollowed to to_user_id.
 */

import java.util.*;


// Definition of Tweet:
class Tweet {
     public int id;
     public int user_id;
     public String text;
     public static Tweet create(int user_id, String tweet_text) {
         // This will create a new tweet object,
         // and auto fill id
         return null;
     }
 }


public class MiniTwitter {
    class Node implements Comparable<Node> {
        public int order;
        public Tweet tweet;
        public Node(int o, Tweet t) {
            this.order = o;
            this.tweet = t;
        }

        public int compareTo(Node a) {
            return a.order - this.order;
        }
    }
    private Map<Integer, Set<Integer>> friends;
    private Map<Integer, List<Node>> users_tweets;
    private int order;
    public MiniTwitter() {
        this.friends = new HashMap<Integer, Set<Integer>>();
        this.users_tweets = new HashMap<Integer, List<Node>>();
        this.order = 0;
    }

    /*
     * @param user_id: An integer
     * @param tweet_text: a string
     * @return: a tweet
     */
    public Tweet postTweet(int user_id, String tweet_text) {
        Tweet tweet = Tweet.create(user_id, tweet_text);
        if (!users_tweets.containsKey(user_id))
            users_tweets.put(user_id, new ArrayList<Node>());
        order += 1;
        users_tweets.get(user_id).add(new Node(order, tweet));
        return tweet;
    }

    /*
     * @param user_id: An integer
     * @return: a list of 10 new feeds recently and sort by timeline
     */
    public List<Tweet> getNewsFeed(int user_id) {
        List<Node> tmp = new ArrayList<Node>();
        if (users_tweets.containsKey(user_id))
            tmp.addAll(getLastTen(users_tweets.get(user_id)));

        if (friends.containsKey(user_id)) {
            for (Integer user : friends.get(user_id)) {
                if (users_tweets.containsKey(user))
                    tmp.addAll(getLastTen(users_tweets.get(user)));
            }
        }

        Collections.sort(tmp);
        List<Tweet> rt = new ArrayList<Tweet>();
        tmp = getFirstTen(tmp);
        for (Node node : tmp) {
            rt.add(node.tweet);
        }
        return rt;
    }

    /*
     * @param user_id: An integer
     * @return: a list of 10 new posts recently and sort by timeline
     */
    public List<Tweet> getTimeline(int user_id) {
        List<Node> tmp = new ArrayList<Node>();
        if (users_tweets.containsKey(user_id))
            tmp.addAll(getLastTen(users_tweets.get(user_id)));

        Collections.sort(tmp);
        List<Tweet> rt = new ArrayList<Tweet>();
        tmp = getFirstTen(tmp);
        for (Node node : tmp)
            rt.add(node.tweet);
        return rt;
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void follow(int from_user_id, int to_user_id) {
        if (!friends.containsKey(from_user_id)) {
            friends.put(from_user_id, new HashSet<Integer>());
        }

        friends.get(from_user_id).add(to_user_id);
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void unfollow(int from_user_id, int to_user_id) {
        if (friends.containsKey(from_user_id)) {
            friends.get(from_user_id).remove(to_user_id);
        }
    }

    private List<Node> getLastTen(List<Node> tmp) {
        return tmp.subList(Math.max(tmp.size() - 10, 0), tmp.size());
    }

    private List<Node> getFirstTen(List<Node> tmp) {
        return tmp.subList(0, Math.min(10, tmp.size()));
    }
}