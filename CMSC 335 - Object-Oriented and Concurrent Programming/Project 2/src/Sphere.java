/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
 *
 * Objects of this class represent a sphere.
 * This class is a child of ThreeDimensionalShape.
 *
 */
import static java.lang.Math.PI;

public class Sphere extends ThreeDimensionalShape {
    private double radius;

    public Sphere(double radius) {
        this.radius = radius;
        setVolume(volume());
    }

    private double volume() {
        return Math.pow(radius, 3) * PI * 1.33;
    }

    @Override
    public String toString() {
        return "Shape: Sphere, Radius: " +  radius + ", Volume: " + String.format("%.3f", this.getVolume());
    }
}
