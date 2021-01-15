package DataStructures.StackQueuePractice;
import java.util.LinkedList;

public class LinkedQueue<T> implements QueueADT<T> {

  private LinkedList<T> list;

  public LinkedQueue() {
    list = new LinkedList<>();
  }

  @Override public void enqueue(T element) {
    list.add(element);
  }

  @Override public T dequeue() {
    return list.removeFirst();
  }

  @Override public T peek() {
    return list.peek();
  }

  public int size() {
    return list.size();
  }

  @Override public boolean isEmpty() {
    return list.isEmpty();
  }
}
