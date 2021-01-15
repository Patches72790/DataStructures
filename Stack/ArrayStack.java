package DataStructures.StackQueuePractice;

import java.util.Arrays;

public class ArrayStack<T> implements StackADT<T> {

  private int size = 0;
  private T[] stack;
  private T[] shadowStack;

  @SuppressWarnings("unchecked") public ArrayStack(int capacity) {

    this.stack = (T[]) new Object[capacity];
    this.shadowStack = (T[]) new Object[capacity * 2];
  }


  /**
   * Swaps the actual stack array with the shadow stack array
   */
  @SuppressWarnings("unchecked") private void useShadowStack() {
    stack = shadowStack;
    shadowStack = (T[]) new Object[stack.length * 2];
  }

  @Override public void push(T element) {
    if (size == stack.length) {
      useShadowStack();
      stack[size] = element;
      shadowStack[size] = element;
      shadowStack[size - (stack.length / 2)] = stack[size - (stack.length / 2)];
      size++;

    } else {
      stack[size] = element;
      shadowStack[size] = element;

      if (size == 0) {
        size++;
        return;
      }

      shadowStack[((size - (stack.length / 2)) + stack.length) % stack.length] =
          stack[((size - (stack.length / 2)) + stack.length) % stack.length];
      size++;

    }
  }

  @Override public T pop() {
    if (size == stack.length) {
      T temp = stack[size - 1];
      stack[size - 1] = null;
      shadowStack[size - 1] = null;
      size--;
      return temp;
    }

    T temp = stack[size - 1];
    stack[size - 1] = null;
    shadowStack[size - 1] = null;
    size--;
    return temp;
  }

  @Override public T peek() {
    return stack[size];
  }

  @Override public boolean isEmpty() {
    return false;
  }

  public String toString() {


    return "Stack: " + Arrays.toString(stack) + "\nShadow: " + Arrays.toString(shadowStack);
  }
}
