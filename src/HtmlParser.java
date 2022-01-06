import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Parse a html page, extract the Urls in it.
public class HtmlParser {

    private static final String HTML_HREF_TAG_PATTERN = "\\s*(?i)href\\s*=\\s*\"([^\"'>\\s]*)";

    /*
     * @param content: content source code
     * @return: a list of links
     */
    public List<String> parseUrls(String content) {
        List<String> result = new ArrayList<>();
        if (content == null || content.length() == 0) {
            return result;
        }

        Pattern p = Pattern.compile(HTML_HREF_TAG_PATTERN);
        Matcher m = p.matcher(content);

        while(m.find()){
            String cur = m.group();
            String url = cur.substring(cur.indexOf("\"")+1);
            if (url.startsWith("#") || url.isEmpty()) {
                continue;
            }
            result.add(url);
        }

        return result;
    }
}