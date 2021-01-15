package DataStructures.Deque;

public interface DequeADT<T extends Comparable<T>> {

  public void enqueueFront(T element);

  public void enqueueBack(T element);

  public T dequeueFront();

  public T dequeueBack();

  public T peekFront();

  public T peekBack();

  public boolean isEmpty();
  public int size();




}
