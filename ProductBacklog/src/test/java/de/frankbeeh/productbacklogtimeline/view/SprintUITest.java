package de.frankbeeh.productbacklogtimeline.view;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class SprintUITest extends BaseUITest {
    private static final LocalDate TIMESTAMP_DATE_1 = LocalDate.of(2001, Month.JANUARY, 1);
    private static final String TIMESTAMP_NAME_1 = "Name 1";
    private static final String PBL_FILE_1 = "PBL1.csv";
    private static final String VELOCITY_FORECAST_FILE_1 = "VelocityForecast1.csv";
    private static final String RELEASES_FILE_1 = "Releases1.csv";

    private static final TableViewContent VELOCITY_FORECAST_1 = new TableViewContent(new String[][] { { "Sprint 1", "25.04.2014", "25.04.2014", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "3.0", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "8.0", "", "", "", "4.0", "4.0", "4.0", "", "7.0", "7.0", "7.0" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "6.0", "", "", "", "3.0", "3.0", "3.0", "", "10.0", "10.0", "10.0" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "4.0", "", "", "", "2.0", "2.0", "2.0", "", "12.0", "12.0", "12.0" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "18.0", "18.0", "18.0" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "24.0", "24.0", "24.0" },
            { "Sprint 7", "12.05.2014", "18.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "30.0", "30.0", "30.0" },
            { "Sprint 8", "19.05.2014", "25.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "36.0", "36.0", "36.0" } });

    @Test
    public void importAndEditSprintClickSave() throws Exception {
        getMenuAccessor().openProductTimelineImportDialog().enter(TIMESTAMP_NAME_1, TIMESTAMP_DATE_1, PBL_FILE_1, VELOCITY_FORECAST_FILE_1, RELEASES_FILE_1);
        getMainAccessor().selectVelocityForecastTab(this);
        getMainAccessor().assertContentOfVelocityForecastTableView(VELOCITY_FORECAST_1);
        editSprint(getFirstSprint());
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