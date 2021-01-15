package DataStructures.IteratorPractice;
import DataStructures.LinkedListPractice.Node;

import java.util.Iterator;

public class LinkedListIterator<T> implements Iterator<T> {

  private Node<T> nextNode;

  public LinkedListIterator(Node<T> newNode) {
    this.nextNode = newNode;
  }

  @Override public boolean hasNext() {
    return nextNode != null;
  }

  @Override public T next() {
    T tmp = nextNode.getData();
    nextNode = nextNode.getNext();
    return tmp;
  }
}
