package DataStructures.StackQueuePractice;

public interface QueueADT<T> {

  public void enqueue(T element);
  public T dequeue();
  public T peek();
  public boolean isEmpty();

}
