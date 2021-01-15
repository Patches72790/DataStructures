import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestTrie {
    

    @Test
    public void testCommonPrefixes() {

        Trie trie = new Trie();
    
        trie.insert("each");
        trie.insert("eat");
        trie.insert("ear");
        trie.insert("port");
        trie.insert("poor");
        trie.insert("peet");
        trie.insert("pear");
        trie.insert("peach");
        trie.insert("earth");

        List<String> commonPrefixes = trie.findCommonPrefix("ea");
        ArrayList<String> expected = new ArrayList<>(Arrays.asList( "each", "ear", "earth", "eat"));
        
        assertEquals("Error", expected, commonPrefixes);

    }

    @Test
    public void testEmptyCommonPrefixes() {

        Trie trie = new Trie();
    
        trie.insert("each");
        trie.insert("eat");
        trie.insert("ear");
        trie.insert("earth");
        trie.insert("port");
        trie.insert("porter");
        trie.insert("portly");
        trie.insert("poor");
        trie.insert("peet");
        trie.insert("pear");
        trie.insert("peach");

        
        List<String> commonPrefixes = trie.findCommonPrefix("po");
        ArrayList<String> expected = new ArrayList<>(Arrays.asList( "poor", "port", "porter", "portly" ));
        
        assertEquals("Error", expected, commonPrefixes);

    }

    @Test
    public void testEmptyPrefix() {
        Trie trie = new Trie();
    
        trie.insert("each");
        trie.insert("eat");
        trie.insert("ear");
        trie.insert("earth");
        trie.insert("port");
        trie.insert("porter");
        trie.insert("portly");
        trie.insert("poor");
        trie.insert("peet");
        trie.insert("pear");
        trie.insert("peach");

        
        List<String> commonPrefixes = trie.findCommonPrefix("");
        ArrayList<String> expected = new ArrayList<>(Arrays.asList( "each", "ear", "earth", "eat", 
             "peach", "pear", "peet", "poor", "port", "porter", "portly"));
        
        assertEquals("Error", expected, commonPrefixes);
    }
}
