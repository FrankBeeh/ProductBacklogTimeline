package de.frankbeeh.productbacklogtimeline.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.annotations.VisibleForTesting;

public abstract class NumberByState<T extends Number> {
    private final Map<State, T> countByValue = new HashMap<State, T>();

    public NumberByState() {
    }

    @VisibleForTesting
    public NumberByState(T canceled, T todo, T inProgress, T done) {
        setValue(State.Canceled, canceled);
        setValue(State.Todo, todo);
        setValue(State.InProgress, inProgress);
        setValue(State.Done, done);
    }

    public T getValue(State state) {
        return countByValue.get(state);
    }

    public abstract void add(State state, T value);
    
    public abstract T getTotalValue();

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    protected void setValue(State state, T value) {
        countByValue.put(state, value);
    }
}
