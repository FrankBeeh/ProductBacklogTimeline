package de.frankbeeh.productbacklogtimeline.service.diff;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class Block {
    private final String firstId;
    private final int lenght;
    private final String lastId;
    private final String precedingIdInTarget;

    public Block(String firstId, int lenght, String lastId, String precedingIdInTarget) {
        this.firstId = firstId;
        this.lenght = lenght;
        this.lastId = lastId;
        this.precedingIdInTarget = precedingIdInTarget;
    }

    public String getStartId() {
        return firstId;
    }

    public int getLenght() {
        return lenght;
    }

    public String getLastId() {
        return lastId;
    }

    public String getPrecedingIdInTarget() {
        return precedingIdInTarget;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
