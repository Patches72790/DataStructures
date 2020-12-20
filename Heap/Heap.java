package DataStructures.PriorityQueueAndHeaps;


import java.util.Arrays;
import java.util.NoSuchElementException;

//***Key Application: Implements a Priority Queue with O(log n) time complexity insert/remove
//
//Heap Properties:
//1. Order
//    a. EVERY node has higher priority than its children (Parent >= Ch1 & Ch2)
//        i.  max heap =====> (parent >= Ch1 & Ch2)
//        ii. min heap =====> (parent <= Ch1 & Ch2)
//    b. CHILDREN are not ordered (key diff. from BST)
//
//    BENEFIT: PARTIAL ORDERING is more efficient than full SORTING
//
//2. Shape (complete tree i.e. left aligned)
//    a. shortest possible height for number of nodes
//    b. any holes (null references) are at the deepest level of tree
//    c. all holes are to the right of non-holes
//
//    BENEFIT: shortest possible height = log(size)
//
//3. Time Complexity
//    1. Insert  -- O(log n)
//    2. Remove  -- O(log n)
//    3. Heapify -- O(n) (with bubbleDown() adjustment)
//
public class Heap<T extends Comparable<T>> implements PriorityQueueADT<T> {

  //heap storage within an array
  //
  // parent(index) = (index - 1) / 2
  // left(index)   = 2 * index + 1
  // right(index)  = 2 * index + 2
  //
  public Object[] heap;
  private int size;
  private int capacity;

  /**
   * @param capacity
   */
  public Heap(int capacity) {
    this.capacity = capacity;
    this.heap = new Object[capacity];
  }

  public Heap(T[] array) {
    this.heap = array;
    this.capacity = array.length;
    this.size = array.length;
  }

  public int size() {
    return size;
  }


  /**
   * 1. Add new node at heap[size]
   * <p>
   * 2. (percolate / bubble) -- swap lower nodes with higher until proper order is achieved
   * a. compare node to parent
   * i.   if node is lower priority, finish (base)
   * ii.  else swap node with parent (recurse)
   *
   * @param newData
   */
  @SuppressWarnings("unchecked") @Override public void insert(T newData) {
    //TODO needs exception for increasing array size

    //first step, add at end of array (for empty array)
    if (size == 0) {
      heap[size++] = newData;
    } else {
      heap[size] = newData;

      //check if the the new node has higher priority than parent node
      if (((T) heap[size]).compareTo((T) heap[(size - 1) / 2]) > 0) {

        //sets initial indices for heap
        int fromIndex = size;
        int toIndex = (size - 1) / 2;

        //percolates the node to appropriate position
        percolateUp(fromIndex, toIndex);

      }
      size++;
    }
  }

  /**
   * Used within the insert method to move higher priority elements higher within the heap.
   *
   * @param fromIndex - the position of the item lower in heap
   * @param toIndex   - the position of the item higher in heap
   */
  @SuppressWarnings("unchecked") private void percolateUp(int fromIndex, int toIndex) {

    //recursively swap child with parent until heap is properly ordered
    if (((T) heap[fromIndex]).compareTo((T) heap[toIndex]) > 0) {
      T val = (T) heap[toIndex];
      heap[toIndex] = heap[fromIndex];
      heap[fromIndex] = val;

      //recursive call with parent and parent's parent
      percolateUp(toIndex, (toIndex - 1) / 2);
    }
  }

  /**
   * Removes the item of highest priority from this heap.
   * Algorithm:
   * 1. replace root with heap[size - 1]
   * 2. percolate down until properly ordered
   * - compare index to both children
   * - swap highest priority child (if the parent is lower priority)
   * - recurse of child index
   *
   * @return reference to highest priority node of heap
   */
  @SuppressWarnings("unchecked") @Override public T removeBest() throws NoSuchElementException {

    if (isEmpty())
      throw new NoSuchElementException("Empty heap!");

    T removed = (T) heap[0];

    //swaps root with final element of heap
    heap[0] = heap[size - 1];
    heap[size - 1] = null;
    size--;

    //re-heapify the array after removing matches
    heapify();

    return removed;
  }

  /**
   * Helper method for remove
   */
  @SuppressWarnings("unchecked") private void percolateDown(int index) {

    int higherPriorityChildIndex;
    int leftChildIndex = getLeftChild(index);
    int rightChildIndex = getRightChild(index);

    //base case - either of the children are outside of the size of heap
    if (rightChildIndex >= size) {
      //both children are greater than size
      if (leftChildIndex >= size ) {
        return;
        //the left is default highest priority child
      } else {
        higherPriorityChildIndex = leftChildIndex;
      }
    }

    //if left and right are null, then stop percolating down
    else if (heap[getLeftChild(index)] == null && heap[getRightChild(index)] == null) {
      return;
    }

    //left child has higher priority since no right child exists
    else if (heap[getLeftChild(index)] != null && heap[getRightChild(index)] == null) {
      higherPriorityChildIndex = getLeftChild(index);
    }

    //both children exist
    else {
      //find higher priority child
      if ( ((T) heap[ getLeftChild(index) ]).compareTo((T) heap[getRightChild(index)]) < 0) {
        higherPriorityChildIndex = getRightChild(index);
      } else {
        higherPriorityChildIndex = getLeftChild(index);
      }
    }

    //swap with higher priority child, if necessary
    if (((T) heap[index]).compareTo((T) heap[higherPriorityChildIndex]) < 0) {

      swap(index, higherPriorityChildIndex);
      percolateDown(higherPriorityChildIndex);
    }

  }


  public void heapify() {
    for (int i = (size - 1) / 2; i >= 0; i--) {
      percolateDown(i);
    }
  }

  @SuppressWarnings("unchecked") private void swap(int indexOne, int indexTwo) {
    T temp = (T) heap[indexTwo];
    heap[indexTwo] = heap[indexOne];
    heap[indexOne] = temp;
  }

  private int getLeftChild(int index) {
    return index * 2 + 1;
  }

  private int getRightChild(int index) {
    return index * 2 + 2;
  }

  private int getParentIndex(int index) {
    return (index - 1) / 2;
  }

  @SuppressWarnings("unchecked") @Override public T peekBest() {
    return (T) heap[0];
  }

  @Override public boolean isEmpty() {
    return size == 0;
  }

  @Override public String toString() {
    return Arrays.toString(heap);
  }



  public static void main(String[] args) {

    Heap<Integer> heap = new Heap<>(20);

    heap.insert(5);
    heap.insert(8);
    heap.insert(3);
    heap.insert(10);
    heap.insert(15);

    heap.removeBest();
    heap.removeBest();


    System.out.println(heap);


  }

}
