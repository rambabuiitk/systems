

/*Implement a simple client for GFS (Google File System, a distributed file system), it provides the following methods:

read(filename). Read the file with given filename from GFS.
write(filename, content). Write a file with given filename & content to GFS.
There are two private methods that already implemented in the base class:

readChunk(filename, chunkIndex). Read a chunk from GFS.
writeChunk(filename, chunkIndex, chunkData). Write a chunk to GFS.
To simplify this question, we can assume that the chunk size is chunkSize bytes. (In a real world system, it is 64M).
The GFS Client's job is splitting a file into multiple chunks (if need) and save to the remote GFS server. chunkSize will
be given in the constructor. You need to call these two private methods to implement read & write methods. */

import java.util.HashMap;
import java.util.Map;

// Definition of BaseGFSClient
class BaseGFSClient {
     private Map<String, String> chunk_list;
     public BaseGFSClient() {}
     public String readChunk(String filename, int chunkIndex) {
     // Read a chunk from GFS
         return null;
     }
     public void writeChunk(String filename, int chunkIndex,
                            String content) {
         // Write a chunk to GFS
     }
 }



public class GFSClient extends BaseGFSClient {

    private int chunkSize;
    private Map<String, Integer> map = new HashMap<>();

    /*
     * @param chunkSize: An integer
     */public GFSClient(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    /*
     * @param filename: a file name
     * @return: conetent of the file given from GFS
     */
    public String read(String filename) {
        if (!map.containsKey(filename)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.get(filename); ++i) {
            sb.append(readChunk(filename, i));
        }
        return sb.toString();
    }

    /*
     * @param filename: a file name
     * @param content: a string
     * @return: nothing
     */
    public void write(String filename, String content) {
        int size = (content.length() - 1) / chunkSize + 1;
        map.put(filename, size);
        for (int i = 0; i < size; ++i) {
            writeChunk(filename, i, content.substring(i * chunkSize, Math.min(content.length(), (i + 1) * chunkSize)));
        }
    }
}
