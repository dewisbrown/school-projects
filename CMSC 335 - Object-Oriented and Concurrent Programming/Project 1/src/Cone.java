import static java.lang.Math.PI;

public class Cone extends ThreeDimensionalShape {
    private double radius;
    private double height;

    public Cone(double radius, double height) {
        this.radius = radius;
        this.height = height;
        setVolume(volume());
    }

    private double volume() {
        return Math.pow(radius, 2) * PI * height * .33;
    }
}
