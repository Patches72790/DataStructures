
// --== CS400 File Header Information ==--
// Name: Patrick Harvey
// Email: plharvey@wisc.edu
// Team: AB
// TA: Sophie Stephenson
// Lecturer: Gary Dahl
// Notes to Grader: see other classes below for full implementation
// Classes Used: HashNode.java, MapADT.java, TestHashTable.java

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * An implementation of a Hash Table with generic types KeyType mapped to ValueType. 
 * This Hash Table handles collisions with the chaining method by utilizing a linked list
 * at each index of the hash table array.
 * 
 * @author Patrick Harvey
 */
public class HashTableMap<KeyType extends Comparable<KeyType>, ValueType> implements MapADT<KeyType, ValueType> {

    private LinkedList<HashNode<KeyType, ValueType>>[] hashTable;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.8;

    /**
     * This constructor creates a new HashTableMap object with the specified
     * capacity amount.
     * 
     * @param capacity - the size of the map to be made
     */
    @SuppressWarnings("unchecked")
    public HashTableMap(int capacity) {

        if (capacity <= 0) {
            throw new NumberFormatException("Cannot have a capacity <= 0.");
        }

        hashTable = new LinkedList[capacity];
        size = 0;
        this.capacity = capacity;
    }

    /**
     * Default constructor that creates a HashTableMap with capacity of 10.
     */
    public HashTableMap() {
        this(10);
    }

    /**
     * Returns the size of this HashTableMap
     * @return the size of this map
     */
    public int size() {
        return this.size;
    }

    /**
     * Test method for TestHashTable error checking.
     * @return the current capacity of this hash table
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Hashes the given KeyValue key with its type's corresponding hashCode() method
     * inherited from Object base call. The hash is then mod'ed with the current capacity 
     * of the hash table.
     * 
     * @param key the key to be hashed for the table
     * @return the corresponding hashcode value for accessing the hash table
     */
    private int hash(KeyType key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     *
     * This method doubles the hash table size and rehashes all entries
     * if and only if the size of the table ever becomes 80% of the capacity 
     * of the table.
     * Load Factor = 0.8
     */
    private void growTable() {
        //get list of all elements of current hash Table
        LinkedList<HashNode<KeyType, ValueType>> hashEntries = getAllElements();

        //double capacity of table
        capacity *= 2;

        //make new hash table
        clear();

        //rehash elements in temp list and put back into new hash table
        for (HashNode<KeyType, ValueType> element : hashEntries) {
            put(element.getKey(), element.getValue());
        }        
    }

    /**
     * Helper method for the growTable() method to retrieve all elements currently
     * within the hash table as a linked list.
     * @return a linked list containing all elements of the hash table
     */
    private LinkedList<HashNode<KeyType, ValueType>> getAllElements() {
        //make a temporary linked list containing hash table entries
        LinkedList<HashNode<KeyType, ValueType>> tempList = new LinkedList<>();

        //add all elements from hash table lists into temp list
        for (LinkedList<HashNode<KeyType, ValueType>> list : hashTable) {

            //add all elements from linked list into temp
            if (list != null) {
                for (HashNode<KeyType, ValueType> element : list) {
                    tempList.add(element);
                }
            }
        }
        return tempList;
    }

    /**
     * Helper method to determine if the hash table's load factor is >= 0.8
     * by comparing (size / capacity) to LOAD_FACTOR fields.
     * @return true if the table IS overloaded, false otherwise
     */
    private boolean tableIsOverloaded() {
        return ((double) size / capacity) >= LOAD_FACTOR;
    }

    /**
     * Stores the value at the location in the hash table specified by hash(key).
     * This method utilizes chaining with a linked list to avoid collisions.
     * 
     * @param key - the key for storing the item in the hash table
     * @param value - the value to be stored in the hash table
     * @return true if the value was put in hashTable[hash(key)], false otherwise
     */
    public boolean put(KeyType key, ValueType value) {

        HashNode<KeyType, ValueType> node = new HashNode<>(key, value);
        int hashValue = hash(node.getKey());

        // if the index of table is empty, make new linked list
        if (hashTable[hashValue] == null) {
            hashTable[hashValue] = new LinkedList<>();
            hashTable[hashValue].addFirst(node);
            size++;

            //check if table is overloaded, and grow table if necessary
            if (tableIsOverloaded()) {
                growTable();
            }

            return true;
        } 
        
        //if the value already exists in table, then false
        else if (containsKey(key)) {
            return false;
        } 
        
        //add the node at the head of the linked list, as normal
        else {
            hashTable[hashValue].addFirst(node);
            size++;
            
            //check if table is overloaded, and grow table if necessary
            if (tableIsOverloaded()) {
                growTable();
            }
            return true;
        }
    }

    /**
     * This method searches for the key-value pair for the given key 
     * through this hash table. If the key does not exist, then it 
     * throws an exception.
     * 
     * @param key the key being searched for within the hashtable
     * @return the value located at hashtable[hash(key)], if it exists
     * @throws NoSuchElementException if the value does not exist within the hash table
     */
    public ValueType get(KeyType key) throws NoSuchElementException {

        int hashValue = hash(key);

        //if the array index is empty
        if (hashTable[hashValue] == null) {
            throw new NoSuchElementException("Key did not exist within hash table.");
        }

        //search in linked list for key
        for (HashNode<KeyType, ValueType> item : hashTable[hashValue]) {

            //if key exists, return value
            if (key.equals(item.getKey())) {
                return item.getValue();
            }
        }

        //else throw exception
        throw new NoSuchElementException("Key did not exist within hash table.");
    }


    /**
    * Searches through the hash table for the given key.
    *
    * @return true if the hash table contains the given key, false otherwise
    */
    public boolean containsKey(KeyType key) {
        int hashValue = hash(key);

        //if the array index is empty
        if (hashTable[hashValue] == null) {
            return false;
        }

        //search through linked list at hash value index for key
        for (HashNode<KeyType, ValueType> item : hashTable[hashValue]) {
            if (key.equals(item.getKey())) {
                return true;
            }
        }

        //key does not exist within table
        return false;
    }


    /**
     * Removes the value associated with the give key from the hash table, and 
     * returns a reference to that value. If the given key does not exist within 
     * the table, it returns null.
     * 
     * @return ValueType if the key-value pair exists within, otherwise null
     */ 
    public ValueType remove(KeyType key) {

        int hashValue = hash(key);
        ValueType removee;

        //if the array index is empty
        if (hashTable[hashValue] == null) {
            return null;
        }

        //search through list for key at hashed index
        for (HashNode<KeyType, ValueType> item : hashTable[hashValue]) {
            if (key.equals(item.getKey())) {
                removee = get(key);
                hashTable[hashValue].remove(item);
                size--;
                return removee;
            }
        }

        //value did not exist within hash table
        return null;
    }

    /**
     * Clears the current hash table of all entries and resets size to 0.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        hashTable = new LinkedList[capacity];
        size = 0;
    }

}