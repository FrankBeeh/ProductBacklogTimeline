package de.frankbeeh.productbacklogtimeline.view;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Skin;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

import org.junit.Before;
import org.loadui.testfx.GuiTest;

public class BaseUITest {
    private GuiTest guiTest;

    public BaseUITest() {
        guiTest = new GuiTest() {
            @Override
            protected Parent getRootNode() {
                final URL resource = getFXMLResourceURL();
                final FXMLLoader fxmlLoader = new FXMLLoader(resource);
                try {
                    fxmlLoader.load();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                return fxmlLoader.getRoot();
            }
        };
    }

    @Before
    public void setUp() throws Throwable {
        guiTest.setupStage();
    }

    @SuppressWarnings("unchecked")
    public <T extends Node> T getUniqueNode(final String selector) {
        final Set<Node> foundNodes = removeSkinNodes(GuiTest.findAll(selector));
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

    private URL getFXMLResourceURL() {
        return MainController.class.getResource("main.fxml");
    }

    protected void enterFileName(String fileName) {
        guiTest.type(fileName);
        guiTest.type(KeyCode.ENTER);
    }

    protected void selectTab(String tabTitle) {
        guiTest.click(tabTitle);
        assertEquals(tabTitle, getSelectedTabTitle());
    }

    private String getSelectedTabTitle() {
        return getTabPane().getSelectionModel().getSelectedItem().getText();
    }

    private TabPane getTabPane() {
        return getUniqueNode("#mainTabPane");
    }

    protected void closeDialog() {
        guiTest.press(KeyCode.ALT);
        guiTest.press(KeyCode.F4);
        guiTest.release(KeyCode.ALT);
    }

    public void click(String query) {
        guiTest.click(query);
    }

    public void rightClick(String query) {
        guiTest.rightClick(query);
    }

    public void closeCurrentWindow() {
        guiTest.closeCurrentWindow();
    }
}
