/**
 * This file contains the program code for the Trie data structure
 * that can find all common prefixes of the given words contained therein.
 * 
 * @author Patrick Harvey
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class contains a basic retrieval tree (Trie) data structure
 * supporting the basic methods insert, remove, and contains all 
 * with the performance of linear time  with regard to the size
 * of each string in question ( Big-Oh (size of word)). 
 *
 * @author Patrick
 */ 
public class Trie implements Dictionary {

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
    public List<String> findCommonPrefix(String prefix) {
        
        // find last letter of prefix for searching
        char[] prefixArray = prefix.toCharArray();
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

        // container for storing suffixes found
        ArrayList<String> suffixList = new ArrayList<>();

        // make recursive call to find all suffixes based on prefix given
        findAllStrings("", suffixList, currentNode);
            
        // return list of full words with prefix appended to suffix
        return suffixList.stream().map(suffix -> prefix + suffix).collect(Collectors.toList());
    }

    /**
     * This method performs a recursive depth first search on the Trie node arrays
     * in order to find all of the substrings within the Trie from
     * a given subTrie node.
     *
     * @param currentSuffix the suffix currently being built from Trie
     * @param suffixList reference to the list being added to with complete suffixes
     * @param subTrie a trie node representing a sub trie of the Trie
     */
    private void findAllStrings(String currentSuffix, ArrayList<String> suffixList, TrieNode subTrie) {

        // if end of word found, add suffix to list
        if (subTrie.isAtEndOfWord()) {
            suffixList.add(currentSuffix);
        }

        // base case is that subtrie is a leaf 
        if (subTrie.isLeaf() ) {
            return; 
        }

        // recursive case searches through all sub trie nodes
        // via depth first approach by descending into
        // successive children and incrementally building up suffix
        Set<Character> subChildren = subTrie.getChildren().keySet();
        for (char currentLetter : subChildren) {
            findAllStrings(currentSuffix + currentLetter, suffixList, subTrie.getChildren().get(currentLetter));
        }
    }
}

