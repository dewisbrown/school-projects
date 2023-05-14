/* Project 1
 *
 * Name: Luis Moreno
 * Date: May 1, 2023
 * Class: CMSC 451 7380 Design and Analysis of Computer Algorithms (2232)
 *
 * This program tests the following two sorting algorithms: heap sort and shell sort.
 * Twelve data set sizes are used in this program. For each data set size, the two algorithms
 * sort forty different data sets.
 *
 * The critical operation count and sort time for every sort
 * is written to a text file in the format:
 * <data set size> <count> <time> <count> <time> ...
 * <data set size> <count> <time> <count> <time> ...
 * ...
 *
 * The text files are meant to be read by ReportGUI.java to process
 * and print calculations on the data.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkSorts {
    private static int[][] heapCounts = new int[12][40];
    private static int[][] shellCounts = new int[12][40];
    private static long[][] heapTimes = new long[12][40];
    private static long[][] shellTimes = new long[12][40];

    public static void main(String[] args) {
        // Warm up JVM
        warmUp();

        // Run both sorting algorithms with different array sizes
        int size = 1000;
        for (int i = 0; i < 12; i++) {
            runSorts(size, i);
            size += 1000;
        }

        // Write to heapsort data file
        try (FileWriter writer = new FileWriter("heapSortData.txt")){
            int arrSize = 1000;
            for (int i = 0; i < 12; i++) {
                writer.write(arrSize + " ");
                for (int j = 0; j < 40; j++) {
                    writer.write(heapCounts[i][j] + " " + heapTimes[i][j] + " ");
                }
                writer.write("\n");
                arrSize += 1000;
            }
        } catch (IOException e) {
            System.err.println("Failed to write to file!");
        }

        // Write to shellsort data file
        try (FileWriter writer = new FileWriter("shellSortData.txt")){
            int arrSize = 1000;
            for (int i = 0; i < 12; i++) {
                writer.write(arrSize + " ");
                for (int j = 0; j < 40; j++) {
                    writer.write(shellCounts[i][j] + " " + shellTimes[i][j] + " ");
                }
                writer.write("\n");
                arrSize += 1000;
            }
        } catch (IOException e) {
            System.err.println("Failed to write to file!");
        }
    }

    // produces sorting data for 40 different, random arrays and inputs data to matrices
    private static void runSorts(int size, int i) {
        for (int j = 0; j < 40; j++) {
            HeapSort heapSort = new HeapSort();
            ShellSort shellSort = new ShellSort();

            // Create random int array to sort
            int[] arrA = randomArray(size);
            int[] arrB = Arrays.copyOf(arrA, arrA.length);

            try {
                heapSort.sort(arrA);
            } catch (UnsortedException e) {
                System.out.println(e.getMessage());
            }
            try {
                shellSort.sort(arrB);
            } catch (UnsortedException e) {
                System.out.println(e.getMessage());
            }

            // Save counts and times to 2D arrays
            heapCounts[i][j] = heapSort.getCount();
            shellCounts[i][j] = shellSort.getCount();

            heapTimes[i][j] = heapSort.getTime();
            shellTimes[i][j] = shellSort.getTime();
        }
    }

    // returns are an array of random integers
    private static int[] randomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

    // Warms up JVM
    private static void warmUp() {
        try {
            for (int i = 0; i < 1000; i++) {
                HeapSort heap = new HeapSort();
                ShellSort shell = new ShellSort();
                heap.sort(randomArray(6500));
                shell.sort(randomArray(6500));
            }
        } catch (UnsortedException e) {
            System.out.println(e.getMessage());
        }
    }
}
