package DataStructures.LinkedListPractice;

import java.util.Iterator;

public class SinglyLinkedList<E extends Comparable<E>> implements Iterable<E>, ListADT<E> {

  private Node<E> head;
  private Node<E> tail;
  private int size;

  public SinglyLinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  public Node<E> getHead() {
    return head;
  }

  public void setHead(Node<E> head) {
    this.head = head;
  }

  public Node<E> getTail() {
    return tail;
  }

  public void setTail(Node<E> tail) {
    this.tail = tail;
  }

  public int getSize() {return size;}

  /**
   * Finds specified node in last by linear search through node data
   *
   * @param previousData reference to particular node's data
   * @return the found node if present, null otherwise
   */
  private Node<E> findNodeByData(E previousData) throws NodeNotFoundException {
    Node<E> current = head;
    Node<E> foundNode = null;
    while (current != null) {
      if (current.getData().equals(previousData)) {
        foundNode = current;
        break;
      }
      current = current.getNext();
    }
    if (foundNode == null) throw new NodeNotFoundException("Node wasn't found in list!");
    return foundNode;
  }


  /**
   *
   */
  public void swapAnyTwo(E nodeDataA, E nodeDataB) {
    Node<E> nodeA = null;
    Node<E> nodeB = null;

    try {
      nodeA = findNodeByData(nodeDataA);
      nodeB = findNodeByData(nodeDataB);
    } catch (NodeNotFoundException e) {
      System.out.println(e.getMessage());
      return;
    }

  }

  /**
   * This method swaps two nodes following previousNode.
   *
   * @param previousData reference to node before two nodes to be swapped
   */
  public void swapNextTwo(E previousData) {
    Node<E> previousNode = null;

    //if Node not found, print error message and return
    try {
      previousNode = findNodeByData(previousData);
    } catch (NodeNotFoundException e) {
      System.out.println(e.getMessage());
      return;
    }

    //if node was found, then swap the next two nodes
    Node<E> temp = previousNode.getNext().getNext().getNext();
    previousNode.getNext().getNext().setNext(previousNode.getNext());
    previousNode.setNext(previousNode.getNext().getNext());
    previousNode.getNext().getNext().setNext(temp);
  }

  //removeAfter()
  public void removeAfter() {

  }

  //insertAfter()
  public void insertAfter() {

  }


  /**
   * Appends newNode reference to the end of the LinkedList.
   *
   * @param newNode newNode reference to be appended
   */
  public void append(Node<E> newNode) {
    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      tail.setNext(newNode);
      tail = newNode;
    }
  }

  /**
   * Prepends a node to the list.
   *
   * @param newNode the node reference to be prepended
   */
  public void prepend(Node<E> newNode) {
    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      newNode.setNext(head);
      head = newNode;
    }
  }


  /**
   * Prints the contents of the Linked List.
   */
  public void printList() {
    Node<E> currentNode = getHead();

    while (currentNode != null) {
      System.out.print(currentNode);
      currentNode = currentNode.getNext();
    }
    System.out.println();
  }


  @Override public E remove() {
    return null;
  }

  @Override public void add(E element) {
    if (head == null) {
      head = new Node<>(element);
      tail = head;
    }
    else {
      Node<E> temp = new Node<>(element);
      tail.setNext(temp);
      tail = temp;
    }
  }

  @Override public boolean isEmpty() {
    return false;
  }

  @Override public int size() {
    return 0;
  }

  public String toString() {

    String data = "";
    Node<E> current = head;
    while (current != null) {
      data += current.toString();
      current = current.getNext();
    }

    return data;
  }

  /**
   * Allows this linked list to be used within for-each loops and enhances looping speeds.
   *
   * @return new iterator
   */
  @Override public LinkedListIterator<E> iterator() {
    return new LinkedListIterator<E>(head);
  }


  /**
   * Iterator for this singly linked list implementation.
   * @param <T> the generic type for this iterator
   */
  public class LinkedListIterator<T> implements Iterator<T> {

    Node<T> current;

    public LinkedListIterator(Node<T> current) {
      this.current = current;
    }

    @Override public boolean hasNext() {
      return current != null && head != null;
    }

    @Override public T next() {
      Node<T> temp = current;
      current = current.getNext();
      return temp.getData();
    }
  }

}
