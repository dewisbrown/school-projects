/**
 *
 * @author Luis Moreno
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.*;

public class GUI implements ActionListener {
    // Timer used to cycle through animation frames
    private Timer animationTimer;
    // used to determine animation frame
    private int frameNumber;

    // flags for buttons
    private boolean started;
    private boolean paused;

    // panels where shape will be painted
    private JPanel shapeOne;
    private JPanel shapeTwo;
    private JPanel shapeThree;

    // global variables for transforming images
    private static int translateX = 0;
    private static int translateY = 0;
    private static double rotation = 0.0;
    private static double scaleX = 1.0;
    private static double scaleY = 1.0;

    // ImageTemplate allows different shapes to be drawn/displayed
    ImageTemplate myImages = new ImageTemplate();

    BufferedImage firstImage = myImages.getImage(ImageTemplate.firstShape);
    BufferedImage secondImage = myImages.getImage(ImageTemplate.secondtShape);
    BufferedImage thirdImage = myImages.getImage(ImageTemplate.thirdShape);

    public GUI() {
        frameNumber = 0;
        createAndShowGUI();
        animationTimer = new Timer(1600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frameNumber > 3) {
                    frameNumber = 0;
                } else {
                    frameNumber++;
                }
                shapeOne.repaint();
                shapeTwo.repaint();
                shapeThree.repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // turn on antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // make background white for all panels
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, shapeOne.getWidth(), shapeOne.getHeight());
        g2.fillRect(0, 0, shapeTwo.getWidth(), shapeTwo.getHeight());
        g2.fillRect(0, 0, shapeThree.getWidth(), shapeThree.getHeight());

        AffineTransform savedTransform = g2.getTransform();

        System.out.printf("Frame is %d\n", frameNumber);
        switch (frameNumber) {
            case 1 -> {
                translateX = 0;
                translateY = 0;
                scaleX = 1.0;
                scaleY = 1.0;
                rotation = 0;
            }
            case 2 -> {
                translateX = -9;
                translateY = 5;
            }
            case 3 -> {
                translateX = -9;
                translateY = 5;
                rotation = 60 * Math.PI / 180.0;
            }
            default -> {
            }
        }

        g2.translate(translateX, translateY);    // move image
        g2.translate(-10, -10);            // offset translate again
        g2.rotate(rotation);                    // rotate image
        g2.scale(scaleX, scaleY);               // scale image

        g2.drawImage(firstImage, 0, 0, (ImageObserver) this);
        g2.drawImage(secondImage, 0, 0, (ImageObserver) this);
        g2.drawImage(thirdImage, 0, 0, (ImageObserver) this);

        g2.setTransform(savedTransform);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "play" -> {
                started = true;
                animationTimer.start();
            }
            case "pause" -> {
                if (started) {
                    animationTimer.stop();
                    started = false;
                } else {
                    System.out.println("Play has not been pressed yet.");
                }
            }
            case "reset" -> {
                System.out.println("Reset clicked.");
            }
        }
    }

    private void createAndShowGUI() {
        // create main window
        JFrame main = new JFrame("Project 1");
        main.setLayout(new GridBagLayout());
        main.setMinimumSize(new Dimension(500, 300));

        // constraints used in frame layout manager
        GridBagConstraints gc = new GridBagConstraints();

        // panel to hold shape panels
        JPanel shapesPanel = new JPanel(new FlowLayout());

        // panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // create shape panels and add to shapesPanel
        shapeOne = new JPanel();
        shapeTwo = new JPanel();
        shapeThree = new JPanel();

        shapesPanel.add(shapeOne);
        shapesPanel.add(shapeTwo);
        shapesPanel.add(shapeThree);

        // create buttons, set action commands, add action listeners
        JButton play = new JButton("Play");
        play.setActionCommand("play");
        play.addActionListener(this);

        JButton pause = new JButton("Pause");
        pause.setActionCommand("pause");
        pause.addActionListener(this);

        JButton reset = new JButton("Reset");
        reset.setActionCommand("reset");
        reset.addActionListener(this);

        // add buttons to button panel
        buttonPanel.add(play);
        buttonPanel.add(pause);
        buttonPanel.add(reset);

        // add panels to main frame
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        main.add(shapesPanel, gc);

        gc.gridy++;
        gc.weighty = 0;
        gc.anchor = GridBagConstraints.PAGE_END;
        main.add(buttonPanel, gc);

        // display main frame
        main.pack();
        main.setLocationRelativeTo(null);
        main.setVisible(true);
        main.setResizable(false);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
