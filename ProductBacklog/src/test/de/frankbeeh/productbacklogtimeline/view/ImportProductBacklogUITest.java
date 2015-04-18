package de.frankbeeh.productbacklogtimeline.view;

import static junit.framework.Assert.assertEquals;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

import org.junit.Test;

public class ImportProductBacklogUITest extends BaseUITest {
    private static final String VELOCITY_FORECAST_TABLE_ID = "#velocityForecastTable";
    private static final String PRODUCT_BACKLOG_TABLE_ID = "#productBacklogTable";
    private static final String SPRINT_1 = "Sprint 1\n25.04.2014";
    private static final String SPRINT_2 = "Sprint 2\n26.04.2014";
    private static final String SPRINT_3 = "Sprint 3\n27.04.2014";
    private static final String SPRINT_4 = "Sprint 4\n30.04.2014";
    private static final String SPRINT_5 = "Sprint 5\n04.05.2014";
    private static final String SPRINT_6 = "Sprint 6\n11.05.2014";
    private static final String SPRINT_7 = "Sprint 7\n18.05.2014";
    private static final String PBL_FILE_1 = "PBL1.csv";
    private static final String PBL_FILE_2 = "PBL2.csv";
    private static final String VELOCITY_FORECAST_FILE_1 = "VelocityForecast1.csv";
    private static final String VELOCITY_FORECAST_FILE_2 = "VelocityForecast2.csv";

