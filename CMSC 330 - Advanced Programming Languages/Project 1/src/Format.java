/*
 * Author: Luis Moreno
 * Date: November 16, 2022
 *
 * Project 1 - GUI Formatter
 *
 * Using a Lexer object, this class uses tokens and lexemes
 * from the input file to create a GUI.
 */
import javax.swing.*;
import java.awt.*;

public class Format {
    private final int MAX_SIZE = 10;
    private Lexer lexer;
    private Token token;
    private JPanel[] panels = new JPanel[MAX_SIZE];     // array of JPanels used to create nested panels
    private JPanel panel;
    private JFrame frame;
    private ButtonGroup buttonGroup;
    private JRadioButton radButton;
    private int depth;                                  // depth determines nesting in panel array

    public Format(Lexer lexer) {
        this.lexer = lexer;
        panels[0] = new JPanel();
    }

    public void file() {
        token = lexer.getNextToken();
        if (gui()) {
            System.out.println("GUI successfully created!");
        } else {
            System.out.println("GUI failed to create." +
                    "\nUnexpected token: [" + token.name() + "] at line " + lexer.getLineNum());
        }
    }

    // creates GUI by verifying tokens from input file using Lexer class
    // returns false if fails to complete
    private boolean gui() {
        int width, height;
        if (token == Token.WINDOW) {        // Window STRING "(" NUMBER "," NUMBER ")"
            depth = 0;
            frame = new JFrame();
            token = lexer.getNextToken();
            if (token == Token.STRING) {
                frame.setTitle(lexer.getLexeme());
                token = lexer.getNextToken();
                if (token == Token.LEFT_PARENTHESIS) {
                    token = lexer.getNextToken();
                    if (token == Token.NUMBER) {
                        width = Integer.parseInt(lexer.getLexeme());
                        token = lexer.getNextToken();
                        if (token == Token.COMMA) {
                            token = lexer.getNextToken();
                            if (token == Token.NUMBER) {
                                height = Integer.parseInt(lexer.getLexeme());
                                frame.setSize(width, height);
                                token = lexer.getNextToken();
                                if (token == Token.RIGHT_PARENTHESIS) {
                                    token = lexer.getNextToken();
                                    if (this.layout()) {                        // layout
                                        if (this.widgets()) {                   // widgets
                                            if (token == Token.END) {           // End
                                                token = lexer.getNextToken();
                                                if (token == Token.PERIOD) {    // "."
                                                    frame.setLocationRelativeTo(null);
                                                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                    frame.setVisible(true);
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // returns true if the input file follows language grammar
    private boolean layout() {
        if (token == Token.LAYOUT) {
            token = lexer.getNextToken();
            if (this.layoutType()) {
                if (token == Token.COLON) {
                    token = lexer.getNextToken();
                    return true;
                }
            }
        }
        return false;
    }

    // sets panel or frame layout using input file tokens
    // returns true if the input file follows language grammar
    private boolean layoutType() {
        if (token == Token.FLOW) {              // Flow
            if (depth == 0) {
                frame.setLayout(new FlowLayout());
            } else {
                panels[depth].setLayout(new FlowLayout());
            }
            token = lexer.getNextToken();
            return true;
        } else if (token == Token.GRID) {       // Grid "("NUMBER "," NUMBER ["," NUMBER "," NUMBER]")"
            int width, height, hgap, vgap;
            token = lexer.getNextToken();
            if (token == Token.LEFT_PARENTHESIS) {
                token = lexer.getNextToken();
                if (token == Token.NUMBER) {
                    width = Integer.parseInt(lexer.getLexeme());
                    token = lexer.getNextToken();
                    if (token == Token.COMMA) {
                        token = lexer.getNextToken();
                        if (token == Token.NUMBER) {
                            height = Integer.parseInt(lexer.getLexeme());
                            token = lexer.getNextToken();
                            if (token == Token.RIGHT_PARENTHESIS) {     // Grid (NUMBER, NUMBER)
                                if (depth == 0) {
                                    frame.setLayout(new GridLayout(width, height));
                                } else {
                                    panels[depth].setLayout(new GridLayout(width, height));
                                }
                                token = lexer.getNextToken();
                                return true;
                            } else if (token == Token.COMMA) {          // Grid (NUMBER, NUMBER, NUMBER, NUMBER)
                                token = lexer.getNextToken();
                                if (token == Token.NUMBER) {
                                    hgap = Integer.parseInt(lexer.getLexeme());
                                    token = lexer.getNextToken();
                                    if (token == Token.COMMA) {
                                        token = lexer.getNextToken();
                                        if (token == Token.NUMBER) {
                                            vgap = Integer.parseInt(lexer.getLexeme());
                                            token = lexer.getNextToken();
                                            if (token == Token.RIGHT_PARENTHESIS) {
                                                if (depth == 0) {
                                                    frame.setLayout(new GridLayout(width, height, hgap, vgap));
                                                } else {
                                                    panels[depth].setLayout(new GridLayout(width, height, hgap, vgap));
                                                }
                                                token = lexer.getNextToken();
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // returns true if the input file follows language grammar
    private boolean widgets() {
        if (this.widget()) {            // widget widgets | widget
            if (this.widgets()) {       // recursive call
                return true;
            }
            return true;
        }
        return false;
    }

    // creates widgets using tokens from input file
    // returns true if the input file follows language grammar
    private boolean widget() {
        String text;
        if (token == Token.BUTTON) {                    // Button STRING ';'
            token = lexer.getNextToken();
            if (token == Token.STRING) {
                text = lexer.getLexeme();
                token = lexer.getNextToken();
                if (token == Token.SEMICOLON) {
                    if (depth == 0) {
                        frame.add(new JButton(text));
                    } else {
                        panels[depth].add(new JButton(text));
                    }
                    token = lexer.getNextToken();
                    return true;
                }
            }
        } else if (token == Token.GROUP) {              // Group radio_buttons End ";"
            buttonGroup = new ButtonGroup();
            token = lexer.getNextToken();
            if (radioButtons()) {
                if (token == Token.END) {
                    token = lexer.getNextToken();
                    if (token == Token.SEMICOLON) {
                        token = lexer.getNextToken();
                        return true;
                    }
                }
            }
        } else if (token == Token.LABEL) {              // Label STRING ";"
            token = lexer.getNextToken();
            if (token == Token.STRING) {
                text = lexer.getLexeme();
                token = lexer.getNextToken();
                if (token == Token.SEMICOLON) {
                    if (depth == 0) {
                        frame.add(new JLabel(text));
                    } else {
                        panels[depth].add(new JLabel(text));
                    }
                    token = lexer.getNextToken();
                    return true;
                }
            }
        } else if (token == Token.PANEL) {              //Panel layout widgets End ";"
            depth++;
            panel = new JPanel();
            panels[depth] = panel;
            token = lexer.getNextToken();
            if (layout()) {
                if (widgets()) {
                    if (token == Token.END) {
                        token = lexer.getNextToken();
                        if (token == Token.SEMICOLON) {
                            if (depth == 1) {
                                frame.add(panels[depth]);               // add panel to frame
                            } else {
                                panels[depth - 1].add(panels[depth]);   // adds nested panel to panel
                            }
                            panels[depth] = null;
                            depth--;
                            token = lexer.getNextToken();
                            return true;
                        }
                    }
                }
            }
        } else if (token == Token.TEXTFIELD) {          // Textfield NUMBER ";"
            token = lexer.getNextToken();
            if (token == Token.NUMBER) {
                int length = Integer.parseInt(lexer.getLexeme());
                token = lexer.getNextToken();
                if (token == Token.SEMICOLON) {
                    if (depth == 0) {
                        frame.add(new JTextField(length));
                    } else {
                        panels[depth].add(new JTextField(length));
                    }
                    token = lexer.getNextToken();
                    return true;
                }
            }
        }
        return false;
    }

    // returns true if the input file follows language grammar
    private boolean radioButtons() {
        if (radioButton()) {        // radio_button
            if (radioButtons()) {   // radio_buttons (recursive statement)
                return true;
            }
            return true;
        }
        return false;
    }

    // creates radio buttons using tokens from input file
    // returns true if the input file follows language grammar
    private boolean radioButton() {
        String buttonText;
        if (token == Token.RADIO) {             // Radio STRING ";"
            token = lexer.getNextToken();
            if (token == Token.STRING) {
                buttonText = lexer.getLexeme();
                token = lexer.getNextToken();
                if (token == Token.SEMICOLON) {
                    radButton = new JRadioButton(buttonText);
                    buttonGroup.add(radButton);
                    if (depth == 0) {
                        frame.add(radButton);
                    } else {
                        panels[depth].add(radButton);
                    }
                    token = lexer.getNextToken();
                    return true;
                }
            }
        }
        return false;
    }
}
