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
 * GUI.java provides an interface for a user to display
 * information from a parsed Windows security.evtx file.
 */

package org.codecrusaders;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <p>The GUI class creates a window with a menu bar. </p>
 * <p>Users can open a Windows evtx file using the menu bar.</p>
 * <p>It utilizes a Parser object to execute a PowerShell script that parses the evtx file.</p>
 * <p>The PowerShell script generates csv files, which the GUI parses and displays in JTables.</p>
 * <p>After the PowerShell script finishes, the GUI shows clickable panels with brief descriptions of the csv files.</p>
 */
public class GUI extends JFrame {
    private File file;
    private boolean fileSelected;
    private Parser parser;
    private int squareCount = 0;

    public GUI() {
        setContentPane(createContentPane());
        setJMenuBar(createMenuBar(203, 203, 203));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Event Log Analysis");
        setPreferredSize(new Dimension(700, 500));
        display();
    }

    /*
     * updates and displays frame
     */
    private void display() {
        revalidate();
        repaint();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * main content pane that gets updated when file is loaded (from action event "open file")
     */
    private Container createContentPane() {
        JPanel panel = new JPanel(new GridBagLayout());  // panel for content pane
        GridBagConstraints gc = new GridBagConstraints();
        panel.setOpaque(true);
        panel.setBackground(new Color(0x2A2A2A));

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0;
        gc.insets = new Insets(10, 10, 10, 10);

        if (fileSelected) {
            for (String filePath : getPaths()) {
                panel.add(createEventPanel(filePath), gc);
                gc.gridy++;
            }
        } else {
            panel.add(homeScreen(), gc);
        }

        return panel;
    }

    /*
     * Panel to display different events
     */
    private JPanel createEventPanel(String filePath) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        panel.setOpaque(true);
        panel.setBackground(new Color(0x3A3A3A));

        ReportType reportType = getReportType(filePath);

        JLabel panelTitle = new JLabel(reportType.name());
        panelTitle.setForeground(Color.WHITE);
        panelTitle.setFont(panelTitle.getFont().deriveFont(18f));

        JLabel filePathLabel = new JLabel();
        filePathLabel.setForeground(Color.WHITE);

        if (fileSelected) {
            filePathLabel.setText(String.format("Log path: %s", file.getAbsolutePath()));
        }

        // scrollpane for table
        JTable table = createTable(filePath);
        table.setAutoCreateRowSorter(true);
        final JScrollPane tableScrollPane = new JScrollPane(table);

        // clickable panel
        JPanel eventButton = createEventButton(filePath);
        eventButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createTableWindow(tableScrollPane, filePath);
            }
        });

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.insets = new Insets(5, 10, 5, 10);
        panel.add(panelTitle, gc);

        gc.gridy++;
        panel.add(filePathLabel, gc);

        gc.gridy++;
        gc.ipady = 10;
        panel.add(eventButton, gc);

        return panel;
    }

    /*
     * Menu bar for GUI, haven't got background color to work so far
     */
    private JMenuBar createMenuBar(int r, int g, int b) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);    // selects "File" menu using alt keystroke

        // menu items
        JMenuItem openFile = new JMenuItem("Open evtx File");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK));
        openFile.addActionListener(e -> openFile());
        openFile.setActionCommand("open");
        menu.add(openFile);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_DOWN_MASK));
        exit.addActionListener(e -> System.exit(0));
        exit.setActionCommand("exit");
        menu.add(exit);

        menuBar.add(menu);                  // add "File" menu to menu bar
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(r, g, b));
        return menuBar;
    }

    /*
     * Returns button for event panel
     */
    private JPanel createEventButton(String filePath) {
        ArrayList<String> eventList = parser.getEventCounts(filePath);
        final JPanel eventButton = new JPanel(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.insets = new Insets(5, 10, 5, 10);

        if (eventList.isEmpty()) {
            JLabel label = new JLabel("[Events: 0] --Log: Security Event ID: N/A");
            label.setForeground(Color.WHITE);
            eventButton.add(label, gc);
        } else {
            for (String event : eventList) {
                JLabel label = new JLabel(event);
                label.setForeground(Color.WHITE);
                eventButton.add(label, gc);
                gc.gridy++;
            }
        }

        // settings to allow display button differently
        eventButton.setBackground(new Color(0xFF283251, true));
        eventButton.setOpaque(true);
        eventButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // displays different color when hovering over panel
        eventButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                eventButton.setBackground(new Color(0xFF343F68, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                eventButton.setBackground(new Color(0xFF283251, true));
            }
        });

        return eventButton;
    }

    /*
     * reads csv file and creates table with data
     */
    public JTable createTable(String csvPath) {
        try {
            Reader reader = new FileReader(csvPath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            String[] colNames = csvParser.iterator().next().values();
            DefaultTableModel model = new DefaultTableModel(colNames, 0);

            for (CSVRecord record : csvParser) {
                String[] rowData = new String[colNames.length];
                for (int i = 0; i < colNames.length; i++) {
                    rowData[i] = record.get(i);
                }
                model.addRow(rowData);
            }
            csvParser.close();

            return new JTable(model);
        } catch (IOException e) {
            System.out.println("Something went wrong...");
            throw new RuntimeException(e);
        }
    }

    /*
     * Displays data table in new window
     */
    private void createTableWindow(JScrollPane tableScrollPane, String fileName) {
        JFrame window = new JFrame();
        window.setTitle(fileName);
        window.setContentPane(tableScrollPane);
        window.setPreferredSize(new Dimension(800, 400));
        window.pack();
        window.setLocationRelativeTo(this);
        window.setVisible(true);
    }

    /*
     * Starting screen for program
     */
    private JPanel homeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(0x2A2A2A));

        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/logo.png")));
        Image resizedImg = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);

        JLabel logo = new JLabel(resizedIcon);
        panel.add(logo, BorderLayout.SOUTH);
        return panel;
    }

    /*
     * Loading screen for script process
     */
    private JPanel loadingScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(0x2A2A2A));
        panel.setName("Loading screen");

        JLabel loadLabel = new JLabel("Loading...");
        loadLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        loadLabel.setForeground(Color.WHITE);
        loadLabel.setFont(loadLabel.getFont().deriveFont(24f));
        panel.add(loadLabel, BorderLayout.SOUTH);

        JPanel buffer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                setOpaque(true);
                super.paintComponent(g);

                setBackground(new Color(0x2A2A2A));

                int squareSize = 20;
                int gapSize = 10;
                int totalWidth = (squareSize + gapSize) * 5 - gapSize;

                int panelWidth = getWidth();
                int x = (panelWidth - totalWidth) / 2;

                int panelHeight = getHeight();
                int y = panelHeight / 2 - squareSize / 2;

                for (int i = 0; i < squareCount; i++) {
                    g.setColor(new Color(0xFF274272, true));
                    g.fillRect(x, y, squareSize, squareSize);
                    x += squareSize + gapSize;
                }
            }
        };

        panel.add(buffer, BorderLayout.CENTER);

        Timer timer = new Timer(500, e -> {
            squareCount++;
            if (squareCount > 5) {
                squareCount = 1;
            }
            buffer.repaint();
        });
        timer.start();

        return panel;
    }

    /*
     * Returns csv paths from eventCsvs folder
     */
    private ArrayList<String> getPaths() {
        File directory = new File("eventCsvs");

        // List to store the file paths
        ArrayList<String> csvFilePaths = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            // Get all files in directory
            File[] files = directory.listFiles();

            // Iterate over files array
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                        csvFilePaths.add(file.getAbsolutePath());
                    }
                }
            }

            return csvFilePaths;
        } else {
            System.out.println("eventCsvs folder was not created properly.");
        }

        return csvFilePaths;
    }

    /*
     * Checks type of csv report
     */
    private ReportType getReportType(String filePath) {
        if (filePath.contains("FailedLogonEvents")) {
            return ReportType.FAILED_LOGINS;
        }
        if (filePath.contains("LogonLogOff")) {
            return ReportType.LOGINS;
        }
        if (filePath.contains("ScheduledTaskEvents")) {
            return ReportType.SCHEDULED_TASK_CREATED;
        }
        if (filePath.contains("AdminLogin")) {
            return ReportType.ADMIN_LOGINS;
        }
        return ReportType.UNDEFINED_EVENTS;
    }

    /*
     * Buttons and menu bar selection action methods
     */
    private void openFile() {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();

            // Parse file if evtx
            if (file.getName().toLowerCase().endsWith(".evtx")) {
                parser = new Parser(file.getAbsolutePath());

                if (parser.getExitCode() == 0) {
                    fileSelected = true;

                    // now that file is loaded, event panels can be created and displayed
                    setContentPane(createContentPane());
                    setTitle("Event Log Analysis - " + file.getName());

                    display();
                } else {
                    JOptionPane.showMessageDialog(null, "There was problem processing the chosen file. Please ensure file is a security.evtx file.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "This program can only process .evtx files.");
            }
        } else {
            System.out.println("Open command cancelled by user.");
            fileSelected = false;
        }
    }
}

