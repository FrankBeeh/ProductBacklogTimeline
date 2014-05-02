package de.frankbeeh.productbacklogtimeline.service.importer;

import java.text.ParseException;
import java.util.Date;

import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

public class SprintsFromCsvImporter extends DataFromCsvImporter<Sprints> {

    private static final String EFFORT_DONE_COLUMN_NAME = "EffortDone";
    private static final String ACTUAL_CAPACITY_COLUMN_NAME = "CapacityDone";
    private static final String EFFORT_FORECAST_COLUMN_NAME = "EffortForecast";
    private static final String CAPACITY_FORECAST_COLUMN_NAME = "CapacityForecast";
    private static final String END_DATE_COLUMN_NAME = "EndDate";
    private static final String START_DATE_COLUMN_NAME = "StartDate";
    private static final String SPRINT_COLUMN_NAME = "SprintName";

    @Override
    protected Sprints createContainer() {
        return new Sprints();
    }

    @Override
    protected void addItem(Sprints container) throws ParseException {
        final String sprintName = getString(SPRINT_COLUMN_NAME);
        final Date startDate = getDate(START_DATE_COLUMN_NAME);
        final Date endDate = getDate(END_DATE_COLUMN_NAME);
        final Double plannedCapacity = getDouble(CAPACITY_FORECAST_COLUMN_NAME);
        final Double plannedEffort = getDouble(EFFORT_FORECAST_COLUMN_NAME);
        final Double actualCapacity = getDouble(ACTUAL_CAPACITY_COLUMN_NAME);
        final Double effortDone = getDouble(EFFORT_DONE_COLUMN_NAME);
        container.addItem(new SprintData(sprintName, startDate, endDate, plannedCapacity, plannedEffort, actualCapacity, effortDone));
    }
}
