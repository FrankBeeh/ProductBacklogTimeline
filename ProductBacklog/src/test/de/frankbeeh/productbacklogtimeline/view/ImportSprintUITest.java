package de.frankbeeh.productbacklogtimeline.view;

import java.net.URL;

import org.junit.Test;

public class ImportSprintUITest extends AbstractBaseUITest {

    @Override
    protected URL getFXMLResourceURL() {
        return MainController.class.getResource("main.fxml");
    }

    @Test
    public void importSprints() throws Exception {
        openSprintImportDialog();
        enterFileName("Sprints.csv");
        selectTab("Sprints");
    }

    private void openSprintImportDialog() {
        click("File");
        click("Import Sprints");
    }
}
