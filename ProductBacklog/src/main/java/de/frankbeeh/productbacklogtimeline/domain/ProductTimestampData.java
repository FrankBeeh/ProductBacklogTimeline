package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductTimestampData {
    private final LocalDateTime dateTime;
    private final String name;
    private final NumberByState<Integer> countByState;
    private final NumberByState<Double> estimateByState;

    public ProductTimestampData(LocalDateTime dateTime, String name, NumberByState<Integer> countByState, NumberByState<Double> estimateByState) {
        this.dateTime = dateTime;
        this.name = name;
        this.countByState = countByState;
        this.estimateByState = estimateByState;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public String getName() {
        return name;
    }
    
    public Integer getCountByState(State state) {
        return countByState.getValue(state);
    }

    public Integer getTotalCount() {
        return countByState.getTotalValue();
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
    
    public Double getEstimateByState(State state) {
        return estimateByState.getValue(state);
    }

    public Double getTotalEstimate() {
        return estimateByState.getTotalValue();
    }
}
