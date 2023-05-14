/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
 *
 * Objects of this class represent a cylinder.
 * This class is a child of ThreeDimensionalShape.
 */
import static java.lang.Math.PI;

public class Cylinder extends ThreeDimensionalShape {
    private double radius;
    private double height;

    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
        setVolume(volume());
    }

    private double volume() {
        return Math.pow(radius, 2) * height * PI;
    }

    @Override
    public String toString() {
        return "Shape: Cylinder, Radius: " + radius +
                ", Height: " + height + ", Volume: " + String.format("%.3f", this.getVolume());
    }
}
