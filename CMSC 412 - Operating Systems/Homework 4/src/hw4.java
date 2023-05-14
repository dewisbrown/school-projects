/* Homework 4
 *
 * Author Luis Moreno
 * Date: February 13, 2023
 *
 * This program uses an altered version of the Greedy Banker's algorithm
 * to find multiple safe sequences, if possible, given data from an input file.
 * If the input file does not have properly formatted data, the program will
 * stop reading the file and print an error.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class hw4 {

    public static void main(String[] args) {
        String fileName = getInput();

        // initialize allSeq and format
        ArrayList<ArrayList<Integer>> allSeq;
        Format format = new Format(fileName);

        // read input file line-by-line
        try {
            File file = new File(fileName);
            Scanner scnr = new Scanner(file);
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                if (!format.analyze(line)) {
                    System.out.printf("%s -- Formatting error found%n", line);
                    System.out.printf("********** %s **********%n%n", fileName);
                    break;
                } else {
                    System.out.println(line);
                }
            }
            scnr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // runs if there are no formatting errors in the input file
        if (format.getCompatible()) {
            format.setNeeds();

            // pass initialized format object, work vector, and finish vector to bankers method
            allSeq = bankers(format);

            System.out.printf("********** %s **********%n%n", fileName);

            // print results
            switch(allSeq.size()) {
                case 0 -> System.out.println("System is in an UNSAFE state");
                case 1 -> System.out.printf("System is in a SAFE state, the safe sequence is %s%n", allSeq.get(0));
                case 2 -> {
                    System.out.println("System is in a SAFE state, with two safe sequences:");
                    for (int i = 0; i < 2; i++) {
                        System.out.println(allSeq.get(i));
                    }
                }
                default -> System.out.println("Null allSeq...");
            }
        } else {
            System.out.println("The input file is not formatted properly for this program.");
        }
    }

    /**
     * This method adjusts the Greedy version of the Banker's algorithm provided by
     * the module 4 commentary. Adjustments were made to find more than one safe sequence
     * if possible with the input file data.
     *
     * @param format (Format) allows access to input file data
     * @return allSeq (ArrayList<ArrayList<Integer>>) safe sequences are stored in this list of lists
     */
    public static ArrayList<ArrayList<Integer>> bankers(Format format) {
        ArrayList<Integer> seq = new ArrayList<>();
        ArrayList<ArrayList<Integer>> allSeq = new ArrayList<>();
        ArrayList<Integer> checked = new ArrayList<>();
        Vector<Integer> work = new Vector<>();
        Vector<Boolean> finish = new Vector<>(Collections.nCopies(format.getProcessCount(), false));

        // assign elements from format.available array to the work vector
        for (int num : format.getAvailble()) {
            work.addElement(num);
        }

        while (allSeq.size() < 2) {
            boolean found = false;
            for (int i = 0; i < format.getProcessCount(); i++) {
                if (!finish.get(i) && !checked.contains(i)) {
                    int j;
                    for (j = 0; j < format.getResourceCount(); j++) {
                        if (format.getNeeds()[i][j] > work.get(j))
                            break;
                    }
                    if (j == format.getResourceCount()) {
                        // update work values
                        for (j = 0; j < format.getResourceCount(); j++) {
                            work.set(j, work.get(j) + format.getAllocation()[i][j]);
                        }
                        seq.add(i);
                        finish.set(i, true);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                if (seq.size() == 0) {                              // no safe sequence
                    return allSeq;
                } else {
                    // reset work and finish to values before last process was added to seq
                    int lastProcess = seq.remove(seq.size() - 1);
                    checked.add(lastProcess);
                    for (int j = 0; j < format.getResourceCount(); j++) {
                        work.set(j, work.get(j) - format.getAllocation()[lastProcess][j]);
                    }
                    finish.set(lastProcess, false);
                }
            } else if (seq.size() == format.getProcessCount()) {    // safe sequence has been found
                allSeq.add(new ArrayList<>(seq));
                checked.clear();

                // reset work and finish to values before last two processes were added to seq
                int lastProcess = seq.remove(seq.size() - 1);
                for (int j = 0; j < format.getResourceCount(); j++) {
                    work.set(j, work.get(j) - format.getAllocation()[lastProcess][j]);
                }
                finish.set(lastProcess, false);
                int temp = seq.remove(seq.size() - 1);
                for (int j = 0; j < format.getResourceCount(); j++) {
                    work.set(j, work.get(j) - format.getAllocation()[temp][j]);
                }
                finish.set(temp, false);
                checked.add(temp);
            } else {
                checked.clear();
            }
        }
        return allSeq;
    }

    /**
     * The only requirements for the filename is length > 4.
     *
     * @return (String) name of file to open
     */
    public static String getInput() {
        Scanner scnr = new Scanner(System.in);
        String fileName = "";

        while (fileName.length() < 4) {
            System.out.println("Enter the name of the file: ");
            fileName = scnr.nextLine();
        }
        return fileName;
    }
}

/*
 * The Format class allows the creation of different matrices given the data
 * of a properly formatted input file. All the "set" methods return a boolean value
 * that hw4.java uses to signal an incorrectly formatted input file.
 */
class Format {
    private int n;
    private int m;
    private int[] totals;
    private int[] available;
    private int[][] max;
    private int[][] allocation;
    private int[][] needs;
    private boolean compatible;

    public Format(String fileName) {
        System.out.printf("%n********** %s **********%n", fileName);
        compatible = true;
    }
    public boolean analyze(String line) {
        if (line.startsWith("N="))
            compatible = setProcessCount(line);
        else if (line.startsWith("M="))
            compatible = setResourceCount(line);
        else if (line.startsWith("TOTAL"))
            compatible = setTotals(line);
        else if (line.startsWith("AVAILABLE"))
            compatible = setAvailable(line);
        else if (line.startsWith("MAX"))
            compatible = setMax(line);
        else if (line.startsWith("ALLOCATION"))
            compatible = setAllocation(line);
        else
            compatible = false;
        return compatible;
    }

    private boolean setProcessCount(String line) {
        try {
            n = Integer.parseInt(String.valueOf(line.charAt(2)));
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    private boolean setResourceCount(String line) {
        try {
            m = Integer.parseInt(String.valueOf(line.charAt(2)));
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    private boolean setTotals(String line) {
        String[] lineElements = line.split(" ");
        totals = new int[m];

        if (lineElements.length > m + 1)
            return false;

        int j = 0;
        try {
            for (int i = 1; i <= m; i++) {
                totals[j] = Integer.parseInt(lineElements[i]);
                j++;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private boolean setAvailable(String line) {
        String[] lineElements = line.split(" ");
        available = new int[m];

        if (lineElements.length > m + 1)
            return false;

        int j = 0;
        try {
            for (int i = 1; i <= m; i++) {
                available[j] = Integer.parseInt(lineElements[i]);
                j++;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private boolean setMax(String line) {
        max = new int[n][m];
        String[] lineElements = line.split(" ");

        if (lineElements.length > (n * m) + 1)
            return false;

        int k = 1;
        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    max[i][j] = Integer.parseInt(lineElements[k]);
                    k++;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private boolean setAllocation(String line) {
        allocation = new int[n][m];
        String[] lineElements = line.split(" ");

        if (lineElements.length > (n * m) + 1)
            return false;

        int k = 1;
        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    allocation[i][j] = Integer.parseInt(lineElements[k]);
                    k++;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public void setNeeds() {
        needs = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                needs[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }
    public int getProcessCount() {
        return n;
    }
    public int getResourceCount() {
        return m;
    }
    public int[] getTotals() {
        return totals;
    }
    public int[] getAvailble() {
        return available;
    }
    public int[][] getMax() {
        return max;
    }
    public int[][] getAllocation() {
        return allocation;
    }
    public int[][] getNeeds() {
        return needs;
    }
    public boolean getCompatible() {
        return compatible;
    }
}
/*
// project pseudocode algorithm to find one safe process sequence
int i = 0;
while (i < format.getProcessCount()) {
    boolean found = false;
    for (int j = 0; j < format.getResourceCount(); j++) {
        if (!finish.get(i) && format.getNeeds()[i][j] <= work.get(j)) {
            found = true;
        } else {
            found = false;
            break;
        }
    }
    if (found) {
        seq.add(i); // add process to seq
        for (int j = 0; j < format.getResourceCount(); j++) {   // update work values
            work.set(j, work.get(j) + format.getAllocation()[i][j]);
        }
        finish.set(i, true);    // update boolean value at index i in finish
        i = 0;                  // reset and continue while loop
        continue;
    }
    i++;
}
*/