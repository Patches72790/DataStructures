/**
 * Interface for a set data type that supports adding, removing and contains operations.
 * @param <T> the type parameter for the elements of the set
 */
interface SetADT<T> {

    /**
     * Add a new element to the set.
     * @param el the new element to add to the set
     * @return true if the element has been added to the set, false if it was already in the set
     * @throws IllegalArgumentException if the new element passed to the method is null
     */
    public boolean add(T el);

    /**
     * Return true if the element is in the set, false otherwise.
     * @param el the element to check for
     * @return true if the element is in the set, false if it is not
     */
    public boolean contains(T el);

    /**
     * Remove element from the set.
     * @param el the element to remove from the set
     * @return true if the element has been removed, false otherwise (if it was not in the set)
     */
    public boolean remove(T el);

}
