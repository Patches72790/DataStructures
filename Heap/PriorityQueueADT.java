package DataStructures.PriorityQueueAndHeaps;

//The elements need to be be able to be differentiated
//thus use Comparable<T>
public interface PriorityQueueADT <T extends Comparable<T>> {

  public void insert(T newData); //adds item to PQ

  public T removeBest(); //removes the highest priority item

  public T peekBest(); //highest priority element is return, but not removed

  public boolean isEmpty();// checks whether PQ is empty

}


//Implementation Strategy 1: unsorted linked list or array
//**optimized for insert
//insert:  O(1)
//remove:  O(n)

//Implementation Strategy 2: sorted linked list or array
//**optimized for remove
//insert:  O(n)
//remove:  O(1)

//Implementation strategy 3: HEAP data structure
//**optimized for insert & remove
//insert:  O(log n)
//remove:  O(log n)
