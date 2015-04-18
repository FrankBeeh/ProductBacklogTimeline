package de.frankbeeh.productbacklogtimeline.view;

import org.junit.Test;

public class SprintUITest extends BaseUITest {
    
    @Test
    public void importAndEditSprintClickSave() throws Exception {
        importSprint();
        editSprint(getFirstSprint());
    }

    private void importSprint() {
        openSprintImportDialog();
        enterFileName("VelocityForecast1.csv");
        selectTab("Sprints");
    }

    private void openSprintImportDialog() {
        clickOn("File");
        clickOn("Import Sprints");
    }

    private String getFirstSprint() {
        return "Sprint 1";
    }

    private void editSprint(String sprint) {
        rightClickOn(sprint);
        clickOn("#editItem");
        clickOn("#okDialog");
    }
}