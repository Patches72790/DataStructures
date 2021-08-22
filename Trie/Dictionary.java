import java.util.List;

/**
 * Public interface implementing a basic dictionary with
 * word searching capabilities and the ability to find
 * all words with a common prefix.
 * 
 */
public interface Dictionary {

    public void insert(String word);

    public boolean remove(String word);

    public boolean contains(String word);

    public List<String> findCommonPrefix(String prefix);

}
