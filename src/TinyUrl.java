/**
 * Given a long url, make it shorter.
 *
 * You should implement two methods:
 *
 * longToShort(url) Convert a long url to a short url which starts with http://tiny.url/.
 * shortToLong(url) Convert a short url to a long url.
 * You can design any shorten algorithm, the judge only cares about two things:
 *
 * The short key's length should equal to 6 (without domain and slash). And the acceptable characters are [a-zA-Z0-9]. For example: abcD9E
 * No two long urls mapping to the same short url and no two short urls mapping to the same long url.
 */

import java.util.*;

public class TinyUrl {

    public static int GLOBAL_ID = 0;
    private HashMap<Integer, String> id2url = new HashMap<Integer, String>();
    private HashMap<String, Integer> url2id = new HashMap<String, Integer>();
    private String getShortKey(String url) {
        return url.substring("http://tiny.url/".length());
    }

    private int toBase62(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        return c - 'A' + 36;
    }

    private int shortKeytoID(String short_key) {
        int id = 0;
        for (int i = 0; i < short_key.length(); ++i) {
            id = id * 62 + toBase62(short_key.charAt(i));
        }
        return id;
    }

    private String idToShortKey(int id) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String short_url = "";
        while (id > 0) {
            short_url = chars.charAt(id % 62) + short_url;
            id = id / 62;
        }
        while (short_url.length() < 6) {
            short_url = "0" + short_url;
        }
        return short_url;
    }

    /*
     * @param url: a long url
     * @return: a short url starts with http://tiny.url/
     */
    public String longToShort(String url) {
        if (url2id.containsKey(url)) {
            return "http://tiny.url/" + idToShortKey(url2id.get(url));
        }
        GLOBAL_ID++;
        url2id.put(url, GLOBAL_ID);
        id2url.put(GLOBAL_ID, url);
        return "http://tiny.url/" + idToShortKey(GLOBAL_ID);
    }

    /*
     * @param url: a short url starts with http://tiny.url/
     * @return: a long url
     */
    public String shortToLong(String url) {
        String short_key = getShortKey(url);
        int id = shortKeytoID(short_key);
        return id2url.get(id);
    }
}