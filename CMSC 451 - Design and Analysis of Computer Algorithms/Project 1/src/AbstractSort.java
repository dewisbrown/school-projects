/* Project 1
 *
 * Name: Luis Moreno
 * Date: May 1, 2023
 * Class: CMSC 451 7380 Design and Analysis of Computer Algorithms (2232)
 *
 * Abstract class for each sorting class with methods to monitor critical operation count
 * and sort time in nanoseconds.
 */

public abstract class AbstractSort {
    public int[] sort(int[] arr) throws UnsortedException {
        return arr;
    }
    protected void startSort() {}
    protected void endSort() {}

    protected void incrementCount() {}

    /** Returns critical operation count */
    public int getCount() {
        return 0;
    }

    /** Returns sort time in nanoseconds */
    public long getTime() {
        return 0;
    }
}
