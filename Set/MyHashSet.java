// --== CS400 File Header Information ==--
// Name: Patrick Harvey
// Email: plharvey@wisc.edu
// Team: AB
// TA: Sophie Stephenson
// Lecturer: Gary Dahl
// Notes to Grader: submitted for Hashset assignment week 6
// Classes Used: java.util.Hashtable

import java.util.Hashtable;

/**
 * A hash set class implementing a set ADT with the java Hashtable.
 * 
 */
public class MyHashSet<T> implements SetADT<T> {
    
    Hashtable<T, T> hashtable;

    public MyHashSet() {
        hashtable = new Hashtable<>();
    }

    /**
     * Add a new element to the set.
     * @param el the new element to add to the set
     * @return true if the element has been added to the set, false if it was already in the set
     * @throws IllegalArgumentException if the new element passed to the method is null
     */
    public boolean add(T el) throws IllegalArgumentException {

        if (el == null) throw new IllegalArgumentException("You cannot add a null element to this set.");

        return hashtable.put(el, el) == null;

        // try {
        //     // check if element is already in set
        //     if (hashtable.get(el) != null) {
        //         return false;
        //     }

        //     // attempt to load element into hash table
        //     hashtable.put(el, el);

        // } catch (NullPointerException e1) {
        //     throw new IllegalArgumentException("You cannot add a null element to this set.");
        // }

        // // return true if successful
        // return true;
    }

    /**
     * Return true if the element is in the set, false otherwise.
     * @param el the element to check for
     * @return true if the element is in the set, false if it is not
     */
    public boolean contains(T el) {
        return hashtable.contains(el);
    }

    /**
     * Remove element from the set.
     * @param el the element to remove from the set
     * @return true if the element has been removed, false otherwise (if it was not in the set)
     */
    public boolean remove(T el) {

        try {
            //attempt to remove element from set
            if (hashtable.remove(el) != null) {
                return true;
            }

        } 
        // catch exception if argument was null
        catch (NullPointerException e1) {
            System.out.println("You cannot remove a null element from this set.");
        }

        //return false if the argument was not in the set
        return false;
    }

    public String toString() {
        return hashtable.keySet().toString();
    }

    /**
     * Demonstrates the general functionality of each of the three methods
     * written above.
     */
    public static void main(String[] args) {
        
        MyHashSet<Integer> set = new MyHashSet<>();
        // demo add method 
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println("Added 1, 2, 3 to set.");

        System.out.println("Set already contains three? " + (!set.add(3))) ;

        // demo contains method
        System.out.println("Set contains 3? " + set.contains(3));
        System.out.println("Set contains 4? " + set.contains(4));

        // demo remove method
        System.out.println("Removed 3? " + set.remove(3));
        System.out.println("Removed 3 again? " + set.remove(3));

        System.out.println(set);
    }
}
