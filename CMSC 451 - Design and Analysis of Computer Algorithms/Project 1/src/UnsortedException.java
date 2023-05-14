/* Project 1
 *
 * Name: Luis Moreno
 * Date: May 1, 2023
 * Class: CMSC 451 7380 Design and Analysis of Computer Algorithms (2232)
 *
 * Custom exception that is thrown when an array is not sorted after running
 * a sorting algorithm on the data set.
 */

public class UnsortedException extends Exception {
    public UnsortedException(String message) {
        super(message);
    }
}
