/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
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

    @Override
    public String toString() {
        return "Shape: Triangle, Base: " + base + ", Height: " + height +
                ", Area: " + String.format("%.3f", this.getArea());
    }
}
