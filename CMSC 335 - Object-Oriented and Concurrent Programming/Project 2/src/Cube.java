/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
 *
 * Objects of this class represent a cube.
 * This class is a child of ThreeDimensionalShape.
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

    @Override
    public String toString() {
        return "Shape: Cone, Length: " + length +
                ", Volume: " + String.format("%.3f", this.getVolume());
    }
}
