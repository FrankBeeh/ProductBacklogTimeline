package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductTimestampData {
    private final LocalDateTime dateTime;
    private final String name;

    public ProductTimestampData(LocalDateTime dateTime, String name) {
        this.dateTime = dateTime;
        this.name = name;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
