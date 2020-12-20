package DataStructures.LinkedListPractice;

public interface ListADT<T extends Comparable<T>> {

  public T remove();
  public void add(T element);
  public boolean isEmpty();
  public int size();

}
