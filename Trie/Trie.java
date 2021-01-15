import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;


/**
 * This class contains a basic retrieval tree (Trie) data structure
 * supporting the basic methods insert, remove, and contains all 
 * with the performance of linear time  with regard to the size
 * of each string in question ( O (size of word)). 
 *
 *
 * @author Patrick
 */ 
public class Trie {

    private TrieNode root;
    private int size;
 
    static class TrieNode {
        
        private Map<Character, TrieNode> children;
        private boolean isKey;

        /**
         *Constructs a new TrieNode with the given letter and
         * whether the node is the final letter of a word
         * or not.
         */
        TrieNode() {
            children = new HashMap<>(26);
        }

        /**
         * Getter for returning the children map.
         * @return the children hash map of the node
         */
        Map<Character, TrieNode> getChildren() {
            return children;
        }

        /**
         * Sets the isKey field for the TrieNode indicating
         * that this node contains a complete word.
         */
        void setEndOfWord() {
            this.isKey = true;
        }

        /**
         * This method clears the isKey marker denoting 
         * that the node is a word end.
         */
        void clearEndOfWord() {
            this.isKey = false;
        }

        /**
         * This function returns whether the TrieNode contains
         * a complete word key.
         * @return true if the node is at the end of word, false otherwise
         */
        boolean isAtEndOfWord() {
            return isKey;
        }

        boolean isLeaf() {
            return children.size() == 0;
        }

    }

    public Trie () {
        root = new TrieNode();
        size = 0;
    }

    public Trie(String word) {
        root = new TrieNode();
        size = 0;
        this.insert(word);
    }

    public int size() {
        return size;
    }

    /**
     * This method inserts the given word from the trie structure.
     * 
     * @param word the word to be inserted into the trie
     */
    public void insert(String word) { 
    
        char[] lettersArray = word.toCharArray();
        int length = word.length();

        // starting node to check
        TrieNode currentNode = root;

        // iterate through entire string
        for (char currentLetter : lettersArray) {
            // check for letter at node
            TrieNode nextNode = currentNode.getChildren().get (currentLetter);

            // if the current node does not have the character
            // then add a new node there and set the character
            if ( nextNode == null) {
                TrieNode newNode = new TrieNode();
                currentNode.children.put(currentLetter, newNode);
                currentNode = newNode;
            }

            // else the character currently exists
            // descend into next level of Trie at that character in map
            else {
                currentNode = nextNode;
            }
        }

        currentNode.setEndOfWord();
        size++;
    }

    /**
     * The simplest implementation of remove (not space ideal) simply
     * clears the isKey field from the end of the word contained in 
     * Trie
     *
     * @param word the word to be removed from Trie
     * @return true if word was successfully removed, false otherwise
     */
    public boolean remove(String word) { 
    
        char[] wordArray = word.toCharArray();
        TrieNode currentNode = root;

        for (char letter : wordArray) {
            TrieNode nextNode = currentNode.getChildren().get(letter);

            if (nextNode == null) {
                return false;
            }

            else {
                currentNode = nextNode;
            }
        }

        currentNode.clearEndOfWord(); 
        size--;
        return true; 
    }

    /**
     * This method checks whether the given word is contained with
     * this Trie structure.
     * @param word the word to be checked for containment within trie
     * @return true if the word is contained, otherwise false
     */
    public boolean contains(String word) {
     
        char[] wordArray = word.toCharArray();
        TrieNode currentNode = root;

        for (char letter : wordArray) {
            TrieNode nextNode = currentNode.getChildren().get(letter);

            // the letter is not contained within Trie
            // therefore the word is not part of the trie
            if (nextNode == null) {
                return false;
            }

            // move down one level to next Trienode at char
            else  {
                currentNode = nextNode;
            }
        }

        // if the final letter is marked as a word, then return true
        // otherwise, word not contained in Trie
        return currentNode.isAtEndOfWord(); 
    }

    /**
     * This method finds all of the strings within the Trie
     * with the common prefix given as an argument. In other words,
     * it finds all of the substrings located after the prefix given.
     *
     * @param prefix the prefix to be searched from
     * @return an array list of strings contained within Trie from given prefix
     */
    public ArrayList<String> findCommonPrefix(String prefix) {
        
        // find last letter of prefix for searching
        char[] prefixArray = prefix.toCharArray();
        ArrayList<String> commonPrefixes = new ArrayList<>();
        TrieNode currentNode = root;

        // descend through Trie to find subtrie containing children 
        for (char letter : prefixArray) {
            TrieNode nextNode = currentNode.getChildren().get(letter);
            if (nextNode == null)
                return null;
            else {
                currentNode = nextNode;
            }
        }

        
        ArrayList<String> suffixList = new ArrayList<>();

        // depth first search through sub trie to find all postfixes
        Set<Character> subTrieCharSet = currentNode.getChildren().keySet();
        for (char currentLetter : subTrieCharSet) {

            String postFix = String.valueOf(currentLetter);
            TrieNode nextNode = currentNode.getChildren().get(currentLetter);

            postFix += findAllStrings(nextNode);

            //append prefix to postfix and add to list
            commonPrefixes.add("" + prefix + postFix);

        }

        return commonPrefixes;
    }

    /**
     * This method performs a recursive depth first search on the Trie node arrays
     * in order to find all of the substrings within the Trie from
     * a given subTrie node.
     *
     * @param subTrie a trie node representing a sub trie of the Trie
     * @return a substring of chars contained with the subtrie
     */
    private String findAllStrings(TrieNode subTrie) {

        if ()


        // base case is that subtrie is a leaf 
        if (subTrie.isLeaf() ) {
            return ""; 
        }


        // recursive case searches through all sub trie nodes
        // and collects chars into string postfix
        Set<Character> subChildren = subTrie.getChildren().keySet();
        String postFix = "";

        for (char currentLetter : subChildren) {

            postFix = String.valueOf(currentLetter);
            postFix += findAllStrings(subTrie.getChildren().get(currentLetter));

        }
        return postFix;
    }
}

