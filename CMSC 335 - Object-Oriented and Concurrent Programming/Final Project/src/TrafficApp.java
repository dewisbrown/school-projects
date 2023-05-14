/*
 * Author: Luis Moreno
 * Date: December 11, 2022
 *
 * Project 3 - Traffic Light Simulator
 *
 * This project uses threads to run and monitor
 * three traffic lights and three cars driving
 * on the road where these traffic lights are positioned.
 *
 * All of these components are viewed through a GUI that is
 * created when the program is run. Clicking "START" begins
 * the simulation.
 *
 * The cars travel in a single direction, while the traffic
 * lights are positioned at different intervals along this
 * straight line.
 *
 * Traffic light positions:
 *      light1: 1000.
 *      light2: 2000.
 *      light3: 3000.
 *
 * The start of the simulation places each car at 0.
 * Car starting speeds:
 *      car1: 10
 *      car2: 50
 *      car3: 20
 *
 * The GUI displays the car speed and position, which
 * updates every second. The speed is displayed as km/hr,
 * although the speed is m/s. Every second, the position
 * is incremented by its speed value.
 *
 * When a car reaches a light position (1000, 2000, 3000)
 * and the light is "RED", the car will display a speed of 0,
 * and it's position will not update. Once the light changes
 * to "GREEN", the car will update its speed and position.
 *
 * The GUI allows the user to pause, resume, and stop the simulation
 * at any point. If the simulation is stopped, the user can
 * restart the simulation using the "START" button.
 *
 * Additional buttons include:
 *      "ADD LIGHT": The GUI will update and show a new label
 *                   and text field for a traffic light.
 *      "ADD CAR": The GUI will update and show a new label and
 *                 text field for a car.
 *
 * Although those additional buttons update the GUI to show
 * new lights and cars, there is no new working light and car
 * threads that update and display in those fields. Some thoughts
 * on how to implement this are commented above the "ADD LIGHT" and
 * "ADD CAR" buttons.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class TrafficApp extends JFrame implements ActionListener, Runnable {
    Thread lightThread1;
    Thread lightThread2;
    Thread lightThread3;

    Thread carThread1;
    Thread carThread2;
    Thread carThread3;

    TrafficLight tl1;
    TrafficLight tl2;
    TrafficLight tl3;

    Car c1;
    Car c2;
    Car c3;

    JTextField lightStatus1;
    JTextField lightStatus2;
    JTextField lightStatus3;
    JTextField carStatus1;
    JTextField carStatus2;
    JTextField carStatus3;
    JTextField timeStamps;

    JPanel carPanel;
    JPanel lightPanel;

    ArrayList<JLabel> carLabelList;
    ArrayList<JLabel> lightLabelList;
    ArrayList<JTextField> carStatusList;
    ArrayList<JTextField> lightStatusList;
    ArrayList<TrafficLight> trafficLightList;
    ArrayList<Car> carList;

    Thread appThread;
    volatile boolean paused = false;
    boolean startClicked = false;
    boolean stopClicked = false;

    // flags for adding new light and car components
    int lightNum;
    int carNum;
    int lightGridY = 4;
    int carGridY = 4;
    int carStatusIndex = 3;
    int lightStatusIndex = 3;
    int carLabelIndex = 3;
    int lightLabelIndex = 3;

    public static void main(String[] args) {
        TrafficApp app = new TrafficApp();
        app.createAndShowGUI();
    }

    public void createAndShowGUI() {
        setTitle("Traffic Simulator");

        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // buttons and button action listeners
        JButton start = new JButton("START");
        JButton pause = new JButton("PAUSE");
        JButton stop = new JButton("STOP");
        JButton resume = new JButton("RESUME");
        JButton addCar = new JButton("ADD CAR");
        JButton addLight = new JButton("ADD LIGHT");

        start.addActionListener(this);
        pause.addActionListener(this);
        stop.addActionListener(this);
        resume.addActionListener(this);
        addCar.addActionListener(this);
        addLight.addActionListener(this);

        // initialize light and car lists
        lightLabelList = new ArrayList<>();
        lightStatusList = new ArrayList<>();
        carLabelList = new ArrayList<>();
        carStatusList = new ArrayList<>();
        trafficLightList = new ArrayList<>();
        carList = new ArrayList<>();

        // panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        JPanel addButtonsPanel = new JPanel();
        addButtonsPanel.setLayout(new GridBagLayout());

        // panel for traffic light labels and text fields
        lightPanel = new JPanel();
        lightPanel.setLayout(new GridBagLayout());

        // panel for car information
        carPanel = new JPanel();
        carPanel.setLayout(new GridBagLayout());

        // panel for time stamps
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());

        // traffic light labels and text fields
        lightNum = 1;
        JLabel lightLabel1 = new JLabel(String.format("Traffic light %s:", lightNum++));
        JLabel lightLabel2 = new JLabel(String.format("Traffic light %s:", lightNum++));
        JLabel lightLabel3 = new JLabel(String.format("Traffic light %s:", lightNum++));

        lightStatus1 = new JTextField();
        lightStatus2 = new JTextField();
        lightStatus3 = new JTextField();

        lightStatus1.setEditable(false);
        lightStatus2.setEditable(false);
        lightStatus3.setEditable(false);

        lightLabelList.add(lightLabel1);
        lightLabelList.add(lightLabel2);
        lightLabelList.add(lightLabel3);

        lightStatusList.add(lightStatus1);
        lightStatusList.add(lightStatus2);
        lightStatusList.add(lightStatus3);

        // car labels and text fields
        carNum = 1;
        JLabel car1 = new JLabel(String.format("Car %s:", carNum++));
        JLabel car2 = new JLabel(String.format("Car %s:", carNum++));
        JLabel car3 = new JLabel(String.format("Car %s:", carNum++));

        carStatus1 = new JTextField();
        carStatus2 = new JTextField();
        carStatus3 = new JTextField();

        carStatus1.setEditable(false);
        carStatus2.setEditable(false);
        carStatus3.setEditable(false);

        carLabelList.add(car1);
        carLabelList.add(car2);
        carLabelList.add(car3);

        carStatusList.add(carStatus1);
        carStatusList.add(carStatus2);
        carStatusList.add(carStatus3);

        // time stamp label and text field
        JLabel timeLabel = new JLabel("Time:");
        timeStamps = new JTextField();
        timeStamps.setEditable(false);

        // add light labels and text fields to light panel
        // add car labels and text fields to car panel
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 0;
        lightPanel.add(lightLabel1, gc);
        carPanel.add(car1, gc);

        gc.gridy++;
        lightPanel.add(lightLabel2, gc);
        carPanel.add(car2, gc);

        gc.gridy++;
        lightPanel.add(lightLabel3, gc);
        carPanel.add(car3, gc);

        gc.gridx++;
        gc.gridy = 0;
        gc.ipadx = 100;
        lightPanel.add(lightStatus1, gc);
        gc.ipadx = 250;
        carPanel.add(carStatus1, gc);

        gc.gridy++;
        gc.ipadx = 100;
        lightPanel.add(lightStatus2, gc);
        gc.ipadx = 250;
        carPanel.add(carStatus2, gc);

        gc.gridy++;
        gc.ipadx = 100;
        lightPanel.add(lightStatus3, gc);
        gc.ipadx = 250;
        carPanel.add(carStatus3, gc);

        // add buttons to button panels
        gc.gridx = 0;
        gc.gridy = 0;
        gc.ipadx = 0;
        buttonPanel.add(start, gc);
        addButtonsPanel.add(addCar, gc);

        gc.gridy++;
        buttonPanel.add(pause, gc);
        addButtonsPanel.add(addLight, gc);

        gc.gridy++;
        buttonPanel.add(resume, gc);

        gc.gridy++;
        buttonPanel.add(stop, gc);

        // add time stamp label and text field to time panel
        gc.gridx = 0;
        gc.gridy = 0;
        timePanel.add(timeLabel, gc);

        gc.gridx++;
        gc.weightx = 1;
        timePanel.add(timeStamps, gc);

        // add panels to main panel
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(10, 10, 10, 10);
        main.add(lightPanel, gc);

        gc.gridx++;
        main.add(carPanel, gc);

        gc.gridx++;
        main.add(buttonPanel, gc);

        gc.gridy++;
        main.add(addButtonsPanel, gc);

        gc.gridy = 1;
        gc.gridx = 0;
        gc.gridwidth = 2;
        main.add(timePanel, gc);

        // configure frame and display
        add(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        tl1 = new TrafficLight(TrafficSignals.RED, lightStatus1, this);
        tl2 = new TrafficLight(TrafficSignals.YELLOW, lightStatus2, this);
        tl3 = new TrafficLight(TrafficSignals.GREEN, lightStatus3, this);

        trafficLightList.add(tl1);
        trafficLightList.add(tl2);
        trafficLightList.add(tl3);

        c1 = new Car(10, 0, tl1, tl2, tl3, this);
        c2 = new Car(50, 0, tl1, tl2, tl3, this);
        c3 = new Car(20, 0, tl1, tl2, tl3, this);

        carList.add(c1);
        carList.add(c2);
        carList.add(c3);

        appThread = new Thread(this);
        appThread.start();
    }

    public void startApp() {
        /* Creates and starts traffic light object threads and
         * car object threads.
         *
         * This can be updated to loop through their respective
         * arraylists and started within those loops. The first
         * three lights and cars can be instantiated and added
         * to their respective lists in the createAndShowGUI() method.
         */

        lightThread1 = new Thread(tl1);
        lightThread2 = new Thread(tl2);
        lightThread3 = new Thread(tl3);

        carThread1 = new Thread(c1);
        carThread2 = new Thread(c2);
        carThread3 = new Thread(c3);

        carThread1.start();
        carThread2.start();
        carThread3.start();

        lightThread1.start();
        lightThread2.start();
        lightThread3.start();
    }

    public void updateLightStatus(TrafficLight tl) {

        // loop through light list to find matching traffic light
        int i = 0;
        for (TrafficLight value : trafficLightList) {
            if (tl == value)
                break;
            i++;
        }

        // int "i" is used as an index flag to get both the light status from the
        // light text field list and traffic light object from the traffic light list
        lightStatusList.get(i).setText(trafficLightList.get(i).getLightColor().toString());
    }

    public void updateCarStatus(Car car) {
        int i = 0;
        for (Car value : carList) {
            if (car == value)
                break;
            i++;
        }

        Car aCar = carList.get(i);

        if (aCar.getPosition() < 4000) {
            carStatusList.get(i).setText("Position: " + aCar.getPosition() + "m, Speed: " + aCar.getStringSpeed());
        } else {
            carStatusList.get(i).setText("The car has left this road.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("START")) {
            // paused = false;
            if (!startClicked) {
                startClicked = true;
                stopClicked = false;
                startApp();
            }
        }

        /* The pause, resume, and stop buttons can be updated to loop
         * through the traffic light list and car list. As of now, I have
         * not been able to implement the addition of more car and light threads.
         */
        if (e.getActionCommand().equals("PAUSE")) {
            // paused = true;   I could not figure out how to pause the timer that runs in the TrafficApp run() method.
            if (startClicked) {
                tl1.pause();
                tl2.pause();
                tl3.pause();

                c1.stop();
                c2.stop();
                c3.stop();

            } else if (stopClicked) {
                JOptionPane.showMessageDialog(null, "Restart the program first!");
            } else {
                JOptionPane.showMessageDialog(null, "Start the simulator first!");
            }
        }
        if (e.getActionCommand().equals("RESUME")) {
            if (startClicked) {
                tl1.resume();
                tl2.resume();
                tl3.resume();

                c1.go();
                c2.go();
                c3.go();
            } else if (stopClicked) {
                JOptionPane.showMessageDialog(null, "Restart the program first!");
            } else {
                JOptionPane.showMessageDialog(null, "Start the simulator first!");
            }
        }
        if (e.getActionCommand().equals("STOP")) {
            if (startClicked) {
                tl1.cancel();
                tl2.cancel();
                tl3.cancel();

                c1.cancel();
                c2.cancel();
                c3.cancel();

                startClicked = false;
                stopClicked = true;
            } else {
                JOptionPane.showMessageDialog(null, "Start the simulator first!");
            }
        }
        if (e.getActionCommand().equals("ADD CAR")) {
            /* Uses an array list of JLabels and JTextFields
             * to add to the car panel. It is possible to add
             * a new car object and start a new thread.
             *
             * However, it would require a reasonable rewrite
             * of the whole Car class and its run() method.
             *
             * As of now, the Car object constructor includes
             * the initial three traffic lights in order for
             * synchronization to occur. The way I would try to
             * implement more lights would be to pass the arraylist
             * of lights. However, this would mean the first three
             * car objects only have the first three lights synchronized.
             * An updateLights() method in the Car class could be added
             * to include new lights as they are added.
             */
            GridBagConstraints gc = new GridBagConstraints();

            JTextField newCarStatus = new JTextField();
            newCarStatus.setEditable(false);

            carStatusList.add(newCarStatus);

            JLabel newCarLabel = new JLabel(String.format("Car %d:", carNum++));
            carLabelList.add(newCarLabel);

            gc.gridy = carGridY++;
            gc.gridx = 0;
            carPanel.add(carLabelList.get(carLabelIndex++), gc);

            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.gridx++;
            carPanel.add(carStatusList.get(carStatusIndex++), gc);

            carPanel.revalidate();
            carPanel.repaint();

            pack();
        }
        if (e.getActionCommand().equals("ADD LIGHT")) {
            GridBagConstraints gc = new GridBagConstraints();

            JTextField newLightStatus = new JTextField();
            newLightStatus.setEditable(false);

            JLabel newLightLabel = new JLabel(String.format("Traffic light %d:", lightNum++));

            lightStatusList.add(newLightStatus);
            lightLabelList.add(newLightLabel);

            gc.gridy = lightGridY++;
            gc.gridx = 0;
            lightPanel.add(lightLabelList.get(lightLabelIndex++), gc);

            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.gridx++;
            gc.ipadx = 100;
            lightPanel.add(lightStatusList.get(lightStatusIndex++), gc);

            lightPanel.revalidate();
            lightPanel.repaint();

            pack();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                if (paused) {
                    TrafficApp.this.wait();
                } else {
                    timeStamps.setText(String.format("%s", new Date()));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}