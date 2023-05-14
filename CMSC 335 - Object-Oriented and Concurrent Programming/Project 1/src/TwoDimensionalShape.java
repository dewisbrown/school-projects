/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * This is the parent class of all the two-dimensional shapes.
 * All two-dimensional shapes have an area.
 */

public class TwoDimensionalShape extends Shape {
    private double area;
    public TwoDimensionalShape() {
        setNumDimension(2);
    }

    // getter and setter
    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }
}
