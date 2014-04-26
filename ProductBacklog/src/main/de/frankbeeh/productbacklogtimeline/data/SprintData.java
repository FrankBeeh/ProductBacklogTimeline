package de.frankbeeh.productbacklogtimeline.data;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SprintData {

    private final String name;
    private final Date startDate;
    private final Date endDate;
    private final Double plannedCapacity;
    private final Double plannedEffort;
    private final Double actualCapacity;
    private final Double effortDone;

    public SprintData(String name, Date startDate, Date endDate, Double plannedCapacity, Double plannedEffort, Double actualCapacity, Double effortDone) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plannedCapacity = plannedCapacity;
        this.plannedEffort = plannedEffort;
        this.actualCapacity = actualCapacity;
        this.effortDone = effortDone;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Double getPlannedCapacity() {
        return plannedCapacity;
    }

    public Double getPlannedEffort() {
        return plannedEffort;
    }

    public Double getActualCapacity() {
        return actualCapacity;
    }

    public Double getEffortDone() {
        return effortDone;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

}
