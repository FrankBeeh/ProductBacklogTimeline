package de.frankbeeh.productbacklogtimeline.service.importer;

import java.time.LocalDate;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

public class VelocityForecastFromCsvImporter extends DataFromCsvImporter<VelocityForecast> {

    private static final String EFFORT_DONE_COLUMN_NAME = "Effort Done";
    private static final String ACTUAL_CAPACITY_COLUMN_NAME = "Capacity Done";
    private static final String EFFORT_FORECAST_COLUMN_NAME = "Effort Forecast";
    private static final String CAPACITY_FORECAST_COLUMN_NAME = "Capacity Forecast";
    private static final String END_DATE_COLUMN_NAME = "End Date";
    private static final String START_DATE_COLUMN_NAME = "Start Date";
    private static final String SPRINT_COLUMN_NAME = "Sprint Name";

    @Override
    protected VelocityForecast createContainer() {
        return new VelocityForecast();
    }

    @Override
    protected void addItem(VelocityForecast container) {
        final String sprintName = getString(SPRINT_COLUMN_NAME);
        final LocalDate startDate = getLocalDate(START_DATE_COLUMN_NAME);
        final LocalDate endDate = getLocalDate(END_DATE_COLUMN_NAME);
        final Double plannedCapacity = getDouble(CAPACITY_FORECAST_COLUMN_NAME);
        final Double plannedEffort = getDouble(EFFORT_FORECAST_COLUMN_NAME);
        final Double actualCapacity = getDouble(ACTUAL_CAPACITY_COLUMN_NAME);
        final Double effortDone = getDouble(EFFORT_DONE_COLUMN_NAME);
        container.addItem(new Sprint(sprintName, startDate, endDate, plannedCapacity, plannedEffort, actualCapacity, effortDone));
    }
}
