package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductTimestampData {
    private final LocalDateTime dateTime;
    private final String name;
    private final NumberByState<Integer> countByState;

    public ProductTimestampData(LocalDateTime dateTime, String name, NumberByState<Integer> countByState) {
        this.dateTime = dateTime;
        this.name = name;
        this.countByState = countByState;
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
        return countByState.getTotalCount();
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
