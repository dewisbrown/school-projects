/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * Objects of this class represent a triangle.
 * This class is a child of TwoDimensionalShape.
 *
 */

public class Triangle extends TwoDimensionalShape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
        setArea(area());
    }

    private double area() {
        return base * height * 0.5;
    }
}
