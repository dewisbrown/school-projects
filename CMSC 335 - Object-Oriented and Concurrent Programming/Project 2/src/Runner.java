/*
 * Author: Luis Moreno
 * Date: November 1, 2022
 *
 * Project 1 - Shapes
 *
 * This program calculates the area or volume of a user-created shape.
 * The user chooses a shape to create from a menu and inputs its dimensions.
 * A shape object is then created and used to display its area or volume.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {
    static Scanner scnr = new Scanner(System.in);

    // handles selection from main menu
    private static boolean selection(int c) {
        switch (c) {
            case 1 -> {
                createCircle();
                return continuePrompt();
            }
            case 2 -> {
                createRectangle();
                return continuePrompt();
            }
            case 3 -> {
                createSquare();
                return continuePrompt();
            }
            case 4 -> {
                createTriangle();
                return continuePrompt();
            }
            case 5 -> {
                createSphere();
                return continuePrompt();
            }
            case 6 -> {
                createCube();
                return continuePrompt();
            }
            case 7 -> {
                createCone();
                return continuePrompt();
            }
            case 8 -> {
                createCylinder();
                return continuePrompt();
            }
            case 9 -> {
                createTorus();
                return continuePrompt();
            }
            case 10 -> exitMsg();
            default -> System.out.println("Please enter a number in the given selections.");
        }
        return true;
    }

    // main menu prompt
    private static void mainMenu() {
        System.out.println("\nSelect from the menu below:\n");
        System.out.println("""
                1. Construct a Circle
                2. Construct a Rectangle
                3. Construct a Square
                4. Construct a Triangle
                5. Construct a Sphere
                6. Construct a Cube
                7. Construct a Cone
                8. Construct a Cylinder
                9. Construct a Torus
                10. Exit the program""");
    }

    // prints the exit message
    private static void exitMsg() {
        Date date = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MMM dd");
        SimpleDateFormat timeForm = new SimpleDateFormat("h:mm a");

        System.out.print("\nThanks for using the program! ");
        System.out.println("Today is " + dateForm.format(date) + " at " + timeForm.format(date));
    }

    // prompt to continue after creating a shape
    // returns true if user enters Y, false if N
    private static boolean continuePrompt() {
        System.out.println("Would you like to continue? (Y or N)");
        while(true) {
            try {
                String str = scnr.next().toUpperCase();
                if (str.equals("Y"))
                    return true;
                if (str.equals("N")) {
                    exitMsg();
                    return false;
                }
                System.out.println("Please enter Y or N.");
                } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Please enter Y or N.");
            }
        }
    }

    // creates a circle object and prints area/circumference of circle
    private static void createCircle() {
        System.out.println("You have chosen to create a circle.");

        System.out.println("What is the radius of the circle?");
        while(true) {
            try {
                double radius = scnr.nextDouble();
                if (radius < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    Circle circle = new Circle(radius);
                    System.out.println("\nThe circumference of the circle is "
                            + String.format("%.3f", circle.getCircumference()));
                    System.out.println("\nThe area of the circle is " + String.format("%.3f", circle.getArea()));
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }
    }

    // creates a Rectangle object and prints the object area
    private static void createRectangle() {
        double length;
        double width;
        System.out.println("You have chosen to create a rectangle.");

        System.out.println("What is the length of the rectangle?");
        while(true) {
            try {
                length = scnr.nextDouble();
                if (length < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        System.out.println("What is the width of the rectangle?");
        while(true) {
            try {
                width = scnr.nextDouble();
                if (width < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        Rectangle rectangle = new Rectangle(length, width);
        System.out.println("The area of the rectangle is " + rectangle.getArea());
    }

    // creates a Square object and prints the object area
    private static void createSquare() {
        double length;
        System.out.println("You have chosen to create a square.");

        System.out.println("What is the length of the side of the square?");
        while(true) {
            try {
                length = scnr.nextDouble();
                if (length < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        Square square = new Square(length);
        System.out.println("The area of the square is " + square.getArea());
    }

    // creates a Triangle object and prints the object area
    private static void createTriangle() {
        double base;
        double height;
        System.out.println("You have chosen to create a triangle.");

        System.out.println("What is the length of the triangle base?");
        while(true) {
            try {
                base = scnr.nextDouble();
                if (base < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        System.out.println("What is the height of the triangle?");
        while(true) {
            try {
                height = scnr.nextDouble();
                if (height < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        Triangle triangle = new Triangle(base, height);
        System.out.println("The area of the triangle is " + triangle.getArea());
    }

    // creates a Sphere object and prints the object volume
    private static void createSphere() {
        System.out.println("You have chosen to create a sphere.");

        System.out.println("What is the radius of the sphere?");
        while(true) {
            try {
                double radius = scnr.nextDouble();
                if (radius < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    Sphere sphere = new Sphere(radius);
                    System.out.println("The volume of the sphere is " + String.format("%.3f", sphere.getVolume()));
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

    }

    // creates a Cube object and prints the object volume
    private static void createCube() {
        System.out.println("You have chosen to create a cube.");

        System.out.println("What is the length of the cube?");
        while(true) {
            try {
                double length = scnr.nextDouble();
                if (length < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    Cube cube = new Cube(length);
                    System.out.println("The volume of the cube is " + cube.getVolume());
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }
    }

    // creates a Cone object and prints the object volume
    private static void createCone() {
        double radius;
        double height;
        System.out.println("You have chosen to create a cone.");

        System.out.println("What is the radius of the base of the cone?");
        while(true) {
            try {
                radius = scnr.nextDouble();
                if (radius < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        System.out.println("What is the height of the cone?");
        while(true) {
            try {
                height = scnr.nextDouble();
                if (height < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        Cone cone = new Cone(radius, height);
        System.out.println("The volume of the cone is " + String.format("%.3f", cone.getVolume()));
    }

    // creates a Cylinder object and prints the object volume
    private static void createCylinder() {
        double radius;
        double height;
        System.out.println("You have chosen to create a cylinder.");

        System.out.println("What is the radius of the base of the cylinder?");
        while(true) {
            try {
                radius = scnr.nextDouble();
                if (radius < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        System.out.println("What is the height of the cylinder?");
        while(true) {
            try {
                height = scnr.nextDouble();
                if (height < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        Cylinder cylinder = new Cylinder(radius, height);
        System.out.println("The volume of the cylinder is " + String.format("%.3f", cylinder.getVolume()));
    }

    // creates a Torus object and prints the object volume
    private static void createTorus() {
        double radius;
        double bigRadius;
        System.out.println("You have chosen to create a torus.");

        System.out.println("What is the radius of the cross-section?");
        while(true) {
            try {
                radius = scnr.nextDouble();
                if (radius < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        System.out.println("What is the radius of the whole torus? (center to outer edge)");
        while(true) {
            try {
                bigRadius = scnr.nextDouble();
                if (bigRadius < 0) {
                    System.out.println("Enter a positive numerical value.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Enter a valid numerical value.");
            }
        }

        Torus torus = new Torus(radius, bigRadius);
        System.out.println("The volume of the torus is " + String.format("%.3f", torus.getVolume()));
    }

    // main method
    /*
    public static void main(String[] args) {
        // flags to exit program
        int c = 0;
        boolean cont = true;

        System.out.println("*********Welcome to the Java OO Shapes Program **********");
        while (c != 10 && cont) {
            mainMenu();
            try {
                c = scnr.nextInt();
                cont = selection(c);
            } catch (InputMismatchException e) {
                scnr.next();
                System.out.println("Please enter a valid option.");
            }
        }
    }

     */
}
