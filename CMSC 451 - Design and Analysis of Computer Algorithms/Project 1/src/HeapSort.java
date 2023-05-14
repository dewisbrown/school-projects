/* Project 1
 *
 * Name: Luis Moreno
 * Date: May 1, 2023
 * Class: CMSC 451 7380 Design and Analysis of Computer Algorithms (2232)
 *
 * The Heap Sort algorithm used in this program is cited above its use.
 */

public class HeapSort extends AbstractSort {
    private int count;
    private long time;
    private long startTime;

    /**
     * <p>The sort and heapify methods in HeapSort are taken from the citation below.</p>
     * <p></p>
     * <p>Title: Java Program for Heap Sort</p>
     * <p>Author: GeeksforGeeks</p>
     * <p>Date: April 15, 2023</p>
     * <p>Availability:
     * <a href="https://www.geeksforgeeks.org/java-program-for-heap-sort/#">Java Program for Heap Sort</a></p>
     */
    public int[] sort(int[] arr) throws UnsortedException {
        long startTime = System.nanoTime();
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }

        time = System.nanoTime() - startTime;

        if (isSorted(arr))
            return arr;
        else
            throw new UnsortedException("HeapSort did not sort the array properly.");
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private void heapify(int[] arr, int n, int i) {
        incrementCount();
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    protected void startSort() {
        startTime = System.nanoTime();
    }

    protected void endSort() {
        time = System.nanoTime() - startTime;
    }

    protected void incrementCount() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public long getTime() {
        return time;
    }

    private boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1])
                return false;
        }
        return true;
    }
}
