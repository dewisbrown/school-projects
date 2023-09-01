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
 * Parser.java runs a PowerShell script to parse a Windows security.evtx file.
 * Upon the completion of the script, counts are calculated for different events, along
 * with event descriptions.
 */

package org.codecrusaders;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.file.StandardCopyOption;

/**
 * <p>Runs a PowerShell script located in the resources directory.</p>
 * <p>Uses the parsed information to create summaries of the data.</p>
 */
public class Parser {
    private final String inputPath;
    private HashMap<String, HashMap<String, Integer>> fileEventsCountMap;
    private int exitCode;
    private boolean test;

    /**
     * Used to run PowerShell script and count events for use in GUI tables
     *
     * @param inputPath - evtx file path
     */
    public Parser(String inputPath) {
        fileEventsCountMap = new HashMap<>();

        System.out.println("Starting PowerShell process.");

        this.inputPath = inputPath;
        this.test = false;
        runScript();

        System.out.println("PowerShell process complete.");
    }

    /**
     * Constructor for testing Parser script run
     *
     * @param inputPath - evtx file path
     * @param test - stops PowerShell script from printing to console
     */
    public Parser(String inputPath, boolean test) {
        fileEventsCountMap = new HashMap<>();

        System.out.println("Starting PowerShell process.");

        this.inputPath = inputPath;
        this.test = test;
        runScript();

        System.out.println("PowerShell process complete.");
    }

    /*** GUI button descriptions/counts for events are searched from the fileEventsCountMap.
     *
     * @param filePath
     * @return ArrayList of events with count and description
     */
    public ArrayList<String> getEventCounts(String filePath) {
        ArrayList<String> eventList = new ArrayList<>();
        if (fileEventsCountMap.containsKey(filePath)) {
            HashMap<String, Integer> eventCountMap = fileEventsCountMap.get(filePath);
            for (Map.Entry<String, Integer> entry : eventCountMap.entrySet()) {
                eventList.add(String.format("[Events: %d] --Log: Security Event ID: %s - %s", entry.getValue(), entry.getKey(), getEventDescription(entry.getKey())));
            }
        }
        return eventList;
    }

    private void runScript() {
        try {
            // Get input stream of PowerShell script from resources folder
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test.ps1");

            // Create a temporary file to hold the script
            Path tempScriptFile = Files.createTempFile("temp", ".ps1");

            // Copy the contents of the script from the input stream to the temp file
            assert inputStream != null;
            Files.copy(inputStream, tempScriptFile, StandardCopyOption.REPLACE_EXISTING);

            // Get the path of the temp script file
            String scriptPath = tempScriptFile.toString();

            // Csv files will be written to folder: "eventCsvs"
            String outputPath = "eventCsvs";

            String command = "powershell.exe -ExecutionPolicy Bypass -File \"" + scriptPath +
                    "\" -inputfile \"" + inputPath + "\" -outputdirectory \"" + outputPath + "\"";

            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            Process process = pb.start();

            // Print PowerShell script output to the console when not in test mode
            if (!test) {
                // Input streams to print powershell execution to console
                InputStream processInputStream = process.getInputStream();
                InputStream errorStream = process.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(processInputStream));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Print error output of script
                while ((line = errorReader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            exitCode = process.waitFor();
            System.out.println("Script executed with exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        countEvents();
    }

    /**
     * <p>Reads data from csv files to count events from each file.</p>
     * <p>Counts are saved to fileEventsCountMap.</p>
     */
    public void countEvents() {
        // Get list of all file paths in eventCsvs folder
        ArrayList<String> filePaths = new ArrayList<>();

        File csvDirectory = new File("eventCsvs");

        if (csvDirectory.exists() && csvDirectory.isDirectory()) {
            for (File csvFile : Objects.requireNonNull(csvDirectory.listFiles())) {
                filePaths.add(csvFile.getAbsolutePath());
            }
        }

        int eventIndex = -1;

        // Count event IDs in each csv file
        for (String filePath : filePaths) {
            try {
                Reader reader = new FileReader(filePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
                HashMap<String, Integer> eventCountMap = new HashMap<>();

                for (CSVRecord record : csvParser) {
                    if (eventIndex == -1) {
                        eventIndex = findColumnIndex(record, "Event");

                        if (eventIndex == -1) {
                            System.out.printf("Column 'Event' not found in the csv file [%s]%n", filePath);
                            break;
                        }
                    } else {
                        String eventId = record.get(eventIndex);

                        if (eventCountMap.containsKey(eventId)) {       // Check if event ID is already in HashMap
                            int count = eventCountMap.get(eventId);
                            eventCountMap.put(eventId, count + 1);      // increment count
                        } else if (!eventId.equalsIgnoreCase("Event")) {
                            eventCountMap.put(eventId, 1);              // Place in HashMap
                        }
                    }
                }
                csvParser.close();

                if (!eventCountMap.isEmpty()) {
                    fileEventsCountMap.put(filePath, eventCountMap);    // add filePath and eventCountMap to fileEventsCountMap
                }
            } catch (IOException e) {
                System.out.println("Something went wrong...");
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Getter for exitCode
     *
     * @return int - exitCode
     */
    public int getExitCode() {
        return exitCode;
    }

    /*
     * Returns event description given the id
     */
    private String getEventDescription(String eventId) {
        try {
            int id = Integer.parseInt(eventId);
            return switch (id) {
                case 4624 -> "Successful login";
                case 4647 -> "User logoff initiated";
                case 4634 -> "Successful logoff";
                case 4625 -> "Failed login attempt";
                case 4698 -> "Scheduled task created";
                default -> "Unknown event id";
            };
        } catch (NumberFormatException e) {
            return "Event ID description failed to parse.";
        }
    }

    /*
     * Finds the index of "Event ID" given the first row of the csv file
     */
    private int findColumnIndex(CSVRecord record, String columnName) {
        for (int i = 0; i < record.size(); i++) {
            if (columnName.equalsIgnoreCase(record.get(i))) {
                return i;
            }
        }
        return -1;      // column is not in csv file
    }

    /*
     * prints HashMap to console
     */
    private void printMap() {
        if (!fileEventsCountMap.isEmpty()) {
            for (Map.Entry<String, HashMap<String, Integer>> fileEntry : fileEventsCountMap.entrySet()) {
                String filePath = fileEntry.getKey();
                Map<String, Integer> eventCountMap = fileEntry.getValue();

                System.out.printf("File: [%s]%n", filePath);
                for (Map.Entry<String, Integer> eventEntry : eventCountMap.entrySet()) {
                    String eventId = eventEntry.getKey();
                    int count = eventEntry.getValue();
                    System.out.printf("Event ID: %s, Count: %d%n", eventId, count);
                }

            }
        }
    }
}
