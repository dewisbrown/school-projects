/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * Objects of this class represent a square.
 * This class is a child of TwoDimensionalShape.
 *
 */

public class Square extends TwoDimensionalShape {
    private double length;

    public Square(double length) {
        this.length = length;
        setArea(area());
    }

    private double area() {
        return length * length;
    }
}
