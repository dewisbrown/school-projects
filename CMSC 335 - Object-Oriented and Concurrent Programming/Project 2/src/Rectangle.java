/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes
 *
 * Objects of this class represent a rectangle.
 * This class is a child of TwoDimensionalShape.
 */
public class Rectangle extends TwoDimensionalShape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
        setArea(area());
    }

    private double area() {
        return length * width;
    }

    @Override
    public String toString() {
        return "Shape: Rectangle, Length: " + length + ", Width: " + width + ", Area: " + this.getArea();
    }
}
