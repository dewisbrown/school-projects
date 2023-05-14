/*
 * Author: Luis Moreno
 * Date: November 16, 2022
 *
 * Project 1 - GUI Formatter
 *
 * This project is a program that parses, using recursive descent,
 * a GUI definition language defined in an input file
 * and generates the GUI that it defines. The grammar for this language is defined below:
 *
 * gui ::=
 * Window STRING '(' NUMBER ',' NUMBER ')' layout widgets End '.'
 * layout ::=
 * Layout layout_type ':'
 * layout_type ::= Flow |
 * Grid '(' NUMBER ',' NUMBER [',' NUMBER ',' NUMBER] ')' widgets ::=
 * widget widgets |
 * widget widget ::=
 * Button STRING ';' |
 * Group radio_buttons End ';' | Label STRING ';' |
 * Panel layout widgets End ';' | Textfield NUMBER ';'
 * radio_buttons ::=
 * radio_button radio_buttons | radio_button
 * radio_button ::= Radio STRING ';'
 */
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runner extends JFrame {
    private static final BufferedReader stdin =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.out.println("Enter the file path:");
        String path = stdin.readLine();
        Lexer lexer = new Lexer(path);
        Format format = new Format(lexer);

        format.file();
        lexer.close();
    }
}

