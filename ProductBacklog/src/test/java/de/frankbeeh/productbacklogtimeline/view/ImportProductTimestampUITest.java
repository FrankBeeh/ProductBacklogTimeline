package de.frankbeeh.productbacklogtimeline.view;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class ImportProductTimestampUITest extends BaseUITest {
    private static final String SPRINT_5_TO_SPRINT_4 = "Sprint 5\n(Sprint 4)\n04.05.2014\n(+4d)";
    private static final String SPRINT_3_TO_SPRINT_4 = "Sprint 3\n(Sprint 4)\n27.04.2014\n(-3d)";
    private static final String SPRINT_1 = "Sprint 1\n25.04.2014";
    private static final String SPRINT_2 = "Sprint 2\n26.04.2014";
    private static final String SPRINT_3 = "Sprint 3\n27.04.2014";
    private static final String SPRINT_4 = "Sprint 4\n30.04.2014";
    private static final String SPRINT_5 = "Sprint 5\n04.05.2014";
    private static final String SPRINT_6 = "Sprint 6\n11.05.2014";
    private static final LocalDate TIMESTAMP_DATE_1 = LocalDate.of(2001, Month.JANUARY, 1);
    private static final LocalDate TIMESTAMP_DATE_2 = LocalDate.of(2002, Month.FEBRUARY, 2);
    private static final String TIMESTAMP_NAME_1 = "Name 1";
    private static final String TIMESTAMP_NAME_2 = "Name 2";
    private static final String PBL_FILE_1 = "PBL1.csv";
    private static final String PBL_FILE_2 = "PBL2.csv";
    private static final String VELOCITY_FORECAST_FILE_1 = "VelocityForecast1.csv";
    private static final String VELOCITY_FORECAST_FILE_2 = "VelocityForecast2.csv";
    private static final String RELEASES_FILE_1 = "Releases1.csv";
    private static final String RELEASES_FILE_2 = "Releases2.csv";

    private static final TableViewContent PBL_1_WITH_FORECAST_1 = new TableViewContent(new String[][] {
            { "1", "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "2", "2", "1.0", "Todo", "PBI 2", "Description 2", "4.0", "Sprint 1", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "3", "3", "5.0", "Todo", "PBI 3", "Description 3", "9.0", "", "Release 1", SPRINT_3, SPRINT_3, SPRINT_3 },
            { "4", "4", "8.0", "Todo", "PBI 4", "Description 4", "17.0", "", "Release 1", SPRINT_5, SPRINT_5, SPRINT_5 } });

    private static final TableViewContent PBL_1_COMPARED_TO_2 = new TableViewContent(
            new String[][] {
                    { "1", "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
                    { "2", "    2\n(-1)", "1.0", "Todo\n(Canceled)", "PBI 2", "Description 2", "     4.0\n(-12.0)", "Sprint 1\n(No Sprint)", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
                    { "3", "    3\n(-1)", "5.0", "Todo", "PBI 3\n(New PBI 3)", "Description 3\n(New Description 3)", "     9.0\n(-12.0)", "", "Release 1\n(Release 2)", SPRINT_3_TO_SPRINT_4, SPRINT_3,
                            SPRINT_3 },
                    { "4", "    4\n(-1)", "    8.0\n(+3.0)", "Todo", "PBI 4", "Description 4", "   17.0\n(-9.0)", "", "Release 1\n(Release 2)", SPRINT_5, SPRINT_5, SPRINT_5_TO_SPRINT_4 } });

    private static final TableViewContent PBL_2_WITH_FORECAST_2 = new TableViewContent(new String[][] {
            { "1", "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", SPRINT_1, SPRINT_1, SPRINT_1 },
            { "5", "2", "13.0", "Done", "PBI 5", "Description 5", "16.0", "Sprint 2", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "2", "3", "1.0", "Canceled", "PBI 2", "Description 2", "16.0", "No Sprint", "Release 1", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "3", "4", "5.0", "Todo", "New PBI 3", "New Description 3", "21.0", "", "Release 2", SPRINT_4, SPRINT_3, SPRINT_3 },
            { "4", "5", "5.0", "Todo", "PBI 4", "Description 4", "26.0", "", "Release 2", SPRINT_5, SPRINT_5, SPRINT_4 },
            { "6", "6", "2.0", "Todo", "PBI 6", "Description 6", "28.0", "", "Release 2", SPRINT_6, SPRINT_5, SPRINT_5 } });

    private static final TableViewContent PBL_2_COMPARED_TO_1 = new TableViewContent(new String[][] {
            { "1", "1", "3.0", "Done", "PBI 1", "Description 1", "3.0", "Sprint 1", "Release 1", "Sprint 1\n25.04.2014", "Sprint 1\n25.04.2014", "Sprint 1\n25.04.2014" },
            { "5", "     2\n(NEW)", "  13.0\n(NEW)", "Done\n(NEW)", "PBI 5\n(NEW)", "Description 5\n(NEW)", "  16.0\n(NEW)", "Sprint 2\n(NEW)", "Release 1\n(NEW)", "Sprint 2\n26.04.2014",
                    "Sprint 2\n26.04.2014", "Sprint 2\n26.04.2014" },
            { "2", "    3\n(+1)", "1.0", "Canceled\n(Todo)", "PBI 2", "Description 2", "    16.0\n(+12.0)", "No Sprint\n(Sprint 1)", "Release 1", "Sprint 2\n26.04.2014", "Sprint 2\n26.04.2014",
                    "Sprint 2\n26.04.2014" },
            { "3", "    4\n(+1)", "5.0", "Todo", "New PBI 3\n(PBI 3)", "New Description 3\n(Description 3)", "    21.0\n(+12.0)", "", "Release 2\n(Release 1)",
                    "Sprint 4\n(Sprint 3)\n30.04.2014\n(+3d)", "Sprint 3\n27.04.2014", "Sprint 3\n27.04.2014" },
            { "4", "    5\n(+1)", "    5.0\n(-3.0)", "Todo", "PBI 4", "Description 4", "   26.0\n(+9.0)", "", "Release 2\n(Release 1)", "Sprint 5\n04.05.2014", "Sprint 5\n04.05.2014",
                    "Sprint 4\n(Sprint 5)\n30.04.2014\n(-4d)" },
            { "6", "     6\n(NEW)", "   2.0\n(NEW)", "Todo\n(NEW)", "PBI 6\n(NEW)", "Description 6\n(NEW)", "  28.0\n(NEW)", "\n(NEW)", "Release 2\n(NEW)", "Sprint 6\n11.05.2014",
                    "Sprint 5\n04.05.2014", "Sprint 5\n04.05.2014" } });

    private static final TableViewContent VELOCITY_FORECAST_1 = new TableViewContent(new String[][] {
            { "Sprint 1", "25.04.2014", "25.04.2014", "Done", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "3.0", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "Todo", "8.0", "", "", "", "4.0", "4.0", "4.0", "", "7.0", "7.0", "7.0" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "Todo", "6.0", "", "", "", "3.0", "3.0", "3.0", "", "10.0", "10.0", "10.0" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "Todo", "4.0", "", "", "", "2.0", "2.0", "2.0", "", "12.0", "12.0", "12.0" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "Todo", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "18.0", "18.0", "18.0" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "Todo", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "24.0", "24.0", "24.0" },
            { "Sprint 7", "12.05.2014", "18.05.2014", "Todo", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "30.0", "30.0", "30.0" },
            { "Sprint 8", "19.05.2014", "25.05.2014", "Todo", "12.0", "", "", "", "6.0", "6.0", "6.0", "", "36.0", "36.0", "36.0" } });

    private static final TableViewContent VELOCITY_FORECAST_2 = new TableViewContent(new String[][] {
            { "Sprint 1", "25.04.2014", "25.04.2014", "Done", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "3.0", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "Done", "8.0", "13.0", "12.0", "13.0", "6.0", "9.5", "13.0", "16.0", "", "", "" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "InProgress", "6.0", "6.5", "", "", "3.0", "5.3", "6.5", "", "19.0", "21.3", "22.5" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "Todo", "4.0", "", "", "", "2.0", "3.6", "4.3", "", "21.0", "24.9", "26.8" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "Todo", "12.0", "", "", "", "6.0", "10.7", "13.0", "", "27.0", "35.6", "39.8" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "Todo", "14.0", "", "", "", "7.0", "12.4", "15.2", "", "34.0", "48.0", "55.0" },
            { "Sprint 7", "12.05.2014", "19.05.2014", "Todo", "12.0", "", "", "", "6.0", "10.7", "13.0", "", "40.0", "58.7", "68.0" },
            { "Sprint 8 New", "20.05.2014", "25.05.2014", "Todo", "12.0", "", "", "", "6.0", "10.7", "13.0", "", "46.0", "69.4", "81.0" } });

    private static final TableViewContent VELOCITY_FORECAST_1_COMPARED_TO_2 = new TableViewContent(
            new String[][] {
                    { "Sprint 1", "25.04.2014", "25.04.2014", "Done", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "3.0", "", "", "" },
                    { "Sprint 2", "26.04.2014", "26.04.2014", "Todo\n(Done)", "8.0", "", "", "", "    4.0\n(-2.0)", "    4.0\n(-5.5)", "    4.0\n(-9.0)", "", "   7.0\n(NEW)", "   7.0\n(NEW)",
                            "   7.0\n(NEW)" },
                    { "Sprint 3", "27.04.2014", "27.04.2014", "Todo\n(InProgre...", "6.0", "", "", "", "3.0", "    3.0\n(-2.3)", "    3.0\n(-3.5)", "", "   10.0\n(-9.0)", "    10.0\n(-11.3)",
                            "    10.0\n(-12.5)" },
                    { "Sprint 4", "28.04.2014", "30.04.2014", "Todo", "4.0", "", "", "", "2.0", "    2.0\n(-1.6)", "    2.0\n(-2.3)", "", "   12.0\n(-9.0)", "    12.0\n(-12.9)", "    12.0\n(-14.8)" },
                    { "Sprint 5", "01.05.2014", "04.05.2014", "Todo", "12.0", "", "", "", "6.0", "    6.0\n(-4.7)", "    6.0\n(-7.0)", "", "   18.0\n(-9.0)", "    18.0\n(-17.6)", "    18.0\n(-21.8)" },
                    { "Sprint 6", "05.05.2014", "11.05.2014", "Todo", "   12.0\n(-2.0)", "", "", "", "    6.0\n(-1.0)", "    6.0\n(-6.4)", "    6.0\n(-9.2)", "", "    24.0\n(-10.0)",
                            "    24.0\n(-24.0)", "    24.0\n(-31.0)" },
                    { "Sprint 7\n(NEW)", "12.05.2014\n(NEW)", "18.05.2014\n(NEW)", "Todo", "  12.0\n(NEW)", "", "", "", "   6.0\n(NEW)", "   6.0\n(NEW)", "   6.0\n(NEW)", "", "  30.0\n(NEW)",
                            "  30.0\n(NEW)", "  30.0\n(NEW)" },
                    { "Sprint 8\n(Sprint 8 New)", "19.05.2014\n(-1d)", "25.05.2014", "Todo", "12.0", "", "", "", "6.0", "    6.0\n(-4.7)", "    6.0\n(-7.0)", "", "    36.0\n(-10.0)",
                            "    36.0\n(-33.4)", "    36.0\n(-45.0)" } });

    private static final TableViewContent VELOCITY_FORECAST_2_COMPARED_TO_1 = new TableViewContent(new String[][] {
            { "Sprint 1", "25.04.2014", "25.04.2014", "Done", "4.0", "4.0", "6.0", "3.0", "3.0", "3.0", "3.0", "3.0", "", "", "" },
            { "Sprint 2", "26.04.2014", "26.04.2014", "Done\n(Todo)", "8.0", "  13.0\n(NEW)", "  12.0\n(NEW)", "  13.0\n(NEW)", "    6.0\n(+2.0)", "    9.5\n(+5.5)", "   13.0\n(+9.0)",
                    "  16.0\n(NEW)", "", "", "" },
            { "Sprint 3", "27.04.2014", "27.04.2014", "InProgress\n(Todo)", "6.0", "   6.5\n(NEW)", "", "", "3.0", "    5.3\n(+2.3)", "    6.5\n(+3.5)", "", "   19.0\n(+9.0)", "    21.3\n(+11.3)",
                    "    22.5\n(+12.5)" },
            { "Sprint 4", "28.04.2014", "30.04.2014", "Todo", "4.0", "", "", "", "2.0", "    3.6\n(+1.6)", "    4.3\n(+2.3)", "", "   21.0\n(+9.0)", "    24.9\n(+12.9)", "    26.8\n(+14.8)" },
            { "Sprint 5", "01.05.2014", "04.05.2014", "Todo", "12.0", "", "", "", "6.0", "   10.7\n(+4.7)", "   13.0\n(+7.0)", "", "   27.0\n(+9.0)", "    35.6\n(+17.6)", "    39.8\n(+21.8)" },
            { "Sprint 6", "05.05.2014", "11.05.2014", "Todo", "   14.0\n(+2.0)", "", "", "", "    7.0\n(+1.0)", "   12.4\n(+6.4)", "   15.2\n(+9.2)", "", "    34.0\n(+10.0)", "    48.0\n(+24.0)",
                    "    55.0\n(+31.0)" },
            { "Sprint 7\n(NEW)", "12.05.2014\n(NEW)", "19.05.2014\n(NEW)", "Todo", "  12.0\n(NEW)", "", "", "", "   6.0\n(NEW)", "  10.7\n(NEW)", "  13.0\n(NEW)", "", "  40.0\n(NEW)",
                    "  58.7\n(NEW)", "  68.0\n(NEW)" },
            { "Sprint 8 New\n(Sprint 8)", "20.05.2014\n(+1d)", "25.05.2014", "Todo", "12.0", "", "", "", "6.0", "   10.7\n(+4.7)", "   13.0\n(+7.0)", "", "    46.0\n(+10.0)", "    69.4\n(+33.4)",
                    "    81.0\n(+45.0)" } });

    private static final TableViewContent RELEASES_1 = new TableViewContent(new String[][] { { "Release 1", "plannedRelease=\nRelease 1", "17.0", SPRINT_5, SPRINT_5, SPRINT_5 },
            { "Release 2", "idOfPBI=2", "4.0", SPRINT_2, SPRINT_2, SPRINT_2 } });

    private static final TableViewContent RELEASES_2 = new TableViewContent(new String[][] { { "Release 1", "plannedRelease=\nRelease 1", "16.0", SPRINT_2, SPRINT_2, SPRINT_2 },
            { "Release 2", "plannedRelease=\nRelease 2", "28.0", SPRINT_6, SPRINT_5, SPRINT_5 } });

    @Test
    public void selectProductTimestamp() throws Exception {
        getMenuAccessor().openProductTimelineImportDialog().enter(TIMESTAMP_NAME_1, TIMESTAMP_DATE_1, PBL_FILE_1, VELOCITY_FORECAST_FILE_1, RELEASES_FILE_1);
        getMainAccessor().assertSelectedProductTimestampEquals(TIMESTAMP_NAME_1, TIMESTAMP_DATE_1);
        getMainAccessor().selectProductBacklogTab(this);
        getMainAccessor().assertContentOfProductBacklogTableView(PBL_1_WITH_FORECAST_1);
        getMainAccessor().assertContentOfVelocityForecastTableView(VELOCITY_FORECAST_1);
        getMainAccessor().assertContentOfReleasesTableView(RELEASES_1);

        getMenuAccessor().openProductTimelineImportDialog().enter(TIMESTAMP_NAME_2, TIMESTAMP_DATE_2, PBL_FILE_2, VELOCITY_FORECAST_FILE_2, RELEASES_FILE_2);
        getMainAccessor().assertSelectedProductTimestampEquals(TIMESTAMP_NAME_2, TIMESTAMP_DATE_2);
        getMainAccessor().assertContentOfProductBacklogTableView(PBL_2_WITH_FORECAST_2);
        getMainAccessor().assertContentOfVelocityForecastTableView(VELOCITY_FORECAST_2);
        getMainAccessor().assertContentOfReleasesTableView(RELEASES_2);

        getMainAccessor().referenceProductTimestamp(TIMESTAMP_NAME_2, TIMESTAMP_DATE_2);
        getMainAccessor().selectProductTimestamp(TIMESTAMP_NAME_1, TIMESTAMP_DATE_1);
        getMainAccessor().selectProductBacklogTab(this);
        getMainAccessor().assertContentOfProductBacklogTableView(PBL_1_COMPARED_TO_2);
        getMainAccessor().selectVelocityForecastTab(this);
        getMainAccessor().assertContentOfVelocityForecastTableView(VELOCITY_FORECAST_1_COMPARED_TO_2);

        getMainAccessor().referenceProductTimestamp(TIMESTAMP_NAME_1, TIMESTAMP_DATE_1);
        getMainAccessor().selectProductTimestamp(TIMESTAMP_NAME_2, TIMESTAMP_DATE_2);
        getMainAccessor().selectProductBacklogTab(this);
        getMainAccessor().assertContentOfProductBacklogTableView(PBL_2_COMPARED_TO_1);
        getMainAccessor().selectVelocityForecastTab(this);
        getMainAccessor().assertContentOfVelocityForecastTableView(VELOCITY_FORECAST_2_COMPARED_TO_1);
    }
}
