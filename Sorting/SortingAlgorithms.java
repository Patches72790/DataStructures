package DataStructures.Sorting;

import DataStructures.PriorityQueueAndHeaps.Heap;
import java.util.Arrays;


public class SortingAlgorithms {

  //////////////////////////////////////////////////////////////////////////////////////////////
  //Various types of comparison sorting techniques

  /**
   * Find minimum index of an element within an array of int.
   * @param array
   * @param start
   * @param end
   * @return
   */
  public static int findMinimum(int[] array, int start, int end) {

    int minIndex = start;
    int minValue = array[start];

    for (int j = start; j < end; j++) {

      if (array[j] < minValue) {
        minIndex = j;
        minValue = array[j];
      }
    }
    return minIndex;
  }

  /**
   * Swap position of firstIndex with secondIndex.
   *
   * @param array
   * @param firstIndex
   * @param secondIndex
   */
  public static void swap(int[] array, int firstIndex, int secondIndex) {

    int temp = array[secondIndex];
    array[secondIndex] = array[firstIndex];
    array[firstIndex] = temp;
  }

  /**
   * Selection Sort Complexity: O(n^2)
   *
   * Basic Idea: Find smallest index and swap with the rightmost element of "sorted" part
   * of the array
   *
   * Algorithm:
   *    outer loop from 0 --> n-1
   *    inner loop from index + 1 --> n-1
   *      find smallest element
   *      swap with element at index
   *
   */
  public static void selectionSort(int[] array) {

    int minIndex;

    for (int i = 0; i < array.length - 1; i++) {

      minIndex = findMinimum(array, i + 1, array.length);

      if (array[minIndex] < array[i]) {
        swap(array, i, minIndex);
      }
    }
  }

  /**
   * Inserts the value at the appropriate position left of the rightIndex.
   *
   * @param array
   * @param rightIndex
   * @param value
   */
  public static void insert(int[] array, int rightIndex, int value) {

    int j = rightIndex;

    for (; j >= 0 && array[j] > value; j--) {
      array[j + 1] = array[j];
    }

    array[j + 1] = value;
  }

  /**
   * Insertion Sort Complexity: O(n^2)
   * Best Case (Partially sorted): O(n)
   *
   * Basic Idea: Check whether index is less than index - 1, and swap repeatedly until
   * order is achieved
   *
   * Algorithm:
   *  start with index 1 until array.size - 1
   *    while element at index - 1 >= 0 AND array[index - 1] > array[i]
   *      shift array[j] to array[j + 1] (to the right)
   *    put lowest value at point j + 1
   *
   *
   * @param array
   */
  public static void insertionSort(int[] array) {

    for (int i = 1; i < array.length; i++) {
      insert(array, i - 1, array[i]);
    }
  }


  /**
   * Heap Sort Complexity: O(n * log n)
   *
   * Basic Idea: Heapify an array (into a max-heap) and swap highest priority item with
   * last item of heap until the array is properly sorted.
   *
   * Algorithm description:
   *  1. heapify an array into max-heap
   *  2. for (index = 0; index < size -1; index++)
   *        array[size - 1 - index] = heapRemove()
   *        **This replaces the highest priority element with the final element of heap
   *        **Then it percolates the swapped element down
   *
   * @param heap
   */
  public static void heapSort(Heap<Integer> heap) {

    heap.heapify();

    for (int i = heap.size() - 1; i > 0; i--) {
      heap.heap[i] = heap.removeBest();
    }

  }

/////////////////////////////////////////////////////////////////////////////////////////////
//Divide and Conquer Algorithms (i.e. recursive sorting techniques)
//
// This paradigm splits is characterized by splitting large problems into 2 smaller problems
//
//  It is generally effective for problems of O(n^2) complexity
//  The average complexity is improved to O(n * log n)
//
//  Merge Sort: majority of work goes into re-JOINING the split arrays
//
//  Quick Sort: Work goes into effectively SPLITTING arrays into sorted halves
//
/////////////////////////////////////////////////////////////////////////////////////////////

  public static void merge(int[] array, int start, int mid, int end) {

    //calculate lengths for low and high half arrays
    int[] lowHalf = new int[mid - start + 1];
    int[] highHalf = new int[end - mid];

    //copy contents of array into lower half
    for (int i = 0; i < lowHalf.length; i++) {
      lowHalf[i] = array[i + start];
    }

    //copy contents of array into higher half
    for (int j = 0; j < highHalf.length; j++) {
      highHalf[j] = array[mid + 1 + j];
    }

    //set count variables for merging
    int low = 0, high = 0;
    int count = start;

    //copy over higher value in each half array
    while (low < lowHalf.length && high < highHalf.length) {
      if (lowHalf[low] < highHalf[high]) {
        array[count] = lowHalf[low];
        low++;
        count++;
      } else {
        array[count] = highHalf[high];
        count++;
        high++;
      }
    }

    //copy over remaining values in half arrays once one is finished
    while (low < lowHalf.length) {
      array[count] = lowHalf[low];
      low++;
      count++;
    }

    while (high < highHalf.length) {
      array[count] = highHalf[high];
      high++;
      count++;
    }
  }


  /**
   * Complexity: O (n * log n)
   *
   *
   */
  public static void mergeSort(int[] array, int start, int end) {

    //base-case: start >= end (i.e. 0 or 1)


    //recursive case:
    if (start < end) {

      //find midpoint
      int middle = (start + end) / 2;

      //recurse through left half-list
      mergeSort(array, start, middle);

      //recurse through right half-list
      mergeSort(array, middle + 1, end);

      //merge two sub arrays
      merge(array, start, middle, end);

    }
  }



  public static int quickselect() { //todo finish this algorithm for finding median
                                    //O(n) time
    return -1;
  }

  public static int partition(int[] array, int startIndex, int endIndex) {

    int leftIndex = startIndex + 1;
    int rightIndex = endIndex;
    int pivotIndex = startIndex;
    boolean partitionFinished = false;


    while (!partitionFinished) {

      while (array[leftIndex] <= array[pivotIndex]) {
        leftIndex++;
      }

      while (array[rightIndex] > array[pivotIndex]) {
        rightIndex--;
      }

      if (rightIndex <= leftIndex) {
        swap(array, pivotIndex, rightIndex);
        pivotIndex = rightIndex;
        partitionFinished = true;
      }

      else {
        swap(array, leftIndex, rightIndex);
        leftIndex++;
        rightIndex--;

      }
    }
    return pivotIndex;
  }


  /**
   * Complexity:
   *    Best case:  O(n * log n)
   *    Worst case: O(n^2)
   *
   */
  public static void quickSort(int[] array, int start, int end) {

    if (start < end) {
      int pivot = partition(array, start, end);
      quickSort(array, start, pivot - 1);
      quickSort(array, pivot + 1, end);

    }
  }


  public static void main(String[] args) {

    int[] array = new int[] {4, 7, 1, 45, 6, 2, 67, 12};
    int[] expected = new int[] {1, 2, 4, 6, 7, 12, 45, 67};

    Heap<Integer> heap = new Heap<>(new Integer[] {4, 7, 1, 45, 6, 2, -67, 12, 14, 16, 58});

//  selectionSort(array);
//    insertionSort(array);
//    mergeSort(array, 0, array.length - 1);
    heapSort(heap);
//    quickSort(array, 0, array.length - 1);

    
    System.out.println(Arrays.toString(array));


  }


}
