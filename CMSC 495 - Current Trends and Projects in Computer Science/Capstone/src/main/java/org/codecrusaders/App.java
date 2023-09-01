/* Code Crusaders - Capstone Project
 *
 * Windows Security Event Log Parser
 *
 * University of Maryland Global Campus
 * CMSC 495: Current Trends and Projects in Computer Science
 * Professor Nevarez
 * July 11, 2023
 *
 * Development team:
 * Patrick Bell         - Project Lead, Programmer Back-End
 * Shallon Clippinger   - Technical Writer, Quality Assurance
 * Casey Lednum         - Documentation
 * Luis Moreno          - Programmer Front-End
 *
 * App.java creates an instance of GUI to display a window for the program.
 */

package org.codecrusaders;

import javax.swing.*;

/**
 * App instantiates the GUI class.
 */
public class App {
    public static void main( String[] args ) {
       SwingUtilities.invokeLater(GUI::new);
    }
}
