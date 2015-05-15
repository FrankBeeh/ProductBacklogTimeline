package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.DifferenceFormatterTest;

public class SprintComparisonTest {
    private static final LocalDate END_DATE = LocalDate.now();

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
        return new Sprint(null, null, END_DATE, null, 1d, null, null);
    }

    private Sprint newTodoSprint() {
        return new Sprint(null, null, END_DATE, null, null, null, null);
    }
}
