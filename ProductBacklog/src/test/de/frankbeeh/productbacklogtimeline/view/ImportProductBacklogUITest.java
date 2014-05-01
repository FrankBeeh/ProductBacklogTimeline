package de.frankbeeh.productbacklogtimeline.view;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class ImportProductBacklogUITest extends GuiTest {

    private static final String APPLICATION_FXML = "main.fxml";

    @Override
    protected Parent getRootNode() {
        final FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource(APPLICATION_FXML));
        try {
            fxmlLoader.load();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getRoot();
    }

    @Test
    public void importBacklog() throws Exception {
        importBacklogFile();
        enterFileName();
        selectPBLTab();
    }

    @Test
    public void cancelImportBacklog() throws Exception {
        importBacklogFile();
        closeDialog();
        selectPBLTab();
    }

    private void enterFileName() {
        type("PBL11.csv");
        type(KeyCode.ENTER);
    }

    private void selectPBLTab() {
        click("PBL");
        assertEquals("PBL", getSelectedTabTitle());
    }

    private String getSelectedTabTitle() {
        return getTabPane().getSelectionModel().getSelectedItem().getText();
    }

    private TabPane getTabPane() {
        return this.<TabPane> getNode("#mainTabPane");
    }

    private void importBacklogFile() {
        click("File");
        click("Import PBL");
    }

    private void closeDialog() {
        press(KeyCode.ALT);
        press(KeyCode.F4);
        release(KeyCode.ALT);
    }

    @SuppressWarnings("unchecked")
    public <T> T getNode(final String theSelector) {
        return (T) find(theSelector);
    }
}