    private static final TableViewContent PBL_1 = new TableViewContent(new String[][] { { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "", "", "" },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "3.0", "Sprint 1", "", "", "" }, { "3", "5.0", "Todo", "PBI 3", "Description 3", "8.0", "", "", "", "" },
            { "4", "8.0", "Todo", "PBI 4", "Description 4", "16.0", "", "", "", "" } });

    private static final TableViewContent PBL_1_WITH_FORECAST_1 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "3", "5.0", "Todo", "PBI 3", "Description 3", "8.0", "", SPRINT_3, SPRINT_3, SPRINT_3 }, { "4", "8.0", "Todo", "PBI 4", "Description 4", "16.0", "", SPRINT_5, SPRINT_5, SPRINT_5 } });

    private static final TableViewContent PBL_1_COMPARED_TO_PBL_2 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "3", "5.0", "Todo", "PBI 3", "Description 3", "     8.0\n(-13.0)", "", "Sprint 3\n(Sprint 4)\n27.04.2014\n(-3d)", "Sprint 3\n(Sprint 4)\n27.04.2014\n(-3d)", SPRINT_3 },
            { "4", "8.0", "Todo", "PBI 4", "Description 4", "    16.0\n(-10.0)", "", SPRINT_5, SPRINT_5, "Sprint 5\n(Sprint 4)\n04.05.2014\n(+4d)" } });

    private static final TableViewContent PBL_2_WITH_FORECAST_1 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "5", "13.0", "Done", "PBI 5", "Description 5", "16.0", "Sprint 2", SPRINT_5, SPRINT_5, SPRINT_5 },
            { "3", "5.0", "Todo", "PBI 3", "Description 3", "21.0", "", SPRINT_6, SPRINT_6, SPRINT_6 }, { "4", "5.0", "Todo", "PBI 4", "Description 4", "26.0", "", SPRINT_7, SPRINT_7, SPRINT_7 },
            { "6", "2.0", "Todo", "PBI 6", "Description 6", "28.0", "", SPRINT_7, SPRINT_7, SPRINT_7 } });

    private static final TableViewContent PBL_2_WITH_FORECAST_2 = new TableViewContent(new String[][] {
            // FIXME: The PBIs are not ordered in the content, but in the UI it's ok.
            { "4", "5.0", "Todo", "PBI 4", "Description 4", "26.0", "", SPRINT_5, SPRINT_5, SPRINT_4 }, { "6", "2.0", "Todo", "PBI 6", "Description 6", "28.0", "", SPRINT_6, SPRINT_5, SPRINT_5 },
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "5", "13.0", "Done", "PBI 5", "Description 5", "16.0", "Sprint 2", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "3", "5.0", "Todo", "PBI 3", "Description 3", "21.0", "", SPRINT_4, SPRINT_4, SPRINT_3 } });

    private static final TableViewContent PBL_2_COMPARED_TO_PBL_1 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "3.0", "Sprint 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "5", "13.0", "Done", "PBI 5", "Description 5", "16.0", "Sprint 2", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "3", "5.0", "Todo", "PBI 3", "Description 3", "    21.0\n(+13.0)", "", "Sprint 4\n(Sprint 3)\n30.04.2014\n(+3d)", "Sprint 4\n(Sprint 3)\n30.04.2014\n(+3d)", SPRINT_3 },
            { "4", "5.0", "Todo", "PBI 4", "Description 4", "    26.0\n(+10.0)", "", SPRINT_5, SPRINT_5, "Sprint 4\n(Sprint 5)\n30.04.2014\n(-4d)" },
            { "6", "2.0", "Todo", "PBI 6", "Description 6", "28.0", "", SPRINT_6, SPRINT_5, SPRINT_5 } });

    private static final TableViewContent VELOCITY_FORECAST_1 = new TableViewContent(new String[][] { { "Sprint 1", "25.04.2014", "25.04.2014", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "8.0", "", "", "", "4.0", "4.0", "4.0", "", "7.0", "7.0", "7.0" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "6.0", "", "", "", "3.0", "3.0", "3.0", "", "10.0", "10.0", "10.0" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "4.0", "", "", "", "2.0", "2.0", "2.0", "", "12.0", "12.0", "12.0" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "18.0", "18.0", "18.0" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "24.0", "24.0", "24.0" },
            { "Sprint 7", "12.05.2014", "18.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "30.0", "30.0", "30.0" },
            { "Sprint 8", "19.05.2014", "25.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "36.0", "36.0", "36.0" } });
    
    private static final TableViewContent VELOCITY_FORECAST_2 = new TableViewContent(new String[][] { { "Sprint 1", "25.04.2014", "25.04.2014", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "8.0", "13.0", "12.0", "13.0", "6.0", "9.5", "13.0", "", "", "", "" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "6.0", "", "", "", "3.0", "4.8", "6.5", "", "19.0", "20.8", "22.5" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "4.0", "", "", "", "2.0", "3.2", "4.3", "", "21.0", "24.0", "26.8" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "27.0", "33.5", "39.8" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "33.0", "43.0", "52.8" },
            { "Sprint 7", "12.05.2014", "18.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "39.0", "52.5", "65.8" },
            { "Sprint 8", "19.05.2014", "25.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "45.0", "62.0", "78.8" } });
    
    @Test
    public void selectReleaseForecast() throws Exception {
        importProductBacklog(PBL_FILE_1);
        assertEquals(PBL_FILE_1, getSelectedReleaseForecast());
        selectProductBacklogTab();
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_1);
        
        importSprint(VELOCITY_FORECAST_FILE_1);
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_1_WITH_FORECAST_1);
        
        importProductBacklog(PBL_FILE_2);
        assertEquals(PBL_FILE_2, getSelectedReleaseForecast());
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_2_WITH_FORECAST_1);
        
        importSprint(VELOCITY_FORECAST_FILE_2);
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_2_WITH_FORECAST_2);
        
        referenceReleaseForecast(PBL_FILE_2);
        selectReleaseForecast(PBL_FILE_1);
        selectProductBacklogTab();
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_1_COMPARED_TO_PBL_2);
        selectVelocityForecastTab();
        assertContentOfTableView(VELOCITY_FORECAST_TABLE_ID, VELOCITY_FORECAST_1);

        referenceReleaseForecast(PBL_FILE_1);
        selectReleaseForecast(PBL_FILE_2);
        selectProductBacklogTab();
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_2_COMPARED_TO_PBL_1);
        selectVelocityForecastTab();
        assertContentOfTableView(VELOCITY_FORECAST_TABLE_ID, VELOCITY_FORECAST_2);
    }

    @Test
    public void cancelImportBacklog() throws Exception {
        openPBLImportDialog();
        closeCurrentWindow();
    }

    private String getSelectedReleaseForecast() {
        return getSelectedReleaseForecastComboBox().getSelectionModel().getSelectedItem();
    }

    private void selectReleaseForecast(final String releaseForecastName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getSelectedReleaseForecastComboBox().getSelectionModel().select(releaseForecastName);
            }
        });
    }

    private void referenceReleaseForecast(final String releaseForecastName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getReferencedReleaseForecastComboBox().getSelectionModel().select(releaseForecastName);
            }
        });
    }

    private ComboBox<String> getSelectedReleaseForecastComboBox() {
        return getUniqueNode("#selectedReleaseForecast");
    }

    private ComboBox<String> getReferencedReleaseForecastComboBox() {
        return getUniqueNode("#referencedReleaseForecast");
    }

    private void openPBLImportDialog() {
        clickOn("File");
        clickOn("Import PBL");
    }

    private void importProductBacklog(String fileName) {
        openPBLImportDialog();
        enterFileName(fileName);
    }
}
