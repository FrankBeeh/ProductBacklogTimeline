package de.frankbeeh.productbacklogtimeline.domain;

import com.google.common.annotations.VisibleForTesting;

public class DoubleByState extends NumberByState<Double> {
    public DoubleByState() {
        this(0d, 0d, 0d, 0d);
    }

    @Override
    public Double getTotalValue() {
        double totalCount = 0d;
        for (State state : State.values()) {
            totalCount += getValue(state);
        }
        return new Double(totalCount);
    }

    @VisibleForTesting
    public DoubleByState(double canceled, double todo, double inProgress, double done) {
        super(canceled, todo, inProgress, done);
    }

    public void add(State state, Double value) {
        if (value != null) {
            setValue(state, getValue(state) + value);
        }
    }
}
