/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * Objects of this class represent a torus.
 * This class is a child of ThreeDimensionalShape.
 *
 */

import static java.lang.Math.PI;

public class Torus extends ThreeDimensionalShape {
    private double radius;
    private double bigRadius;

    public Torus(double radius, double bigRadius) {
        this.radius = radius;
        this.bigRadius = bigRadius;
        setVolume(volume());
    }

    private double volume() {
        return 2 * Math.pow(PI, 2) * Math.pow(radius, 2) * bigRadius;
    }
}
