package de.frankbeeh.productbacklogtimeline.view;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import org.testfx.api.FxRobot;

import com.google.common.base.Predicate;
import com.sun.javafx.scene.control.skin.LabeledText;

public class BaseAccessor {
    private final FxRobot fxRobot;

    public BaseAccessor(FxRobot fxRobot) {
        this.fxRobot = fxRobot;
    }

    @SuppressWarnings("unchecked")
    protected <T extends Node> T getUniqueNode(final String selector) {
        final Set<Node> foundNodes = removeSkinNodes(fxRobot.lookup(selector).<Node> queryAll());
        if (foundNodes.isEmpty()) {
            throw new RuntimeException("No node found for selector '" + selector + "'!");
        }
        if (foundNodes.size() > 1) {
            throw new RuntimeException("Multiple nodes found for selector '" + selector + ": " + foundNodes + "!");
        }
        return (T) foundNodes.iterator().next();
    }

    protected FxRobot getFxRobot() {
        return fxRobot;
    }

    protected void clickOn(Node node) {
        fxRobot.clickOn(node);
    }
    
    protected void enterText(String selector, String text) {
        clickOn(getUniqueNode(selector));
        fxRobot.write(text);
    }

    protected void typeKeyCode(KeyCode keyCode) {
        fxRobot.type(keyCode);
    }

    protected void typeString(CharSequence characters) {
        int length = characters.length();
        for (int i = 0; i < length; i++) {
            type(characters.charAt(i));
        }
    }

    private Set<Node> removeSkinNodes(Set<Node> foundNodes) {
        final Set<Node> filteredNodes = new HashSet<Node>();
        for (final Node node : foundNodes) {
            if (!(node instanceof Skin) && !isSkinClass(node)) {
                filteredNodes.add(node);
            }
        }
        return filteredNodes;
    }

    protected TableViewContent getActualTableViewContent(TableView<?> tableView) {
        final TableViewContent actualContent = new TableViewContent();
        final Predicate<Node> nodePredicate = new Predicate<Node>() {
            @Override
            public boolean apply(Node node) {
                if (node instanceof TableRow) {
                    actualContent.addRow();
                }
                if (node instanceof LabeledText) {
                    actualContent.addCellContent(((LabeledText) node).getText());
                }
                if (node instanceof StackPane) {
                    actualContent.stopAdding();
                }
                return false;
            }
        };
        fxRobot.from(tableView).lookup(nodePredicate).queryFirst();
        return actualContent;
    }
    
    private boolean isSkinClass(final Node node) {
        final String name = node.getClass().getName();
        return name.contains("Skin") && !(name.contains("MenuBarSkin$MenuBarButton"));
    }

