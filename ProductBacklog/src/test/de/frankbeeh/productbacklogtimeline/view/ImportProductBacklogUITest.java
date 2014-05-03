package de.frankbeeh.productbacklogtimeline.view;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

import org.junit.Test;

public class ImportProductBacklogUITest extends AbstractBaseUITest {

    @Override
    protected URL getFXMLResourceURL() {
        return MainController.class.getResource("main.fxml");
    }

    @Test
    public void importBacklog() throws Exception {
        openPBLImportDialog();
        enterFileName();
        selectPBLTab();
    }

    @Test
    public void cancelImportBacklog() throws Exception {
        openPBLImportDialog();
        closeDialog();
        selectPBLTab();
    }

    private void enterFileName() {
        type("PBL.csv");
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
        return getNode("#mainTabPane");
    }

    private void openPBLImportDialog() {
        click("File");
        click("Import PBL");
    }

    private void closeDialog() {
        press(KeyCode.ALT);
        press(KeyCode.F4);
        release(KeyCode.ALT);
    }

}
