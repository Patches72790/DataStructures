import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class TestTrie {
    

    @Test
    public void testInsert() {

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

    




}
