/* Author: Luis Moreno
 * Date: March 3, 2023
 *
 * Final Project
 * ---------------------------------------------------------------------------------------------
 * This program applies the OPT algorithm and NEW algorithm for selecting victim pages.
 *
 * The NEW algorithm, as stated in the project documentation, is a modified version
 * of the LRU algorithm. In the LRU algorithm, the victim page is the Least Recently Used page.
 * For the NEW algorithm, the victim page is the second Least Recently Used page.
 *
 * The program prompts the user to enter a value for N, which represents the number of
 * physical frames, and a reference string. Inputs for these values are checked regularly
 * and the user is notified if any value is incorrect.
 *
 * The user can then simulate the OPT or NEW algorithms and view a table that contains the
 * reference string, all physical frames and their values, page faults, and victim pages.
 * The user can view the algorithm working step-by-step by pressing 'enter' once it has started.
 *
 * At any point from the main menu, the user can change the number of physical frames (N)
 * and the reference string.
 */
import java.util.*;

public class FinalProject {
    public static void main(String[] args) {
        boolean end = false;
        boolean nExists = false;
        boolean refStringExists = false;

        int n = -1;
        int[] refString = new int[0];

        // main program loop
        while (!end) {
            menuPrompt();
            int choice = getInt(0);
            switch(choice) {
                case 0 -> {
                    System.out.println("Program terminating...");
                    end = true;
                }
                case 1 -> {
                    System.out.println("Enter a value for N:");
                    n =  getInt(1);
                    nExists = true;
                    System.out.printf("N = %d\n", n);
                }
                case 2 -> {
                    if (nExists) {
                        refString = getRefString(n);
                        refStringExists = true;

                        System.out.print("Reference string: ");
                        for (int j : refString) {
                            System.out.print(j);
                        }
                        System.out.println();
                    } else {
                        System.out.println("A value for N must be entered first.");
                    }
                }
                case 3 -> {
                    if (nExists && refStringExists) {
                        optAlgorithm(n, refString);
                    } else {
                        System.out.println("Input N and a reference string to start simulation.");
                    }
                }
                case 4 -> {
                    if (nExists && refStringExists) {
                        newAlgorithm(n, refString);
                    } else {
                        System.out.println("Input N and a reference string to start simulation.");
                    }
                }
            }
        }
    }

    // main menu prints to console
    private static void menuPrompt() {
        System.out.println("""
            0 – Exit \s
            1 – Input N \s
            2 – Input the reference string \s
            3 – Simulate the OPT algorithm \s
            4 – Simulate the NEW algorithm \s
            Select option:""");
    }

    // get input text from user
    private static int[] getRefString(int n) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Enter the length of your reference string:");
        int max = 0;

        while (true) {
            try {
                int temp = Integer.parseInt(scnr.nextLine());
                if (n <= temp && temp <= 20) {
                    max = temp;
                    break;
                }
                else
                    System.out.printf("Enter a value in the range %d - 20\n", n);
            } catch (NumberFormatException e) {
                System.out.println("Enter a numerical value.");
            }
        }

        int[] refString = new int[max];

        System.out.println("Enter a value in the range 0 - 9:");

