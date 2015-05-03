package de.frankbeeh.productbacklogtimeline.view;

import static org.junit.Assert.assertEquals;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

import org.junit.Test;

public class ImportProductBacklogUITest extends BaseUITest {
    private static final String SPRINT_4_TO_SPRINT_5 = "Sprint 4\n(Sprint 5)\n30.04.2014\n(-4d)";
    private static final String SPRINT_4_TO_SPRINT_3 = "Sprint 4\n(Sprint 3)\n30.04.2014\n(+3d)";
    private static final String SPRINT_5_TO_SPRINT_4 = "Sprint 5\n(Sprint 4)\n04.05.2014\n(+4d)";
    private static final String SPRINT_3_TO_SPRINT_4 = "Sprint 3\n(Sprint 4)\n27.04.2014\n(-3d)";
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

    private static final TableViewContent PBL_1 = new TableViewContent(new String[][] { { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", "", "", "" },
            { "2", "1.0", "Todo", "PBI 2", "Description 2", "4.0", "Sprint 1", "Release 1", "", "", "" }, { "3", "5.0", "Todo", "PBI 3", "Description 3", "9.0", "", "Release 1", "", "", "" },
            { "4", "8.0", "Todo", "PBI 4", "Description 4", "17.0", "", "Release 1", "", "", "" } });

    private static final TableViewContent PBL_1_WITH_FORECAST_1 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "1.0", "Todo", "PBI 2", "Description 2", "4.0", "Sprint 1", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "3", "5.0", "Todo", "PBI 3", "Description 3", "9.0", "", "Release 1", SPRINT_3, SPRINT_3, SPRINT_3 }, { "4", "8.0", "Todo", "PBI 4", "Description 4", "17.0", "", "Release 1", SPRINT_5, SPRINT_5, SPRINT_5 } });

    private static final TableViewContent PBL_1_COMPARED_TO_PBL_2 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "1.0", "Todo\n(Canceled)", "PBI 2", "Description 2", "     4.0\n(-12.0)", "Sprint 1\n(No Sprint)", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2},
            { "3", "5.0", "Todo", "PBI 3\n(New PBI 3)", "Description 3\n(New Description 3)", "     9.0\n(-12.0)", "", "Release 1\n(Release 2)", SPRINT_3_TO_SPRINT_4, SPRINT_3_TO_SPRINT_4, SPRINT_3 },
            { "4", "    8.0\n(+3.0)", "Todo", "PBI 4", "Description 4", "   17.0\n(-9.0)", "", "Release 1\n(Release 2)", SPRINT_5, SPRINT_5, SPRINT_5_TO_SPRINT_4 } });

    private static final TableViewContent PBL_2_WITH_FORECAST_1 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "5", "13.0", "Done", "PBI 5", "Description 5", "16.0", "Sprint 2", "Release 1", SPRINT_5, SPRINT_5, SPRINT_5 },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "16.0", "No Sprint", "Release 1", SPRINT_5, SPRINT_5, SPRINT_5 },
            { "3", "5.0", "Todo", "New PBI 3", "New Description 3", "21.0", "", "Release 2", SPRINT_6, SPRINT_6, SPRINT_6 }, { "4", "5.0", "Todo", "PBI 4", "Description 4", "26.0", "", "Release 2", SPRINT_7, SPRINT_7, SPRINT_7 },
            { "6", "2.0", "Todo", "PBI 6", "Description 6", "28.0", "", "Release 2", SPRINT_7, SPRINT_7, SPRINT_7 } });

    private static final TableViewContent PBL_2_WITH_FORECAST_2 = new TableViewContent(new String[][] {
            // FIXME: The PBIs are not ordered in the content, but in the UI it's ok.
            { "4", "5.0", "Todo", "PBI 4", "Description 4", "26.0", "", "Release 2", SPRINT_5, SPRINT_5, SPRINT_4 }, { "6", "2.0", "Todo", "PBI 6", "Description 6", "28.0", "", "Release 2", SPRINT_6, SPRINT_5, SPRINT_5 },
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "5", "13.0", "Done", "PBI 5", "Description 5", "16.0", "Sprint 2", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "2", "1.0", "Canceled", "PBI 2", "Description 2", "16.0", "No Sprint", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "3", "5.0", "Todo", "New PBI 3", "New Description 3", "21.0", "", "Release 2", SPRINT_4, SPRINT_4, SPRINT_3 } });

    private static final TableViewContent PBL_2_COMPARED_TO_PBL_1 = new TableViewContent(new String[][] {
            { "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "5", "13.0", "Done", "PBI 5", "Description 5", "16.0", "Sprint 2", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "2", "1.0", "Canceled\n(Todo)", "PBI 2", "Description 2", "    16.0\n(+12.0)", "No Sprint\n(Sprint 1)", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "3", "5.0", "Todo", "New PBI 3\n(PBI 3)", "New Description 3\n(Description 3)", "    21.0\n(+12.0)", "", "Release 2\n(Release 1)", SPRINT_4_TO_SPRINT_3, SPRINT_4_TO_SPRINT_3, SPRINT_3 },
            { "4", "    5.0\n(-3.0)", "Todo", "PBI 4", "Description 4", "   26.0\n(+9.0)", "", "Release 2\n(Release 1)", SPRINT_5, SPRINT_5, SPRINT_4_TO_SPRINT_5 },
            { "6", "2.0", "Todo", "PBI 6", "Description 6", "28.0", "", "Release 2", SPRINT_6, SPRINT_5, SPRINT_5 } });

    private static final TableViewContent VELOCITY_FORECAST_1 = new TableViewContent(new String[][] {
            { "Sprint 1", "25.04.2014", "25.04.2014", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "3.0", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "8.0", "", "", "", "4.0", "4.0", "4.0", "", "7.0", "7.0", "7.0" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "6.0", "", "", "", "3.0", "3.0", "3.0", "", "10.0", "10.0", "10.0" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "4.0", "", "", "", "2.0", "2.0", "2.0", "", "12.0", "12.0", "12.0" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "18.0", "18.0", "18.0" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "24.0", "24.0", "24.0" },
            { "Sprint 7", "12.05.2014", "18.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "30.0", "30.0", "30.0" },
            { "Sprint 8", "19.05.2014", "25.05.2014", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "36.0", "36.0", "36.0" } });

    private static final TableViewContent VELOCITY_FORECAST_2 = new TableViewContent(new String[][] {
            { "Sprint 1", "25.04.2014", "25.04.2014", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "3.0", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "8.0", "13.0", "12.0", "13.0", "6.0", "9.5", "13.0", "16.0", "", "", "" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "6.0", "", "", "", "3.0", "4.8", "6.5", "", "19.0", "20.8", "22.5" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "4.0", "", "", "", "2.0", "3.2", "4.3", "", "21.0", "24.0", "26.8" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "27.0", "33.5", "39.8" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "33.0", "43.0", "52.8" },
            { "Sprint 7", "12.05.2014", "18.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "39.0", "52.5", "65.8" },
            { "Sprint 8", "19.05.2014", "25.05.2014", "12.0", "", "", "", "6.0", "9.5", "13.0", "", "45.0", "62.0", "78.8" } });

    @Test
    public void selectProductTimestamp() throws Exception {
        importProductBacklog(PBL_FILE_1);
        assertEquals(PBL_FILE_1, getSelectedProductTimestamp());
        selectProductBacklogTab();
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_1);

        importSprint(VELOCITY_FORECAST_FILE_1);
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_1_WITH_FORECAST_1);

        importProductBacklog(PBL_FILE_2);
        assertEquals(PBL_FILE_2, getSelectedProductTimestamp());
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_2_WITH_FORECAST_1);

        importSprint(VELOCITY_FORECAST_FILE_2);
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_2_WITH_FORECAST_2);

        referenceProductTimestamp(PBL_FILE_2);
        selectProductTimestamp(PBL_FILE_1);
        selectProductBacklogTab();
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, PBL_1_COMPARED_TO_PBL_2);
        selectVelocityForecastTab();
        assertContentOfTableView(VELOCITY_FORECAST_TABLE_ID, VELOCITY_FORECAST_1);

        referenceProductTimestamp(PBL_FILE_1);
        selectProductTimestamp(PBL_FILE_2);
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

    private String getSelectedProductTimestamp() {
        return getSelectedProductTimestampComboBox().getSelectionModel().getSelectedItem();
    }

    private void selectProductTimestamp(final String productTimestampName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getSelectedProductTimestampComboBox().getSelectionModel().select(productTimestampName);
            }
        });
    }

    private void referenceProductTimestamp(final String productTimestamp) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getReferencedProductTimestampComboBox().getSelectionModel().select(productTimestamp);
            }
        });
    }

    private ComboBox<String> getSelectedProductTimestampComboBox() {
        return getUniqueNode("#selectedProductTimestamp");
    }

    private ComboBox<String> getReferencedProductTimestampComboBox() {
        return getUniqueNode("#referencedProductTimestamp");
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
