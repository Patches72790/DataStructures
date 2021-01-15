package DataStructures.StackQueuePractice;

import java.util.Arrays;

public class ArrayQueue<T> implements QueueADT<T>, Comparable<T> {

  private int size = 0;
  private int enqueueTo = 0; //back of line
  private T[] queue;
  private T[] shadowQueue;
  private int dequeueFrom; //front of line

  @SuppressWarnings("unchecked") public ArrayQueue(int capacity) {
    queue = (T[]) new Object[capacity];
    shadowQueue = (T[]) new Object[capacity * 2];
    dequeueFrom = (enqueueTo - size + queue.length) % queue.length;
  }

  /**
   * Swaps the current queue array with the shadow array and creates a new shadow array with
   * double the size.
   */
  @SuppressWarnings("unchecked") private void useShadowQueue() {
    queue = shadowQueue;
    shadowQueue = (T[]) new Object[shadowQueue.length * 2];
  }

  @Override public void enqueue(T element) {
    if (size == queue.length - 1)
      useShadowQueue();

    else {
      queue[enqueueTo] = element;
      shadowQueue[enqueueTo] = element;
//      shadowQueue[] todo how to implement adding to shadow array?
      enqueueTo++;
      size++;
    }
  }

  @Override public T dequeue() {

    T temp = queue[dequeueFrom];
    queue[dequeueFrom] = null;

    dequeueFrom++;

    return temp;
  }

  @Override public T peek() {
    return queue[dequeueFrom];
  }

  @Override public boolean isEmpty() {
    return false;
  }

  @Override public String toString() {
    return Arrays.toString(queue);
  }

  @Override public int compareTo(T o) {
    return 0; //todo fixme
  }
}
