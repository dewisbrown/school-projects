/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
 *
 * Objects of this class represent a circle.
 * This class is a child of TwoDimensionalShape.
 *
 */
import static java.lang.Math.PI;

public class Circle extends TwoDimensionalShape {
    private double radius;
    private double circumference;

    public Circle(double radius) {
        this.radius = radius;
        setArea(area());
        circumference = 2 * PI * radius;
    }

    private double area() {
        return Math.pow(radius, 2) * PI;
    }
    public double getCircumference() {
        return circumference;
    }

    @Override
    public String toString() {
        return "Shape: Circle, Radius: " +  radius + ", Circumference: " + String.format("%.3f", circumference)
                + ", Area: " + String.format("%.3f", this.getArea());
    }
}
