/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
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
