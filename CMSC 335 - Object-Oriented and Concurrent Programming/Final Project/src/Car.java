/*
 * Author: Luis Moreno
 * Date: December 11, 2022
 *
 * Project 3 - Traffic Light Simulator
 *
 * The Car class is dependent on TrafficLight objects.
 *
 * Each car object is synchronized with all three
 * traffic light objects that are created when the program
 * is run. This allows the cars to stop and go depending
 * on the light color of each traffic light and its position.
 *
 * Traffic light positions are documented in the TrafficApp
 * header comment block.
 *
 * The ability to add more lights to synchronize was not implemented
 * upon the submission of this project, but ideas about this
 * implementation are noted below the "ADD LIGHT" event handler
 * in the TrafficApp class.
 *
 * Methods exist to change the speed and position of the cars, but
 * there was not a need to use them for this project. These fields
 * are updated from the GUI when the cars are requested to stop or go.
 * ("PAUSE", "RESUME", and "STOP" buttons)
 */
public class Car implements Runnable {
    private double speed;
    private final double ogSpeed; // used to save the initial speed
    private double position;
    private TrafficApp app;
    private TrafficLight light1;
    private TrafficLight light2;
    private TrafficLight light3;
    private boolean stopped = false;
    private boolean isRunning;

    public Car(int speed, int position, TrafficLight light1, TrafficLight light2, TrafficLight light3, TrafficApp app) {
        this.speed = speed;
        this.position = position;
        this.light1 = light1;
        this.light2 = light2;
        this.light3 = light3;
        this.app = app;
        ogSpeed = speed;
        isRunning = true;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // used when printing speed in the GUI
    public String getStringSpeed() {
        return speed * 3.6 + " km/h";
    }

    // runs when "PAUSE" is clicked in the GUI
    public void stop() {
        isRunning = false;
        speed = 0;
    }

    // runs when "RESUME" is clicked in the GUI
    // also when a light changes from red to green
    public void go() {
        isRunning = true;
        this.speed = ogSpeed;
    }

    // runs when "STOP" is clicked in the GUI
    public void cancel() {
        stopped = true;
        stop();
        position = 0;
    }

    @Override
    public void run() {
        // resets flags in case simulation is stopped
        // and started within same program session
        stopped = false;
        isRunning = true;

        while (!stopped) {
            synchronized (light1) {
                while (!light1.isRunning()) {
                    try {
                        light1.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (position == 1000) {
                    while (light1.isRed()) {
                        try {
                            stop();
                            app.updateCarStatus(this);
                            light1.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    go(); // car continues once light returns to GREEN
                }
            }
            if (position == 2000) {
                synchronized (light2) {
                    while (light2.isRed()) {
                        try {
                            stop();
                            app.updateCarStatus(this);
                            light2.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    go(); // car continues once light returns to GREEN
                }
            }
            if (position == 3000) {
                synchronized (light3) {
                    while (light3.isRed()) {
                        try {
                            stop();
                            app.updateCarStatus(this);
                            light3.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    go(); // car continues once light returns to GREEN
                }
            }
            if (isRunning)
                go();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // car speed and position is updated every second in the GUI
            position += speed;
            app.updateCarStatus(this);

        }
    }
}

