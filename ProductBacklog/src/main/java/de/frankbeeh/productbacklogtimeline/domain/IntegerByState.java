package de.frankbeeh.productbacklogtimeline.domain;

import com.google.common.annotations.VisibleForTesting;

public class IntegerByState extends NumberByState<Integer> {
    public IntegerByState() {
        this(0, 0, 0, 0);
    }

    @Override
    public Integer getTotalCount() {
        int totalCount = 0;
        for (State state : State.values()) {
            totalCount += getValue(state);
        }
        return new Integer(totalCount);
    }

    @VisibleForTesting
    public IntegerByState(int canceled, int todo, int inProgress, int done) {
        super(canceled, todo, inProgress, done);
    }
}