        int i = 0;
        while (i < max) {
            try {
                int temp = Integer.parseInt(scnr.nextLine());
                if (0 <= temp && temp <= 9) {
                    refString[i] = temp;
                    i++;
                }
                else
                    System.out.println("Enter a value in the range 0 - 9");
            } catch (NumberFormatException e) {
                System.out.println("Enter a numerical value.");
            }
        }
        return refString;
    }

    // get input text (int) from user
    private static int getInt(int bounds) {
        Scanner scnr = new Scanner(System.in);
        int start;
        int end;

        if (bounds == 0) {
            start = 0;
            end = 5;
        } else {
            start = 2;
            end = 9;
        }

        while (true) {
            try {
                int choice = Integer.parseInt(scnr.nextLine());
                if (start <= choice && choice < end)
                    return choice;
                else
                    System.out.printf("Value entered is out of bounds. " +
                            "Value must be in the range %d - %d\n", start, end - 1);
            } catch (NumberFormatException e) {
                System.out.println("Enter a numerical value.");
            }
        }
    }

    // prints and updates a table while it applies the OPT Algorithm given N and a reference string
    private static void optAlgorithm(int n, int[] refString) {
        Scanner scnr = new Scanner(System.in);
        boolean fullCol = false;
        int colB = 0;

        // Map used to keep track of each frame and count of
        LinkedHashMap<String, Integer> frames = new LinkedHashMap<>();

        // create blank string value table
        String[][] stringValueTable = new String[refString.length][n];
        for (int i = 0; i < refString.length; i++) {
            for (int j = 0; j < n; j++) {
                stringValueTable[i][j] = " ";
            }
        }

        // create blank victim pages row
        String[] victimPages = new String[refString.length];
        for (int i = 0; i < refString.length; i++) {
            victimPages[i] = " ";
        }

        // create blank page faults row
        String[] pageFaults = new String[refString.length];
        for (int i = 0; i < refString.length; i++) {
            pageFaults[i] = " ";
        }

        // updating table until all frames are filled
        while (!fullCol) {
            if (colB > 0) {
                for (int j = 0; j < n; j++) {
                    if (!Objects.equals(stringValueTable[colB - 1][j], " ")) {   // if there is a value in the previous column
                        stringValueTable[colB][j] = stringValueTable[colB - 1][j];    // copy to current column
                    }
                }
            }

            // first if-statement checks if reference string page is already
            // loaded in memory. if not, load reference string page into memory
            for (int j = 0; j < n; j++) {
                if (stringValueTable[colB][j].equals(" ") && colB > 0 &&
                        alreadyLoaded(n, colB, refString, stringValueTable)) {
                    break;
                } else if (stringValueTable[colB][j].equals(" ")) {
                    stringValueTable[colB][j] = String.format("%d", refString[colB]);
                    pageFaults[colB] = "F";
                    break;
                }
            }

            printTable(n, refString, stringValueTable, pageFaults, victimPages);
            scnr.nextLine();    // waits for user to press 'enter' to continue

            // check if column is full, exit loop if so
            for (int j = 0; j < n; j++) {
                if (Objects.equals(stringValueTable[colB][j], " ")) {
                    fullCol = false;
                    break;
                } else {
                    fullCol = true;
                }
            }

            colB++;
        }

        /* old algo for printing table until whole column is full.
         * this algo didn't check for repeating page references within
         * the first several columns

        for (int col = 0; col < n; col++) {
            if (col > 0) {
                for (int j = 0; j < n; j++) {
                    if (!Objects.equals(stringValueTable[col - 1][j], " ")) {   // if there is a value in the previous column
                        stringValueTable[col][j] = stringValueTable[col - 1][j];    // copy to current column
                    }
                }
            }

            for (int j = 0; j < n; j++) {
                if (stringValueTable[col][j].equals(" ")) {
                    stringValueTable[col][j] = String.format("%d", refString[col]);
                    break;
                }
            }

            pageFaults[col] = "F";

            printTable(n, refString, stringValueTable, pageFaults, victimPages);
            scnr.nextLine();
        }

         */

        // fill rest of table
        for (int col = colB; col < refString.length; col++) { // before: for (int col = n; col < refString.length; col++) {
            boolean cont = false;
            frames.clear();

            // check if page number is already in memory (frames already contain value)
            for (int j = 0; j < n; j++) {
                if (refString[col] == Integer.parseInt(stringValueTable[col - 1][j])) {
                    cont = true;
                    break;
                }
            }

            // move on to next column if page already in memory
            if (cont) {
                // update frames with pages from previous iteration
                for (int j = 0; j < n; j++) {
                    stringValueTable[col][j] = stringValueTable[col - 1][j];
                }
                printTable(n, refString, stringValueTable, pageFaults, victimPages);
                scnr.nextLine();    // waits for user to press 'enter' to continue
                continue;
            }

            // select victim page and update values
            for (int j = 0; j < n; j++) {
                frames.put(stringValueTable[col - 1][j], optAlgoHelper(stringValueTable[col - 1][j], refString, col));
            }

            int victimFrame = maxFrameIndex(frames);

            // update table values
            for (int j = 0; j < n; j++) {
                if (j == victimFrame) {
                    stringValueTable[col][j] = String.format("%d", refString[col]);
                    pageFaults[col] = "F";
                    victimPages[col] = stringValueTable[col - 1][j];
                } else {
                    stringValueTable[col][j] = stringValueTable[col - 1][j];
                }
            }

            printTable(n, refString, stringValueTable, pageFaults, victimPages);
            scnr.nextLine();        // waits for user to press 'enter' to continue
        }

    }

    // similar to optAlgorithm but uses newAlgoHelper() to find victim page
    private static void newAlgorithm(int n, int[] refString) {
        Scanner scnr = new Scanner(System.in);
        int colB = 0;
        boolean fullCol = false;

        // Map used to keep track of each frame and count of
        LinkedHashMap<String, Integer> frames = new LinkedHashMap<>();

        // create blank string value table
        String[][] stringValueTable = new String[refString.length][n];
        for (int i = 0; i < refString.length; i++) {
            for (int j = 0; j < n; j++) {
                stringValueTable[i][j] = " ";
            }
        }

        // create blank victim pages row
        String[] victimPages = new String[refString.length];
        for (int i = 0; i < refString.length; i++) {
            victimPages[i] = " ";
        }

        // create blank page faults row
        String[] pageFaults = new String[refString.length];
        for (int i = 0; i < refString.length; i++) {
            pageFaults[i] = " ";
        }

        // updating table until all frames are filled
        while (!fullCol) {
            if (colB > 0) {
                for (int j = 0; j < n; j++) {
                    if (!Objects.equals(stringValueTable[colB - 1][j], " ")) {   // if there is a value in the previous column
                        stringValueTable[colB][j] = stringValueTable[colB - 1][j];    // copy to current column
                    }
                }
            }

            // first if-statement checks if reference string page is already
            // loaded in memory. if not, load reference string page into memory
            for (int j = 0; j < n; j++) {
                if (stringValueTable[colB][j].equals(" ") && colB > 0 &&
                        alreadyLoaded(n, colB, refString, stringValueTable)) {
                    break;
                } else if (stringValueTable[colB][j].equals(" ")) {
                    stringValueTable[colB][j] = String.format("%d", refString[colB]);
                    pageFaults[colB] = "F";
                    break;
                }
            }

            printTable(n, refString, stringValueTable, pageFaults, victimPages);
            scnr.nextLine();        // waits for user to press 'enter' to continue

            // check if column is full, exit loop if so
            for (int j = 0; j < n; j++) {
                if (Objects.equals(stringValueTable[colB][j], " ")) {
                    fullCol = false;
                    break;
                } else {
                    fullCol = true;
                }
            }

            colB++;
        }

        // fill rest of table
        for (int col = colB; col < refString.length; col++) {
            boolean cont = false;
            frames.clear();

            // check if page number is already in memory (frames already contain value)
            for (int j = 0; j < n; j++) {
                if (refString[col] == Integer.parseInt(stringValueTable[col - 1][j])) {
                    cont = true;
                    break;
                }
            }

            // move on to next column if page already in memory
            if (cont) {
                // update frames with pages from previous iteration
                for (int j = 0; j < n; j++) {
                    stringValueTable[col][j] = stringValueTable[col - 1][j];
                }
                printTable(n, refString, stringValueTable, pageFaults, victimPages);
                scnr.nextLine();        // waits for user to press 'enter' to continue
                continue;
            }

            // select victim page and update values
            for (int j = 0; j < n; j++) {
                frames.put(stringValueTable[col - 1][j], newAlgoHelper(stringValueTable[col - 1][j], refString, col)); //TODO: CHANGE THIS TO NEW ALGO HELPER
            }

            int victimFrame = secondMaxFrameIndex(frames);

            // update table values
            for (int j = 0; j < n; j++) {
                if (j == victimFrame) {
                    stringValueTable[col][j] = String.format("%d", refString[col]);
                    pageFaults[col] = "F";
                    victimPages[col] = stringValueTable[col - 1][j];
                } else {
                    stringValueTable[col][j] = stringValueTable[col - 1][j];
                }
            }

            printTable(n, refString, stringValueTable, pageFaults, victimPages);
            scnr.nextLine();        // waits for user to press 'enter' to continue
        }
    }

    // likely an unnecessary method, but made sense to make at the time
    // (similar to code blocks starting at lines 246 and 378)
    private static boolean alreadyLoaded(int n, int col, int[] refString, String[][] stringValueTable) {
        for (int j = 0; j < n; j++) {
            if (Objects.equals(stringValueTable[col - 1][j], String.format("%d", refString[col]))) {
                return true;
            }
        }
        return false;
    }

    // returns index of max key value in LinkedHashMap
    private static int maxFrameIndex(LinkedHashMap<String, Integer> frames) {
        // find max key value from HashMap
        int max = Integer.MIN_VALUE;
        for (int value : frames.values()) {
            if (value > max)
                max = value;
        }

        // find its index in the HashMap (there is probably an easier way to do this)
        int count = 0;
        for (int value : frames.values()) {
            if (value == max) {
                break;
            }
            count++;
        }

        return count;
    }

    // returns index of second-highest key value in LinkedHashMap
    private static int secondMaxFrameIndex(LinkedHashMap<String, Integer> frames) {
        // find max key value from HashMap
        int max = Integer.MIN_VALUE;
        for (int value : frames.values()) {
            if (value > max)
                max = value;
        }

        int secondMax = Integer.MIN_VALUE;
        for (int value : frames.values()) {
            if (value > secondMax && value < max)
                secondMax = value;
        }

        // find its index in the HashMap
        int count = 0;
        for (int value : frames.values()) {
            if (value == secondMax) {
                break;
            }
            count++;
        }

        /* print frames and values (used to check if algo worked))
        frames.entrySet().stream().forEach(entry ->
                System.out.println("Frame " + entry.getKey() + " : " + entry.getValue()));
         */

        return count;
    }

    // returns the amount of spaces until the number appears
    // in the reference string (traversing forwards from index)
    private static int optAlgoHelper(String framePage, int[] refString, int index) {
        for (int i = 0; i < refString.length - index; i++) {
            if (refString[i + index] == Integer.parseInt(framePage)) {
                return i;
            }
        }
        return 100;     // value never shows up for the rest of the reference string
    }

    // returns the amount of spaces until the number appears
    // in the reference string (traversing backwards from index)
    private static int newAlgoHelper(String framePage, int[] refString, int index) {
        int k = index - 1;
        for (int i = 1; i <= index; i++) {
            if (refString[k] == Integer.parseInt(framePage)) {
                return i;
            }
            k--;
        }
        return 100;     // should never return 100 since traversing back in the reference string
    }

    // print table given all values (updates one column at a time from optAlgorithm() and newAlgorithm())
    private static void printTable(int n , int[] refString, String[][] stringValueTable, String[] pageFaults, String[] victimPages) {
        String stringRef = "Reference string";
        int refStringWidth = stringRef.length() + 4;
        int tableWidth = refString.length * 4 + 2;
        String border = "-".repeat(refStringWidth) + "-".repeat(tableWidth - 1);

        // prints reference string row
        System.out.println(border);
        System.out.printf("%s%s |", stringRef, " ".repeat(refStringWidth - stringRef.length() - 1));
        for (int col = 0; col < refString.length; col++) {
            System.out.printf(" %d |", refString[col]);
        }
        System.out.println("\n" + border);

        // prints physical frame rows
        for (int row = 0; row < n; row++) {
            System.out.printf("Physical frame %d    |", row);
            for (int j = 0; j < refString.length; j++) {
                System.out.printf(" %s |", stringValueTable[j][row]);
            }
            System.out.println();
        }
        System.out.println(border);

        // prints page faults row
        System.out.print("Page faults         |");
        for (int col = 0; col < refString.length; col++) {
            System.out.printf(" %s |", pageFaults[col]);
        }
        System.out.println("\n" + border);

        // prints victim pages row
        System.out.print("Victim pages        |");
        for (int col = 0; col < refString.length; col++) {
            System.out.printf(" %s |", victimPages[col]);
        }
        System.out.println("\n" + border);
    }
}

