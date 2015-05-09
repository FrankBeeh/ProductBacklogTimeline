package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Strings;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

/**
 * Responsibility:
 * <ul>
 * <li>Represent the data a sprint:
 * <ul>
 * <li>Data that is independent from previous sprints.
 * <li>Data that is dependent of other sprints.
 * </ul>
 * </ul>
 */
public class Sprint {
    private final SprintData data;
    private final Map<String, Double> progressForecastBasedOnHistory;
    private final Map<String, Double> accumulatedProgressForecastBasedOnHistory;
    private Double accumulatedEffortDone;

    public Sprint(String name, LocalDate startDate, LocalDate endDate, Double capacityForecast, Double effortForecast, Double capacityDone, Double effortDone) {
        this.data = new SprintData(name, startDate, endDate, capacityForecast, effortForecast, capacityDone, effortDone);
        this.progressForecastBasedOnHistory = new HashMap<String, Double>();
        this.accumulatedProgressForecastBasedOnHistory = new HashMap<String, Double>();
    }

    public String getName() {
        return data.getName();
    }

    public LocalDate getStartDate() {
        return data.getStartDate();
    }

    public LocalDate getEndDate() {
        return data.getEndDate();
    }

    public Double getCapacityForecast() {
        return data.getCapacityForecast();
    }

    public Double getEffortForecast() {
        return data.getEffortForecast();
    }

    public Double getCapacityDone() {
        return data.getCapacityDone();
    }

    public Double getEffortDone() {
        return data.getEffortDone();
    }

    public void setAccumulatedEffortDone(Double accumulatedEffortDone) {
        this.accumulatedEffortDone = accumulatedEffortDone;
    }

    public Double getAccumulatedEffortDone() {
        return accumulatedEffortDone;
    }

    public void setProgressForecastBasedOnHistory(String progressForecastName, Double progressForecast) {
        progressForecastBasedOnHistory.put(progressForecastName, roundToOneDecimal(progressForecast));
    }

    public Double getProgressForecastBasedOnHistory(String progressForecastName) {
        return progressForecastBasedOnHistory.get(progressForecastName);
    }

    public void setAccumulatedProgressForecastBasedOnHistory(String progressForecastName, Double progressForecast) {
        accumulatedProgressForecastBasedOnHistory.put(progressForecastName, roundToOneDecimal(progressForecast));
    }

    public Double getAccumulatedProgressForecastBasedOnHistory(String progressForecastName) {
        return accumulatedProgressForecastBasedOnHistory.get(progressForecastName);
    }

    public void accept(SprintDataVisitor visitor) {
        visitor.visit(this);
    }

    public Double getAccumulatedEffortDoneOrProgressForcast(String progressForecastName) {
        final Double accumulatedEffortDone = getAccumulatedEffortDone();
        if (accumulatedEffortDone != null) {
            return accumulatedEffortDone;
        }
        return getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
    }


    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private Double roundToOneDecimal(Double value) {
        if (value == null) {
            return value;
        }
        return Math.round(value.doubleValue() * 10.0) / 10.0;
    }

    public State getState() {
        if (getEffortDone() != null) {
            return State.Done;
        }
        if (getEffortForecast() != null) {
            return State.InProgress;
        }
        return State.Todo;
    }

    public String getHash() {
        final HashFunction hashFunction = Hashing.sha1();
        final Hasher hasher = hashFunction.newHasher().putUnencodedChars(Strings.nullToEmpty(getName()));
        hashDate(hasher, getStartDate());
        hashDate(hasher, getEndDate());
        hashDouble(hasher, getCapacityForecast());
        hashDouble(hasher, getEffortForecast());
        hashDouble(hasher, getCapacityDone());
        hashDouble(hasher, getEffortDone());
        return hasher.hash().toString();
    }

    private void hashDouble(final Hasher hasher, Double value) {
        if (value == null) {
            hasher.putChar('-');
        } else {
            hasher.putDouble(value);
        }
    }

    private void hashDate(final Hasher hasher, final LocalDate localDate) {
        if (localDate == null) {
            hasher.putChar('-');
        } else {
            hasher.putLong(localDate.toEpochDay());
        }
    }
}
