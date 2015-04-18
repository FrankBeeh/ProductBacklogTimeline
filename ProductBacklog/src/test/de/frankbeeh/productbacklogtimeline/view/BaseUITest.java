package de.frankbeeh.productbacklogtimeline.view;

import static junit.framework.Assert.assertEquals;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Skin;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.testfx.framework.junit.ApplicationTest;

public class BaseUITest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getFXMLResourceURL());
        final Parent root = (Parent) loader.load();

        final Scene scene = new Scene(root, 1200, 800);

        stage.setScene(scene);
        stage.show();

        final MainController controller = loader.getController();
        controller.initController(stage);
    }

    @SuppressWarnings("unchecked")
    public <T extends Node> T getUniqueNode(final String selector) {
        final Set<Node> foundNodes = removeSkinNodes(lookup(selector).<Node>queryAll());
        if (foundNodes.isEmpty()) {
            throw new RuntimeException("No node found for selector '" + selector + "'!");
        }
        if (foundNodes.size() > 1) {
            throw new RuntimeException("Multiple nodes found for selector '" + selector + ": " + foundNodes + "!");
        }
        return (T) foundNodes.iterator().next();
    }

    private Set<Node> removeSkinNodes(Set<Node> foundNodes) {
        final Set<Node> filteredNodes = new HashSet<Node>();
        for (final Node node : foundNodes) {
            if (!(node instanceof Skin) && !(node.getClass().getName().contains("Skin"))) {
                filteredNodes.add(node);
            }
        }
        return filteredNodes;
    }

    protected void enterFileName(String fileName) {
        typeString(fileName);
        type(KeyCode.ENTER);
    }

    protected void selectTab(String tabTitle) {
        clickOn(tabTitle);
        assertEquals(tabTitle, getSelectedTabTitle());
    }

    private String getSelectedTabTitle() {
        return getTabPane().getSelectionModel().getSelectedItem().getText();
    }

    private TabPane getTabPane() {
        return getUniqueNode("#mainTabPane");
    }

    private URL getFXMLResourceURL() {
        return MainController.class.getResource("main.fxml");
    }

    // This is a workaround for FxRobot.write not working on native file chooser dialog.
    private void typeString(CharSequence characters) {
        int length = characters.length();
        for (int i = 0; i < length; i++) {
            type(characters.charAt(i));
        }
    }

    private void type(char character) {
        switch (character) {
            case 'a':
                type(KeyCode.A);
                break;
            case 'b':
                type(KeyCode.B);
                break;
            case 'c':
                type(KeyCode.C);
                break;
            case 'd':
                type(KeyCode.D);
                break;
            case 'e':
                type(KeyCode.E);
                break;
            case 'f':
                type(KeyCode.F);
                break;
            case 'g':
                type(KeyCode.G);
                break;
            case 'h':
                type(KeyCode.H);
                break;
            case 'i':
                type(KeyCode.I);
                break;
            case 'j':
                type(KeyCode.J);
                break;
            case 'k':
                type(KeyCode.K);
                break;
            case 'l':
                type(KeyCode.L);
                break;
            case 'm':
                type(KeyCode.M);
                break;
            case 'n':
                type(KeyCode.N);
                break;
            case 'o':
                type(KeyCode.O);
                break;
            case 'p':
                type(KeyCode.P);
                break;
            case 'q':
                type(KeyCode.Q);
                break;
            case 'r':
                type(KeyCode.R);
                break;
            case 's':
                type(KeyCode.S);
                break;
            case 't':
                type(KeyCode.T);
                break;
            case 'u':
                type(KeyCode.U);
                break;
            case 'v':
                type(KeyCode.V);
                break;
            case 'w':
                type(KeyCode.W);
                break;
            case 'x':
                type(KeyCode.X);
                break;
            case 'y':
                type(KeyCode.Y);
                break;
            case 'z':
                type(KeyCode.Z);
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
                type(KeyCode.BACK_QUOTE);
                break;
            case '0':
                type(KeyCode.DIGIT0);
                break;
            case '1':
                type(KeyCode.DIGIT1);
                break;
            case '2':
                type(KeyCode.DIGIT2);
                break;
            case '3':
                type(KeyCode.DIGIT3);
                break;
            case '4':
                type(KeyCode.DIGIT4);
                break;
            case '5':
                type(KeyCode.DIGIT5);
                break;
            case '6':
                type(KeyCode.DIGIT6);
                break;
            case '7':
                type(KeyCode.DIGIT7);
                break;
            case '8':
                type(KeyCode.DIGIT8);
                break;
            case '9':
                type(KeyCode.DIGIT9);
                break;
            case '-':
                type(KeyCode.MINUS);
                break;
            case '=':
                type(KeyCode.EQUALS);
                break;
            case '~':
                typeUpperKey(KeyCode.BACK_QUOTE);
                break;
            case '!':
                type(KeyCode.EXCLAMATION_MARK);
                break;
            case '@':
                type(KeyCode.AT);
                break;
            case '#':
                type(KeyCode.NUMBER_SIGN);
                break;
            case '$':
                type(KeyCode.DOLLAR);
                break;
            case '%':
                typeUpperKey(KeyCode.DIGIT5);
                break;
            case '^':
                type(KeyCode.CIRCUMFLEX);
                break;
            case '&':
                type(KeyCode.AMPERSAND);
                break;
            case '*':
                type(KeyCode.ASTERISK);
                break;
            case '(':
                type(KeyCode.LEFT_PARENTHESIS);
                break;
            case ')':
                type(KeyCode.RIGHT_PARENTHESIS);
                break;
            case '_':
                type(KeyCode.UNDERSCORE);
                break;
            case '+':
                type(KeyCode.PLUS);
                break;
            case '\t':
                type(KeyCode.TAB);
                break;
            case '\n':
                type(KeyCode.ENTER);
                break;
            case '[':
                type(KeyCode.OPEN_BRACKET);
                break;
            case ']':
                type(KeyCode.CLOSE_BRACKET);
                break;
            case '\\':
                type(KeyCode.BACK_SLASH);
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
                type(KeyCode.SEMICOLON);
                break;
            case ':':
                type(KeyCode.COLON);
                break;
            case '\'':
                type(KeyCode.QUOTE);
                break;
            case '"':
                type(KeyCode.QUOTEDBL);
                break;
            case ',':
                type(KeyCode.COMMA);
                break;
            case '<':
                typeUpperKey(KeyCode.COMMA);
                break;
            case '.':
                type(KeyCode.PERIOD);
                break;
            case '>':
                typeUpperKey(KeyCode.PERIOD);
                break;
            case '/':
                type(KeyCode.SLASH);
                break;
            case '?':
                typeUpperKey(KeyCode.SLASH);
                break;
            case ' ':
                type(KeyCode.SPACE);
                break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    private void typeUpperKey(KeyCode keyCode) {
        press(KeyCode.SHIFT).type(keyCode).release(KeyCode.SHIFT);
    }
}
