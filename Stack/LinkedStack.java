package DataStructures.StackQueuePractice;

import java.util.LinkedList;

public class LinkedStack<T> implements StackADT<T> {

  LinkedList<T> list;

  public LinkedStack() {
    list = new LinkedList<>();
  }

  @Override public void push(T element) {
    list.addFirst(element);
  }

  @Override public T pop() {
    return list.removeFirst();
  }

  @Override public T peek() {
    return list.peekFirst();
  }

  @Override public boolean isEmpty() {
    return list.size() == 0;
  }
}
