/*
 * Author: Luis Moreno
 * Date: November 16, 2022
 *
 * Project 1 - GUI Formatter
 *
 * This class reads a file from the path supplied in the main method.
 * The text from the file is read character by character, assigning tokens
 * and creating lexemes that can be used by Format.java.
 *
 * Tokens can be found in the enum class Token.java.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {
    private BufferedReader file;
    private String line = "";
    private char character;
    private String currentLexeme;
    private Token token;
    private int i = 0;
    private int lineNum;

    public Lexer(String fileName) throws FileNotFoundException {
        file = new BufferedReader(new FileReader(fileName));
        character = nextChar();
        currentLexeme = "";
        lineNum = 0;
    }

    public void close() throws IOException {
        file.close();
    }

    // returns token after checking for language tokens,
    // also sets the lexeme for a token
    public Token getNextToken() {
        currentLexeme = "";
        while (character != 0 && Character.isWhitespace(character)) {
            character = nextChar();
        }
        if (character == 0) {
            token = Token.END_OF_FILE;
        }
        while (Character.isLetter(character)) {
            currentLexeme += character;
            character = nextChar();
        }
        if (testToken(currentLexeme) != Token.NOT_FOUND)
            token = testToken(currentLexeme);
        else if (Character.isDigit(character)) {
            while (Character.isDigit(character)) {
                currentLexeme += character;
                character = nextChar();
            }
            token = Token.NUMBER;
        } else {
            token = testSeparator();
        }
        return token;
    }

    public String getLexeme() {
        return currentLexeme;
    }

    // used to print errors in Format class
    public int getLineNum() {
        return lineNum;
    }

    // returns next character in input file
    private char nextChar() {
        try {
            if (line == null) {
                return 0;
            }
            if (i == line.length()) {
                line = file.readLine();
                i = 0;
                lineNum++;
                return '\n';
            }
            return line.charAt(i++);
        } catch (IOException ioException) {
            return 0;
        }
    }

    // checks for language token symbols
    private Token testSeparator() {
        switch (character) {
            case ':':
                currentLexeme += character;
                character = nextChar();
                return Token.COLON;
            case '(':
                currentLexeme += character;
                character = nextChar();
                return Token.LEFT_PARENTHESIS;
            case ')':
                currentLexeme += character;
                character = nextChar();
                return Token.RIGHT_PARENTHESIS;
            case ';':
                currentLexeme += character;
                character = nextChar();
                return Token.SEMICOLON;
            case ',':
                currentLexeme += character;
                character = nextChar();
                return Token.COMMA;
            case '"':
                character = nextChar();
                while (character != '"') {
                    currentLexeme += character;
                    character = nextChar();
                }
                character = nextChar();
                return Token.STRING;
            case '.':
                currentLexeme += character;
                character = nextChar();
                return Token.PERIOD;
            default:
                return Token.NOT_FOUND;
        }
    }

    // checks for language tokens
    private Token testToken(String lexeme) {
        return switch (lexeme) {
            case "Window" -> Token.WINDOW;
            case "Layout" -> Token.LAYOUT;
            case "Flow" -> Token.FLOW;
            case "Grid" -> Token.GRID;
            case "Textfield" -> Token.TEXTFIELD;
            case "Panel" -> Token.PANEL;
            case "Button" -> Token.BUTTON;
            case "Group" -> Token.GROUP;
            case "Radio" -> Token.RADIO;
            case "Label" -> Token.LABEL;
            case "End" -> Token.END;
            default -> Token.NOT_FOUND;
        };
    }
}