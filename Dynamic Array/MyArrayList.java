package DataStructures.LinkedListPractice;

public class MyArrayList<T extends Comparable<T>> implements ListADT<T> {

  T[] array;
  int size;
  int capacity;

  @Override public T remove() {
    return null;
  }

  @Override public void add(T element) {

  }

  @Override public boolean isEmpty() {
    return false;
  }

  @Override public int size() {
    return 0;
  }
}
