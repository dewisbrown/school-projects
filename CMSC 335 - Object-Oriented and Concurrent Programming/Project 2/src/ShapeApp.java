/*
 * Author: Luis Moreno
 * Date: November 15, 2022
 *
 * Project 2 - Shapes GUI
 *
 * This GUI-driven program calculates the area or volume of a user-created shape.
 * The user chooses a shape to create from a drop-down menu and inputs its dimensions.
 *
 * A shape object is then created and used to
 * display its area or volume along with an image of the object.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShapeApp extends JFrame {

    // creates GUI components and handles action events
    public ShapeApp() {
        super("Shape App");
        JPanel main = new JPanel();
        String[] shapes = {"--- Shapes List ---", "Circle", "Rectangle", "Square", "Triangle",
                "Cube", "Cone", "Cylinder", "Sphere", "Torus"};

        // set layout
        main.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // labels
        JLabel label = new JLabel("Select a shape from the drop down menu.");
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.ipady = 20;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(10, 10, 5, 10);
        main.add(label, gc);

        JLabel labelBottom = new JLabel("Click the \"Create Shape\" button " +
                "to input dimensions and display the shape.");
        gc.gridy = 1;
        gc.ipady = 0;
        gc.gridwidth = 2;
        gc.insets = new Insets(5, 10, 0, 10);
        main.add(labelBottom, gc);

        // blank label for image icons to be updated in imgPanel
        JLabel imgLabel = new JLabel();

        // combo box
        JComboBox<String> shapeList = new JComboBox<>(shapes);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.ipadx = 20;
        gc.ipady = 10;
        gc.gridwidth = 1;
        gc.insets = new Insets(20, 10, 0, 10);
        main.add(shapeList, gc);

        // button
        JButton createShape = new JButton("Create Shape");
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 1;
        gc.gridy = 2;
        main.add(createShape, gc);

        // text area
        JTextField outputField = new JTextField("... shape data will display here ...");
        outputField.setEditable(false);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 2;
        gc.insets = new Insets(10, 10, 10, 10);
        main.add(outputField, gc);

        // panel to display image or drawing
        JPanel imgPanel = new JPanel();
        gc.gridx = 0;
        gc.gridy = 4;
        gc.insets = new Insets(10, 10, 30, 10);
        main.add(imgPanel, gc);
        imgPanel.add(imgLabel);

        // create shape button creates new window
        createShape.addActionListener((ActionEvent e) -> {
            String theShape = (String) shapeList.getSelectedItem();

            NewWindow w = new NewWindow();
            w.frame.setTitle(theShape);

            if (theShape.equals("Circle") || theShape.equals("Sphere")) {
                w.setCircle();
                w.display();

                // secondary button event handler
                w.button.addActionListener((ActionEvent ab) -> {
                    try {
                        double radius = Math.abs(Double.parseDouble(w.firstText.getText()));
                        BufferedImage img = null;
                        if (theShape.equals("Circle")) {
                            img = ImageIO.read(new File("shapePics/circle.png"));
                            Image scaleImg = img.getScaledInstance(160, 160, Image.SCALE_DEFAULT);

                            Circle circle = new Circle(radius);

                            outputField.setText(circle.toString());
                            imgLabel.setIcon(new ImageIcon(scaleImg));
                            w.frame.dispose();
                        }
                        if (theShape.equals("Sphere")) {
                            img = ImageIO.read(new File("shapePics/sphere.png"));
                            Image scaleImg = img.getScaledInstance(160, 160, Image.SCALE_DEFAULT);

                            Sphere sphere = new Sphere(radius);

                            outputField.setText(sphere.toString());
                            imgLabel.setIcon(new ImageIcon(scaleImg));
                            w.frame.dispose();
                        }
                    } catch (NumberFormatException nfe) {
                        error("Invalid input.");
                    } catch (IOException ioe) {
                        error("Image failed to load.");
                    }
                });
            }

            if (theShape.equals("Rectangle")) {
                w.setRectangle();
                w.display();

                // secondary button event handler
                w.button.addActionListener((ActionEvent ab) -> {
                    BufferedImage img;
                    try {
                        double length = Math.abs(Double.parseDouble(w.firstText.getText()));
                        double width = Math.abs(Double.parseDouble(w.secondText.getText()));
                        img = ImageIO.read(new File("shapePics/rectangle.png"));
                        Image scaleImg = img.getScaledInstance(160, 160, Image.SCALE_DEFAULT);

                        Rectangle rectangle = new Rectangle(length, width);

                        outputField.setText(rectangle.toString());
                        imgLabel.setIcon(new ImageIcon(scaleImg));
                        w.frame.dispose();
                    } catch (NumberFormatException nfe) {
                        error("Invalid input.");
                    } catch (IOException ioe) {
                        error("Image failed to load.");
                    }
                });
            }

            if (theShape.equals("Triangle")) {
                w.setTriangle();
                w.display();

                // secondary button event handler
                w.button.addActionListener((ActionEvent ab) -> {
                    BufferedImage img;
                    try {
                        double base = Math.abs(Double.parseDouble(w.firstText.getText()));
                        double height = Math.abs(Double.parseDouble(w.secondText.getText()));
                        img = ImageIO.read(new File("shapePics/triangle.png"));
                        Image scaleImg = img.getScaledInstance(160, 160, Image.SCALE_DEFAULT);

                        Triangle triangle = new Triangle(base, height);

                        outputField.setText(triangle.toString());
                        imgLabel.setIcon(new ImageIcon(scaleImg));
                        w.frame.dispose();
                    } catch (NumberFormatException nfe) {
                        error("Invalid input.");
                    } catch (IOException ioe) {
                        error("Image failed to load.");
                    }
                });
            }

            if (theShape.equals("Square") || theShape.equals("Cube")) {
                w.setSquare();
                w.display();

                // secondary button event handler
                w.button.addActionListener((ActionEvent ab) -> {
                    BufferedImage img;
                    try {
                        double length = Double.parseDouble(w.firstText.getText());
                        if (theShape.equals("Cube")) {
                            Cube cube = new Cube(length);
                            img = ImageIO.read(new File("shapePics/cube.png"));
                            outputField.setText(cube.toString());
                        } else {
                            Square square = new Square(length);
                            img = ImageIO.read(new File("shapePics/square.png"));
                            outputField.setText(square.toString());
                        }
                        Image scaleImg = img.getScaledInstance(160, 160, Image.SCALE_DEFAULT);
                        imgLabel.setIcon(new ImageIcon(scaleImg));
                        w.frame.dispose();
                    } catch (IOException ioe) {
                        error("Image failed to load.");
                    } catch (NumberFormatException nfe) {
                        error("Invalid input.");
                    }
                });
            }

            if (theShape.equals("Cylinder") || theShape.equals("Cone")) {
                w.setCylinder();
                w.display();

                // secondary button event handler
                w.button.addActionListener(ab -> {
                    BufferedImage img;
                    try {
                        double radius = Double.parseDouble(w.firstText.getText());
                        double height = Double.parseDouble(w.secondText.getText());

                        if (theShape.equals("Cylinder")) {
                            Cylinder cylinder = new Cylinder(radius, height);
                            img = ImageIO.read(new File("shapePics/cylinder.png"));
                            outputField.setText(cylinder.toString());
                        } else {
                            Cone cone = new Cone(radius, height);
                            img = ImageIO.read(new File("shapePics/cone.png"));
                            outputField.setText(cone.toString());
                        }

                        Image scaledImg = img.getScaledInstance(160, 160, Image.SCALE_DEFAULT);
                        imgLabel.setIcon(new ImageIcon(scaledImg));
                        w.frame.dispose();
                    } catch (IOException ioe) {
                        error("Image failed to load.");
                    } catch (NumberFormatException nfe) {
                        error("Invalid input.");
                    }
                });
            }

            if (theShape.equals("Torus")) {
                w.setTorus();
                w.display();

                // secondary button event handler
                w.button.addActionListener(ab -> {
                    BufferedImage img;
                    try {
                        double radius = Double.parseDouble(w.firstText.getText());
                        double bigRadius = Double.parseDouble(w.secondText.getText());
                        Torus torus = new Torus(radius, bigRadius);
                        img = ImageIO.read(new File("shapePics/torus.png"));
                        Image scaledImg = img.getScaledInstance(160, 160, Image.SCALE_DEFAULT);

                        outputField.setText(torus.toString());
                        imgLabel.setIcon(new ImageIcon(scaledImg));
                        w.frame.dispose();
                    } catch (IOException ioe) {
                        error("Image failed to load.");
                    } catch (NumberFormatException nfe) {
                        error("Invalid input.");
                    }
                });
            }
        });

        // JFrame settings
        add(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // pop-up window that displays a given message
    public void error(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void main(String[] args) {
        new ShapeApp();
    }
}

// class to create and display secondary window for shape creation
class NewWindow extends JFrame {
    JFrame frame;
    JPanel panel;

    JButton button;

    JLabel prompt;
    JLabel firstLabel;
    JLabel secondLabel;

    JTextField firstText;
    JTextField secondText;

    GridBagConstraints con;

    NewWindow() {
        frame = new JFrame();
        panel = new JPanel();

        // button
        button = new JButton("Create shape");

        // labels
        prompt = new JLabel();
        firstLabel = new JLabel();
        secondLabel = new JLabel();

        // text areas
        firstText = new JTextField();
        secondText = new JTextField();

        // set layout
        panel.setLayout(new GridBagLayout());
        con = new GridBagConstraints();
    }

    // display NewWindow frame
    public void display() {
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // used for circle and sphere
    public void setCircle() {
        firstLabel.setText("Radius:");
        con.gridx = 0;
        con.gridy = 1;
        con.gridwidth = 1;
        con.insets = new Insets(20, 20, 20, 20);
        panel.add(firstLabel, con);

        con.gridx = 1;
        con.gridy = 1;
        con.ipadx = 80;
        con.weightx = 1;
        panel.add(firstText, con);

        con.gridx = 0;
        con.gridy = 2;
        con.ipadx = 0;
        con.weightx = 0;
        con.gridwidth = 2;
        panel.add(button, con);
    }

    // used for rectangle
    public void setRectangle() {
        firstLabel.setText("Length:");
        con.gridx = 0;
        con.gridy = 1;
        con.gridwidth = 1;
        con.insets = new Insets(20, 20, 20, 20);
        panel.add(firstLabel, con);

        con.gridx = 1;
        con.gridy = 1;
        con.ipadx = 80;
        con.weightx = 1;
        panel.add(firstText, con);

        secondLabel.setText("Width:");
        con.gridx = 0;
        con.gridy = 2;
        con.ipadx = 0;
        con.weightx = 0;
        panel.add(secondLabel, con);

        con.gridx = 1;
        con.gridy = 2;
        con.ipadx = 80;
        panel.add(secondText, con);

        con.gridx = 0;
        con.gridy = 3;
        con.gridwidth = 2;
        con.ipadx = 0;
        panel.add(button, con);
    }

    // used for triangle
    public void setTriangle() {
        firstLabel.setText("Base:");
        con.gridx = 0;
        con.gridy = 1;
        con.gridwidth = 1;
        con.insets = new Insets(20, 20, 20, 20);
        panel.add(firstLabel, con);

        con.gridx = 1;
        con.gridy = 1;
        con.ipadx = 80;
        con.weightx = 1;
        panel.add(firstText, con);

        secondLabel.setText("Height:");
        con.gridx = 0;
        con.gridy = 2;
        con.ipadx = 0;
        con.weightx = 0;
        panel.add(secondLabel, con);

        con.gridx = 1;
        con.gridy = 2;
        con.ipadx = 80;
        panel.add(secondText, con);

        con.gridx = 0;
        con.gridy = 3;
        con.gridwidth = 2;
        con.ipadx = 0;
        panel.add(button, con);
    }

    // used for square and cube
    public void setSquare() {
        firstLabel.setText("Length of side:");
        con.gridx = 0;
        con.gridy = 1;
        con.gridwidth = 1;
        con.insets = new Insets(20, 20, 20, 20);
        panel.add(firstLabel, con);

        con.gridx = 1;
        con.gridy = 1;
        con.ipadx = 80;
        con.weightx = 1;
        panel.add(firstText, con);

        con.gridx = 0;
        con.gridy = 2;
        con.ipadx = 0;
        con.weightx = 0;
        con.gridwidth = 2;
        panel.add(button, con);
    }

    // used for cylinder and cone
    public void setCylinder() {
        firstLabel.setText("Radius:");
        con.gridx = 0;
        con.gridy = 1;
        con.gridwidth = 1;
        con.insets = new Insets(20, 20, 20, 20);
        panel.add(firstLabel, con);

        con.gridx = 1;
        con.gridy = 1;
        con.ipadx = 80;
        con.weightx = 1;
        panel.add(firstText, con);

        secondLabel.setText("Height:");
        con.gridx = 0;
        con.gridy = 2;
        con.ipadx = 0;
        con.weightx = 0;
        panel.add(secondLabel, con);

        con.gridx = 1;
        con.gridy = 2;
        con.ipadx = 80;
        panel.add(secondText, con);

        con.gridx = 0;
        con.gridy = 3;
        con.gridwidth = 2;
        con.ipadx = 0;
        panel.add(button, con);
    }

    public void setTorus() {
        firstLabel.setText("Cross-section radius:");
        con.gridx = 0;
        con.gridy = 1;
        con.gridwidth = 1;
        con.insets = new Insets(20, 20, 20, 20);
        panel.add(firstLabel, con);

        con.gridx = 1;
        con.gridy = 1;
        con.ipadx = 80;
        con.weightx = 1;
        panel.add(firstText, con);

        secondLabel.setText("Total radius:");
        con.gridx = 0;
        con.gridy = 2;
        con.ipadx = 0;
        con.weightx = 0;
        panel.add(secondLabel, con);

        con.gridx = 1;
        con.gridy = 2;
        con.ipadx = 80;
        panel.add(secondText, con);

        con.gridx = 0;
        con.gridy = 3;
        con.gridwidth = 2;
        con.ipadx = 0;
        panel.add(button, con);
    }
}
