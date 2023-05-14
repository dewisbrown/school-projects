/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
 *
 * This is the parent class to all the shape objects
 * in this program.
 */
public class Shape {
    private int numDimension;

    public Shape() {
    }

    // getter and setter
    public void setNumDimension(int numDimension) {
        this.numDimension = numDimension;
    }
    public int getNumDimension() {
        return numDimension;
    }
}
