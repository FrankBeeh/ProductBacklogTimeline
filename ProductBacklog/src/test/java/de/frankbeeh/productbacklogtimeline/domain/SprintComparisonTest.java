package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class SprintComparisonTest {
    @Test
    public void getComparedState_referenceNull() {
        assertEquals("Todo", new SprintComparison(newTodoSprint(), null).getComparedState());
    }
    
    @Test
    public void getComparedState_referenceValueNull() {
        assertEquals("Todo", new SprintComparison(newTodoSprint()).getComparedState());
    }
    
    @Test
    public void getComparedState_equal() {
        assertEquals("Todo", new SprintComparison(newTodoSprint(), newTodoSprint()).getComparedState());
    }

    @Test
    public void getComparedState_notEqual() {
        assertEquals("Todo\n(InProgress)", new SprintComparison(newTodoSprint(), newOngoingSprint()).getComparedState());
    }

    private Sprint newOngoingSprint() {
        return new Sprint(null, null, null, null, 1d, null, null);
    }

    private Sprint newTodoSprint() {
        return new Sprint(null, null, null, null, null, null, null);
    }
}
