/* Project 1
 *
 * Name: Luis Moreno
 * Date: May 1, 2023
 * Class: CMSC 451 7380 Design and Analysis of Computer Algorithms (2232)
 *
 * The Shell Sort algorithm used in this program is cited above its use.
 */


public class ShellSort extends AbstractSort {
    private int count;
    private long time;
    private long startTime;

    /**
     * <p>The sort method in ShellSort is taken from the citation below.</p>
     * <p></p>
     * <p>Title: Shell Sort in Java</p>
     * <p>Author: baeldung</p>
     * <p>Date: February 15, 2023</p>
     * <p>Availability: <a href="https://www.baeldung.com/java-shell-sort">Shell Sort in Java</a></p>
     */
    public int[] sort(int[] arr) throws UnsortedException {
        int n = arr.length;
        startSort();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > key) {
                    incrementCount();
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = key;
            }
        }
        endSort();

        if (isSorted(arr))
            return arr;
        else
            throw new UnsortedException("ShellSort did not sort the array properly.");
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