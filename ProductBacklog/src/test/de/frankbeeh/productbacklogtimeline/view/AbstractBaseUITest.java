package de.frankbeeh.productbacklogtimeline.view;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

import org.loadui.testfx.GuiTest;

public abstract class AbstractBaseUITest extends GuiTest {

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

    public <T extends Node> T getNode(final String selector) {
        return GuiTest.<T> find(selector);
    }

    protected abstract URL getFXMLResourceURL();

    protected void enterFileName(String fileName) {
        type(fileName);
        type(KeyCode.ENTER);
    }

    protected void selectTab(String tabTitle) {
        click(tabTitle);
        assertEquals(tabTitle, getSelectedTabTitle());
    }

    private String getSelectedTabTitle() {
        return getTabPane().getSelectionModel().getSelectedItem().getText();
    }

    private TabPane getTabPane() {
        return getNode("#mainTabPane");
    }

    protected void closeDialog() {
        press(KeyCode.ALT);
        press(KeyCode.F4);
        release(KeyCode.ALT);
    }

}
