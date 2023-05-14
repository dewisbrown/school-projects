/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * This is the parent class of all the three-dimensional shapes.
 * All three-dimensional shapes have a volume.
 */
public class ThreeDimensionalShape extends Shape {
    private double volume;
    public ThreeDimensionalShape() {
        setNumDimension(3);
    }

    // getter and setter
    public double getVolume() {
        return volume;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }
}
