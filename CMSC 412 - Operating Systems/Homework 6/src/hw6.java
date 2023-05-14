/* Author: Luis Moreno
 * Date: March 2, 2023
 *
 * Homework 6
 *
 * This command-line driven program allows a user to select a directory
 * from their machine. The user can then delete, view, and mirror
 * reflect files within that directory.
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

public class hw6 {
    public static void main(String[] args) {
        boolean end = false;        // flag to end the program
        boolean dirLoaded = false;  // flag for menu options when directory has not been selected yet

        File directory = null;

        // program loop
        while(!end) {
            prompt();
            int option = getInt();
            switch (option) {
                case 0 -> {
                    System.out.println("\nProgram terminating...");
                    end = true;
                }
                case 1 -> {
                    directory = selectDirectory();
                    if (directory.exists() && directory.isDirectory()) {
                        dirLoaded = true;   // allows other menu options to work now
                        System.out.println("Successfully loaded directory: " + directory);
                        System.out.printf("Directory path: %s\n", directory.getAbsolutePath());
                    } else {
                        System.out.println("There is not a directory at the path entered.");
                    }
                }
                case 2 -> {
                    if (dirLoaded) {
                        listDirectory(directory);
                    } else {
                        System.out.println("A directory has not been loaded yet.");
                    }
                }
                case 3 -> {
                    if (dirLoaded) {
                        viewHexFile(directory);
                    } else {
                        System.out.println("A directory has not been loaded yet.");
                    }
                }
                case 4 -> {
                    if (dirLoaded) {
                        deleteFile(directory);
                    } else {
                        System.out.println("A directory has not been loaded yet.");
                    }
                }
                case 5 -> {
                    if (dirLoaded) {
                        reverseBytes(directory);
                    } else {
                        System.out.println("A directory has not been loaded yet.");
                    }
                }
                default -> System.out.println("impossible...");
            }
        }
    }

    // prints the main menu
    private static void prompt() {
        System.out.println("""
                0 – Exit\s
                1 – Select directory\s
                2 – List directory content\s
                3 – Display file (hexadecimal view)\s
                4 – Delete file\s
                5 – Mirror reflect file (byte level)\s 
                Select option:""");
    }

    // receive menu option from user
    private static int getInt() {
        Scanner scnr = new Scanner(System.in);
        int choice;

        while (true) {
            try {
                choice = scnr.nextInt();
                if (0 <= choice && choice < 6)
                    return choice;
                System.out.println("Enter a valid menu option.");
            } catch (InputMismatchException e) {
                System.out.println("Enter a numerical value.");
                scnr.next();
            }
        }
    }

    // receive text input from user
    private static String getText() {
        Scanner scnr = new Scanner(System.in);
        return scnr.nextLine();
    }

    // menu option 1
    private static File selectDirectory() {
        System.out.println("Enter the directory path:");
        return new File(getText());
    }

    // menu option 2
    private static void listDirectory(File directory) {
        System.out.println(directory.getName());
        File[] files = directory.listFiles();

        // display message if directory is empty
        if (files.length > 0) {
            // print file information
            for (File file : files) {
                System.out.printf("\t- %s, Size: %d bytes\n",
                        file.getName(), file.length());
            }
        } else {
            System.out.println("\t- No files in current directory.");
        }
    }

    // menu option 3
    private static void viewHexFile(File directory) {
        listDirectory(directory);
        System.out.println("Enter a file to view:");
        String fileName = directory.getAbsolutePath() + "/" + getText();

        // create FileInputStream with user-entered file
        try (FileInputStream inputStream = new FileInputStream(fileName)){
            byte[] buffer = new byte[16];   // FileInputStream .read() parameter is a byte array
            int fileBytes = 0;              // used to loop through bytes

            while(fileBytes != -1) {
                fileBytes = inputStream.read(buffer);   // reads file 16 bytes at a time
                for (int i = 0; i < fileBytes; i++) {
                    System.out.printf("%02X ", buffer[i]);
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.printf("The file entered was either not in directory %s, or not a readable file.\n", directory.getName());
        } catch (IOException e) {
            System.out.println("There was an error while reading the file.");
        }
    }

    // menu option 4
    private static void deleteFile(File directory) {
        listDirectory(directory);
        System.out.println("Enter a file to delete:");

        Path filePath = new File((directory.getAbsolutePath() + "/" + getText())).toPath();

        try {
            Files.delete(filePath);     // Files.delete requires a Path object ^^^
            System.out.printf("Successfully deleted file [%s]\n", filePath.getFileName());
        } catch (NoSuchFileException e) {
            System.out.printf("The file [%s] does not exist in this directory.\n", filePath.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // menu option 5
    private static void reverseBytes(File directory) {
        listDirectory(directory);
        System.out.println("Enter a file to mirror reflect:");
        File file = new File(directory.getAbsolutePath() + "/" + getText());
        byte[] buffer = new byte[(int) file.length()];

        if (file.exists()) {
            // create FileInputStream and FileOutputStream with user-entered file
            try (FileInputStream inStream = new FileInputStream(file)) {
                inStream.read(buffer);                      // read file into byte array (buffer)
                for (int i = 0; i < buffer.length; i++)
                    buffer[i] = reverseBits(buffer[i]);     // reverse bits for each byte and store back into buffer
            } catch (FileNotFoundException e) {
                System.out.printf("The file entered was either not in directory %s, or not a readable file.\n", directory.getName());
            } catch (IOException e) {
                System.out.println("There was an error while reading the file.");
            }

            try (FileOutputStream outStream = new FileOutputStream(file)) {
                outStream.write(buffer);                    // write reversed byte array (buffer) to file
            } catch (FileNotFoundException e) {
                System.out.printf("The file entered was either not in directory %s, or not a readable file.\n", directory.getName());
            } catch (IOException e) {
                System.out.println("There was an error while reading the file.");
            }
        } else {
            System.out.printf("The file entered was either not in directory %s, or not a readable file.\n", directory.getName());
        }
    }

    // reverses the bits of the passed in byte
    private static byte reverseBits(byte b) {
        byte mirror = 0;

        for (int i = 0; i < 8; i++) {
            mirror <<= 1;       // shift 'mirror' bits to the left (starting value = 0)
            mirror |= (b & 1);  // '|=' sets the least significant bit of 'b' (b & 1)
                                // as the least significant bit of 'mirror'
            b >>= 1;            // shift 'b' bits to the right
        }                       // repeat for all bits
        return mirror;
    }
}
