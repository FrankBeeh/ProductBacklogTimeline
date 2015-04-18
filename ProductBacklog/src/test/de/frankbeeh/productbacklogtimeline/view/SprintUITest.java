package de.frankbeeh.productbacklogtimeline.view;

import javafx.scene.control.TableView;

import org.junit.Test;

public class SprintUITest extends BaseUITest {

    @Test
    public void importAndEditSprintClickSave() throws Exception {
        importSprint();
        final TableViewContent expectedContent = new TableViewContent(new String[][] { { "Sprint 1", "25.04.2014", "25.04.2014", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "", "", "", "" },
                { "Sprint 2", "26.04.2014", "26.04.2014", "8.0", "", "", "", "4.0", "4.0", "4.0", "", "7.0", "7.0", "7.0" },
                { "Sprint 3", "27.04.2014", "27.04.2014", "6.0", "", "", "", "3.0", "3.0", "3.0", "", "10.0", "10.0", "10.0" },
                { "Sprint 4", "28.04.2014", "30.04.2014", "4.0", "", "", "", "2.0", "2.0", "2.0", "", "12.0", "12.0", "12.0" },
                { "Sprint 5", "01.05.2014", "04.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "18.0", "18.0", "18.0" },
                { "Sprint 6", "05.05.2014", "11.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "24.0", "24.0", "24.0" },
                { "Sprint 7", "12.05.2014", "18.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "30.0", "30.0", "30.0" },
                { "Sprint 8", "19.05.2014", "25.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "36.0", "36.0", "36.0" } });
        assertContentOfTableView((TableView<?>) getUniqueNode("#sprintsTable"), expectedContent);
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