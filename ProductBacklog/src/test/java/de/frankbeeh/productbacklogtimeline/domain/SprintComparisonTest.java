package de.frankbeeh.productbacklogtimeline.domain;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.DifferenceFormatterTest;

public class SprintComparisonTest {
    @Test
    public void getComparedState_equal() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "Todo"), new SprintComparison(newTodoSprint(), newTodoSprint()).getComparedState());
    }

    @Test
    public void getComparedState_notEqual() {
        DifferenceFormatterTest.assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Changed, "Todo\n(InProgress)"),
                new SprintComparison(newTodoSprint(), newOngoingSprint()).getComparedState());
    }

    private Sprint newOngoingSprint() {
        return new Sprint(null, null, null, null, 1d, null, null);
    }

    private Sprint newTodoSprint() {
        return new Sprint(null, null, null, null, null, null, null);
    }
}
