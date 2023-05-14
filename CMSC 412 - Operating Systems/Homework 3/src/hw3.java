/* Homework 3
 *
 * Author Luis Moreno
 * Date: January 30, 2023
 *
 * This program checks if numbers in the range 100000-999999
 * are Vampire numbers. Two threads search this range, one checking
 * even numbers and the other checking odd numbers.
 *
 * A vampire number is a number that can be written as the product of two numbers,
 * where the digits of the original number are the same as the digits of the product,
 * but in a different order.
 *
 * The program prints a statement each time a Vampire number is found, then prints the total count
 * before the program ends.
 */
public class hw3 {

    public static void main(String[] args) {
        MyThread even = new MyThread(false);
        MyThread odd = new MyThread(true);

        even.start();
        odd.start();

        // do not continue until both worker threads are done
        try {
            even.join();
            odd.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        // print total vampire number count
        System.out.printf("The TOTAL number of vampire numbers is %d%n", (even.getVampNumCount() + odd.getVampNumCount()));
    }
}

class MyThread extends Thread {
    private final boolean odd;
    private final String threadNum;
    private int vampNumCount;

    /** A boolean is used to differentiate between
     * even/odd worker threads when a MyThread object is created.
     *
     * @param odd (boolean) : this flag represents whether a thread
     *            is checking even or odd numbers for Vampire numbers
     */
    public MyThread(boolean odd) {
        this.odd = odd;
        vampNumCount = 0;

        if (odd)
            threadNum = "Second";
        else
            threadNum = "First";
    }

    /** Getter for vampNumCount.
     *
     * @return (int) : count of Vampire numbers found by the worker thread
     */
    public int getVampNumCount() { return vampNumCount; }

    /** Returns true if the product of each half equals the original number.
     * A for-loop tests each permutation for each half number.
     *
     * @param fullNum (int) : a six-digit number in the range 100000-999999
     * @return (boolean) : true if a number is a Vampire number, false if not
     */
    public boolean isVamp(int fullNum) {
        String numString = Integer.toString(fullNum);
        String firstHalf = numString.substring(0, numString.length()/2);
        String secondHalf = numString.substring(numString.length()/2);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (Integer.parseInt(firstHalf) * Integer.parseInt(secondHalf) == fullNum) {
                    return true;
                }
                firstHalf = perm(firstHalf, i);
                secondHalf = perm(secondHalf, j);
            }
        }
        return false;
    }

    /** Used by both halves of a six-digit number.
     * The iteration count (i) of the for-loop in isVamp() determines
     * which permutation to return using a switch statement.
     * This method works for three-digit numbers only, so revisions
     * would need to be made to include larger/smaller numbers.
     *
     * @param halfNum (String) : three-digit number
     * @param i (int) : represents the case/permutation to be done to halfNum
     * @return (String) : permutation of three-digit number
     */
    public String perm(String halfNum, int i) {
        StringBuilder sb = new StringBuilder(halfNum);
        switch (i) {
            case 0 -> {
                sb.setCharAt(0, halfNum.charAt(0));
                sb.setCharAt(1, halfNum.charAt(2));
                sb.setCharAt(2, halfNum.charAt(1));
                return sb.toString();
            }
            case 1 -> {
                sb.setCharAt(0, halfNum.charAt(1));
                sb.setCharAt(1, halfNum.charAt(0));
                sb.setCharAt(2, halfNum.charAt(2));
                return sb.toString();
            }
            case 2 -> {
                sb.setCharAt(0, halfNum.charAt(1));
                sb.setCharAt(1, halfNum.charAt(2));
                sb.setCharAt(2, halfNum.charAt(0));
                return sb.toString();
            }
            case 3 -> {
                sb.setCharAt(0, halfNum.charAt(2));
                sb.setCharAt(1, halfNum.charAt(0));
                sb.setCharAt(2, halfNum.charAt(1));
                return sb.toString();
            }
            case 4 -> {
                sb.setCharAt(0, halfNum.charAt(2));
                sb.setCharAt(1, halfNum.charAt(1));
                sb.setCharAt(2, halfNum.charAt(0));
                return sb.toString();
            }
            default -> {
                System.out.println("Something happened...");
                return "000";
            }
        }
    }

    @Override
    public void run() {
        int i;
        if (odd)
            i = 100001;
        else
            i = 100000;
        while (i < 1000000) {
            if (isVamp(i)) {
                System.out.printf("%s worker found: %s%n", threadNum, i);
                vampNumCount++;
            }
            i += 2;
        }
        System.out.printf("%s worker found %d Vampire numbers%n", threadNum, vampNumCount);
    }
}

