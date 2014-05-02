package de.frankbeeh.productbacklogtimeline.view;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

import org.junit.Test;

public class ImportSprintUITest extends AbstractBaseUITest {

    @Override
    protected URL getFXMLResourceURL() {
        return MainController.class.getResource("main.fxml");
    }

    @Test
    public void importSprints() throws Exception {
        openSprintImportDialog();
        enterFileName();
        selectSprintsTab();
    }

    private void enterFileName() {
        type("Sprints5.csv");
        type(KeyCode.ENTER);
    }

    private void selectSprintsTab() {
        click("Sprints");
        assertEquals("Sprints", getSelectedTabTitle());
    }

    private String getSelectedTabTitle() {
        return getTabPane().getSelectionModel().getSelectedItem().getText();
    }

    private TabPane getTabPane() {
        return this.<TabPane> getNode("#mainTabPane");
    }

    private void openSprintImportDialog() {
        click("File");
        click("Import Sprints");
    }
}
