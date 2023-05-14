/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * Objects of this class represent a cube.
 * This class is a child of ThreeDimensionalShape.
 *
 */

public class Cube extends ThreeDimensionalShape {
    private double length;

    public Cube(double length) {
        this.length = length;
        setVolume(volume());
    }

    private double volume() {
        return Math.pow(length, 3);
    }
}
