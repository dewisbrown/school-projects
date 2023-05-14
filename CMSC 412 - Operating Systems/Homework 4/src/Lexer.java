import java.io.*;
import java.util.Scanner;

public class Lexer {
    private final Scanner scnr;
    File file;

    public Lexer(String fileName) throws FileNotFoundException {
        file = new File(fileName);
        scnr = new Scanner(file);
    }
    public void close() throws IOException {
        scnr.close();
    }

    public String getNextLine() {
        if (scnr.hasNext())
            return scnr.nextLine();
        else
            return "END";
    }

    public String getNext() {
        if (scnr.hasNext())
            return scnr.next();
        else
            return "ENDLINE";
    }
}
