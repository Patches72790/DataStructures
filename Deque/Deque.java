package DataStructures.Deque;

import java.util.LinkedList;

public class Deque<T extends Comparable<T>> implements DequeADT<T> {

  LinkedList<T> deque;

  public Deque() {
    deque = new LinkedList<>();
  }

  @Override public void enqueueFront(T element) {
    deque.addFirst(element);
  }

  @Override public void enqueueBack(T element) {
    deque.addLast(element);
  }

  @Override public T dequeueFront() {
    return deque.removeFirst();
  }

  @Override public T dequeueBack() {
    return deque.removeLast();
  }

  @Override public T peekFront() {
    return deque.peekFirst();
  }

  @Override public T peekBack() {
    return deque.peekLast();
  }

  @Override public boolean isEmpty() {
    return deque.size() == 0;
  }

  @Override public int size() {
    return deque.size();
  }
}