    private void type(char character) {
        switch (character) {
            case 'a':
                fxRobot.type(KeyCode.A);
                break;
            case 'b':
                fxRobot.type(KeyCode.B);
                break;
            case 'c':
                fxRobot.type(KeyCode.C);
                break;
            case 'd':
                fxRobot.type(KeyCode.D);
                break;
            case 'e':
                fxRobot.type(KeyCode.E);
                break;
            case 'f':
                fxRobot.type(KeyCode.F);
                break;
            case 'g':
                fxRobot.type(KeyCode.G);
                break;
            case 'h':
                fxRobot.type(KeyCode.H);
                break;
            case 'i':
                fxRobot.type(KeyCode.I);
                break;
            case 'j':
                fxRobot.type(KeyCode.J);
                break;
            case 'k':
                fxRobot.type(KeyCode.K);
                break;
            case 'l':
                fxRobot.type(KeyCode.L);
                break;
            case 'm':
                fxRobot.type(KeyCode.M);
                break;
            case 'n':
                fxRobot.type(KeyCode.N);
                break;
            case 'o':
                fxRobot.type(KeyCode.O);
                break;
            case 'p':
                fxRobot.type(KeyCode.P);
                break;
            case 'q':
                fxRobot.type(KeyCode.Q);
                break;
            case 'r':
                fxRobot.type(KeyCode.R);
                break;
            case 's':
                fxRobot.type(KeyCode.S);
                break;
            case 't':
                fxRobot.type(KeyCode.T);
                break;
            case 'u':
                fxRobot.type(KeyCode.U);
                break;
            case 'v':
                fxRobot.type(KeyCode.V);
                break;
            case 'w':
                fxRobot.type(KeyCode.W);
                break;
            case 'x':
                fxRobot.type(KeyCode.X);
                break;
            case 'y':
                fxRobot.type(KeyCode.Y);
                break;
            case 'z':
                fxRobot.type(KeyCode.Z);
                break;
            case 'A':
                typeUpperKey(KeyCode.A);
                break;
            case 'B':
                typeUpperKey(KeyCode.B);
                break;
            case 'C':
                typeUpperKey(KeyCode.C);
                break;
            case 'D':
                typeUpperKey(KeyCode.D);
                break;
            case 'E':
                typeUpperKey(KeyCode.E);
                break;
            case 'F':
                typeUpperKey(KeyCode.F);
                break;
            case 'G':
                typeUpperKey(KeyCode.G);
                break;
            case 'H':
                typeUpperKey(KeyCode.H);
                break;
            case 'I':
                typeUpperKey(KeyCode.I);
                break;
            case 'J':
                typeUpperKey(KeyCode.J);
                break;
            case 'K':
                typeUpperKey(KeyCode.K);
                break;
            case 'L':
                typeUpperKey(KeyCode.L);
                break;
            case 'M':
                typeUpperKey(KeyCode.M);
                break;
            case 'N':
                typeUpperKey(KeyCode.N);
                break;
            case 'O':
                typeUpperKey(KeyCode.O);
                break;
            case 'P':
                typeUpperKey(KeyCode.P);
                break;
            case 'Q':
                typeUpperKey(KeyCode.Q);
                break;
            case 'R':
                typeUpperKey(KeyCode.R);
                break;
            case 'S':
                typeUpperKey(KeyCode.S);
                break;
            case 'T':
                typeUpperKey(KeyCode.T);
                break;
            case 'U':
                typeUpperKey(KeyCode.U);
                break;
            case 'V':
                typeUpperKey(KeyCode.V);
                break;
            case 'W':
                typeUpperKey(KeyCode.W);
                break;
            case 'X':
                typeUpperKey(KeyCode.X);
                break;
            case 'Y':
                typeUpperKey(KeyCode.Y);
                break;
            case 'Z':
                typeUpperKey(KeyCode.Z);
                break;
            case '`':
                fxRobot.type(KeyCode.BACK_QUOTE);
                break;
            case '0':
                fxRobot.type(KeyCode.DIGIT0);
                break;
            case '1':
                fxRobot.type(KeyCode.DIGIT1);
                break;
            case '2':
                fxRobot.type(KeyCode.DIGIT2);
                break;
            case '3':
                fxRobot.type(KeyCode.DIGIT3);
                break;
            case '4':
                fxRobot.type(KeyCode.DIGIT4);
                break;
            case '5':
                fxRobot.type(KeyCode.DIGIT5);
                break;
            case '6':
                fxRobot.type(KeyCode.DIGIT6);
                break;
            case '7':
                fxRobot.type(KeyCode.DIGIT7);
                break;
            case '8':
                fxRobot.type(KeyCode.DIGIT8);
                break;
            case '9':
                fxRobot.type(KeyCode.DIGIT9);
                break;
            case '-':
                fxRobot.type(KeyCode.MINUS);
                break;
            case '=':
                fxRobot.type(KeyCode.EQUALS);
                break;
            case '~':
                typeUpperKey(KeyCode.BACK_QUOTE);
                break;
            case '!':
                fxRobot.type(KeyCode.EXCLAMATION_MARK);
                break;
            case '@':
                fxRobot.type(KeyCode.AT);
                break;
            case '#':
                fxRobot.type(KeyCode.NUMBER_SIGN);
                break;
            case '$':
                fxRobot.type(KeyCode.DOLLAR);
                break;
            case '%':
                typeUpperKey(KeyCode.DIGIT5);
                break;
            case '^':
                fxRobot.type(KeyCode.CIRCUMFLEX);
                break;
            case '&':
                fxRobot.type(KeyCode.AMPERSAND);
                break;
            case '*':
                fxRobot.type(KeyCode.ASTERISK);
                break;
            case '(':
                fxRobot.type(KeyCode.LEFT_PARENTHESIS);
                break;
            case ')':
                fxRobot.type(KeyCode.RIGHT_PARENTHESIS);
                break;
            case '_':
                fxRobot.type(KeyCode.UNDERSCORE);
                break;
            case '+':
                fxRobot.type(KeyCode.PLUS);
                break;
            case '\t':
                fxRobot.type(KeyCode.TAB);
                break;
            case '\n':
                fxRobot.type(KeyCode.ENTER);
                break;
            case '[':
                fxRobot.type(KeyCode.OPEN_BRACKET);
                break;
            case ']':
                fxRobot.type(KeyCode.CLOSE_BRACKET);
                break;
            case '\\':
                fxRobot.type(KeyCode.BACK_SLASH);
                break;
            case '{':
                typeUpperKey(KeyCode.OPEN_BRACKET);
                break;
            case '}':
                typeUpperKey(KeyCode.CLOSE_BRACKET);
                break;
            case '|':
                typeUpperKey(KeyCode.BACK_SLASH);
                break;
            case ';':
                fxRobot.type(KeyCode.SEMICOLON);
                break;
            case ':':
                fxRobot.type(KeyCode.COLON);
                break;
            case '\'':
                fxRobot.type(KeyCode.QUOTE);
                break;
            case '"':
                fxRobot.type(KeyCode.QUOTEDBL);
                break;
            case ',':
                fxRobot.type(KeyCode.COMMA);
                break;
            case '<':
                typeUpperKey(KeyCode.COMMA);
                break;
            case '.':
                fxRobot.type(KeyCode.PERIOD);
                break;
            case '>':
                typeUpperKey(KeyCode.PERIOD);
                break;
            case '/':
                fxRobot.type(KeyCode.SLASH);
                break;
            case '?':
                typeUpperKey(KeyCode.SLASH);
                break;
            case ' ':
                fxRobot.type(KeyCode.SPACE);
                break;
            default:
                throw new IllegalArgumentException("Cannot fxRobot.type character " + character);
        }
    }

    private void typeUpperKey(KeyCode keyCode) {
        fxRobot.press(KeyCode.SHIFT).type(keyCode).release(KeyCode.SHIFT);
    }
}
