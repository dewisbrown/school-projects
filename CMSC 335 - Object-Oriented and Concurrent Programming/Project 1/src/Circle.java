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
}
