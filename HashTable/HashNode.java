

/**
 * This class type HashNode stores the values stored in the HashTableMap 
 * with the associated key and value pairings.
 * @author Patrick Harvey
 */
class HashNode<KeyType extends Comparable<KeyType>, ValueType> {

    final private KeyType key;
    final private ValueType value;

    /**
     * Constructor for the HashNode that creates a HashNode storing 
     * a KeyType key and a ValueType value.
     * 
     * @param key - key for accessing this node within HashTableMap
     * @param value - value associated with this key stored in HashTable
     */
    public HashNode(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key for this node.
     * @return - the key associated with this node.
     */
    public KeyType getKey() {
        return key;
    }

    /**
     * Returns the value associated with this node.
     * @return - the value associated with this HashNode.
     */
    public ValueType getValue() {
        return value;
    }
    
}
