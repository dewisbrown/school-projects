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
 * Testing for different methods in Parser.java and GUI.java.
 */

package org.codecrusaders;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * Unit test for methods in Parser.java and GUI.java.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) {
        super( testName );
    }

    /**
     * Uses Parser object to run PowerShell script. Checks for exit code.
     * @throws IOException
     */
    public void testScriptExitCode() throws IOException {
        // Get input stream of PowerShell script from resources folder
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("TestEventLog.evtx");

        // Create a temporary file to hold the script
        Path tempEventFile = Files.createTempFile("temp", ".evtx");

        // Copy the contents of the script from the input stream to the temp file
        assert inputStream != null;
        Files.copy(inputStream, tempEventFile, StandardCopyOption.REPLACE_EXISTING);

        // Get the path of the temp script file
        String inputPath = tempEventFile.toString();
        Parser parser = new Parser(inputPath, true);

        assertEquals(parser.getExitCode(), 0);
    }

    /**
     * Checks for csv folder in project directory.
     */
    public void testCsvFolderExists() {
        Path folderPath = Paths.get("Capstone Project").toAbsolutePath().resolveSibling("testCsvs");
        System.out.println("CSV folder exists: " + folderPath.toFile().exists());
        assertTrue(folderPath.toFile().exists());
    }

    /**
     * Checks that csv folder is a valid directory.
     */
    public void testCsvFolderIsDirectory() {
        Path folderPath = Paths.get("Capstone Project").toAbsolutePath().resolveSibling("testCsvs");
        System.out.println("CSV folder is a directory: " + folderPath.toFile().isDirectory());
        assertTrue(folderPath.toFile().isDirectory());
    }

    /**
     * Checks that the contents of the csv folder are csv files.
     */
    public void testCsvFolderContent() {
        Path folderPath = Paths.get("Capstone Project").toAbsolutePath().resolveSibling("testCsvs");
        File csvFolder = folderPath.toFile();

        System.out.println("Files within CSV folder:");
        for (File file : Objects.requireNonNull(csvFolder.listFiles())) {
            System.out.println(file.getName());
            assertTrue(file.getName().endsWith(".csv"));
        }
    }

    /**
     * Creates instance of GUI and checks that the window is displayed.
     */
    public void testGuiShowing() {
        GUI gui = new GUI();
        assertTrue(gui.isShowing());
        gui.dispose();
    }
}
