package de.frankbeeh.productbacklogtimeline.view;

import org.junit.Test;

public class SprintUITest extends BaseUITest {

    @Test
    public void importAndEditSprintClickCancel() throws Exception {
        importSprint();
        editSprintClickCancel(getFirstSprint());
    }

    @Test
    public void importAndEditSprintClickSave() throws Exception {
        importSprint();
        editSprintAndSave(getFirstSprint());
    }

    private void importSprint() {
        openSprintImportDialog();
        enterFileName("Sprints.csv");
        selectTab("Sprints");
    }

    private void openSprintImportDialog() {
        click("File");
        click("Import Sprints");
    }

    private String getFirstSprint() {
        return "Sprint 1";
    }

    private void editSprintAndSave(String sprint) {
        editSprint(sprint);
        click("Save");
        closeCurrentWindow();
    }

    private void editSprintClickCancel(String sprint) {
        editSprint(sprint);
        click("Cancel");
        closeCurrentWindow();
    }

    private void editSprint(String sprint) {
        rightClick(sprint);
        click("#editItem");
    }

}
