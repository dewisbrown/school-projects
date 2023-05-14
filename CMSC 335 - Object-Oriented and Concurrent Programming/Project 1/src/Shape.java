/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * This is the parent class to all the shape objects
 * in this program.
 *
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
