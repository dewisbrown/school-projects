/*
 * Author: Luis Moreno
 * Date: December 11, 2022
 *
 * Project 3 - Traffic Light Simulator
 *
 * This TrafficLight class is an expanded version
 * of the class used in TrafficLightDemo.java, which
 * was part of the supplied code examples this week (8).
 *
 * Each traffic light updates it's color after using
 * Thread.sleep(), with different times to simulate
 * traffic light colors:
 *      GREEN: 10
 *      YELLOW: 2
 *      RED: 12
 *
 * When the light is red, a boolean "isRed" is updated and
 * used by the Car class to stop the car if it reaches the
 * light's position. Light positions are documented in the
 * TrafficApp class header comment block.
 *
 * Methods were added to the original class to pause and resume
 * the lights from the program GUI. The GUI text fields are initialized
 * when the TrafficLight threads are started.
 */
import javax.swing.*;

public class TrafficLight implements Runnable {
    private TrafficSignals lightColor;
    private boolean stop = false;
    private boolean changed = false;
    private TrafficApp app;
    private boolean isPaused = false;
    private long pauseTime;
    private long startTime;
    private boolean isRunning = false;
    private boolean isRed;
    private TrafficSignals ogColor;

    private JTextField lightStatus;


    public TrafficLight() {
        this.lightColor = TrafficSignals.RED;
    }

    public TrafficLight(TrafficSignals init, JTextField lightStatus, TrafficApp app) {
        lightColor = init;
        this.lightStatus = lightStatus;
        this.app = app;
        pauseTime = 0;
        ogColor = init;
    }


    @Override
    public void run() {
        // resets flags and updates text fields if simulation
        // is stopped and restarted in same program session
        stop = false;
        lightColor = ogColor;
        lightStatus.setText(lightColor.toString());

        while(!stop) {
            synchronized (this) {
                startTime = System.currentTimeMillis();
                isRunning = true;
                notifyAll();
                while(isPaused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            /* used to add time to Thread.sleep()
             * without this, when "RESUME" was clicked after being paused
             * the three traffic lights would change in-sync
             */
            long elapsed = 0;
            if (pauseTime > 0) {
                elapsed = System.currentTimeMillis() - pauseTime;
            }
            try {
                switch (lightColor) {
                    case GREEN:
                        isRed = false;
                        Thread.sleep(10000 + elapsed); // green for 10 seconds
                        break;
                    case YELLOW:
                        isRed = false;
                        Thread.sleep(2000 + elapsed);  // yellow for 2 seconds
                        break;
                    case RED:
                        isRed = true;
                        Thread.sleep(12000 + elapsed); // red for 12 seconds
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            changeColor();
            app.updateLightStatus(this);
            pauseTime = 0;
        }
    }

    // runs when "PAUSE" is clicked in the GUI
    public void pause() {
        pauseTime = System.currentTimeMillis();
        isPaused = true;
    }

    // runs when "RESUME" is clicked in the GUI
    public void resume() {
        synchronized (this) {
            isPaused = false;
            notify();
        }
    }

    public long getStartTime() {
        return startTime;
    }

    // used by the Car class to synchronize processes
    public boolean isRunning() {
        return isRunning;
    }

    // used by the Car class to synchronize processes
    public boolean isRed() {
        return isRed;
    }

    synchronized void changeColor() {
        switch (lightColor) {
            case GREEN -> {
                lightColor = TrafficSignals.YELLOW;
                isRed = false;
            }
            case YELLOW -> {
                lightColor = TrafficSignals.RED;
                isRed = true;
            }
            case RED -> {
                lightColor = TrafficSignals.GREEN;
                isRed = false;
            }
        }

        changed = true;
        notify();
    }

    // unused method from original TrafficLightDemo.java
    synchronized void waitForChange() {
        try {
            while(!changed) {
                wait();
                changed = false;
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized TrafficSignals getLightColor() {
        return lightColor;
    }

    // runs when "STOP" is clicked in the GUI
    synchronized void cancel() {
        stop = true;
    }
}
