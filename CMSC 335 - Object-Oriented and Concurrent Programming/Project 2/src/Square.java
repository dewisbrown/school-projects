/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
 *
 * Objects of this class represent a square.
 * This class is a child of TwoDimensionalShape.
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

    @Override
    public String toString() {
        return "Shape: Square, Length: " + length + ", Area: " + this.getArea();
    }
}
