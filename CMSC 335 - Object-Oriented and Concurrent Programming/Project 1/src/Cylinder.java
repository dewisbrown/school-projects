/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * Objects of this class represent a cylinder.
 * This class is a child of ThreeDimensionalShape.
 *
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
}
