package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Responsibility:
 * <ul>
 * <li>Contain a read-only representation of the data of a {@link Sprint} that is stored in the data base.
 * </ul>
 */
public class SprintData {
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Double capacityForecast;
    private final Double effortForecast;
    private final Double capacityDone;
    private final Double effortDone;

    public SprintData(String name, LocalDate startDate, LocalDate endDate, Double capacityForecast, Double effortForecast, Double capacityDone, Double effortDone) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacityForecast = capacityForecast;
        this.effortForecast = effortForecast;
        this.capacityDone = capacityDone;
        this.effortDone = effortDone;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Double getCapacityForecast() {
        return capacityForecast;
    }

    public Double getEffortForecast() {
        return effortForecast;
    }

    public Double getCapacityDone() {
        return capacityDone;
    }

    public Double getEffortDone() {
        return effortDone;
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}