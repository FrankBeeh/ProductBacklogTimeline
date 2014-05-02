package de.frankbeeh.productbacklogtimeline.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

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

}
