package de.frankbeeh.productbacklogtimeline.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ComparedValue;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogDirection;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class DifferenceFormatterTest {
    private static final LocalDate END_DATE = LocalDate.of(2001, Month.JANUARY, 1);
    private static final LocalDate LATER_END_DATE = LocalDate.of(2002, Month.FEBRUARY, 2);
    private static final LocalDate EARLIER_END_DATE = LocalDate.of(2000, Month.DECEMBER, 12);
    private static final String FORMATED_END_DATE_1 = DateConverter.formatLocalDate(END_DATE);
    private static final String SPRINT_NAME_1 = "Sprint name 1";
    private static final String SPRINT_NAME_2 = "Sprint name 2";
    private static final String STRING_VALUE_1 = "Value 1";
    private static final String STRING_VALUE_2 = "Value 2";

    @Test
    public void formatTextualDifference_valueNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null), DifferenceFormatter.formatTextualDifference(null, STRING_VALUE_1));
    }

    @Test
    public void formatTextualDifference_referenceValueNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, STRING_VALUE_1 + "\n(NEW)"), DifferenceFormatter.formatTextualDifference(STRING_VALUE_1, null));
    }

    @Test
    public void formatTextualDifference_equal() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, STRING_VALUE_1), DifferenceFormatter.formatTextualDifference(STRING_VALUE_1, STRING_VALUE_1));
    }

    @Test
    public void formatTextualDifference_notEqual() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Changed, STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")"),
                DifferenceFormatter.formatTextualDifference(STRING_VALUE_1, STRING_VALUE_2));
    }

    @Test
    public void formatSprintDifference_sprintNull() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null), DifferenceFormatter.formatSprintDifference(null, sprint));
    }

    @Test
    public void formatSprintDifference_withEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, SPRINT_NAME_1 + "\n" + FORMATED_END_DATE_1 + "\n(NEW)"), DifferenceFormatter.formatSprintDifference(sprint, null));
    }

    @Test
    public void formatSprintDifference_noEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, null);
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, SPRINT_NAME_1), DifferenceFormatter.formatSprintDifference(sprint, null));
    }

    @Test
    public void formatSprintDifference_sameReferenceSprint() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, SPRINT_NAME_1 + "\n" + FORMATED_END_DATE_1), DifferenceFormatter.formatSprintDifference(sprint, sprint));
    }

    @Test
    public void formatSprintDifference_laterReferenceEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        final Sprint referenceSprint = newSprint(SPRINT_NAME_2, LATER_END_DATE);
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Earlier, SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + FORMATED_END_DATE_1 + "\n(-397d)"),
                DifferenceFormatter.formatSprintDifference(sprint, referenceSprint));
    }

    @Test
    public void formatSprintDifference_earlierReferenceEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        final Sprint referenceSprint = newSprint(SPRINT_NAME_2, EARLIER_END_DATE);
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Later, SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + FORMATED_END_DATE_1 + "\n(+20d)"),
                DifferenceFormatter.formatSprintDifference(sprint, referenceSprint));
    }

    @Test
    public void formatLocalDateDifference_referenceValueNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, FORMATED_END_DATE_1 + "\n(NEW)"), DifferenceFormatter.formatLocalDateDifference(END_DATE, null));
    }

    @Test
    public void formatDoubleDifferenceIntoComparedValue_valueNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null), DifferenceFormatter.formatDoubleDifference(null, 2d, true));
    }

    @Test
    public void formatDoubleDifferenceIntoComparedValue_referenceValueNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "   2.0\n(NEW)"), DifferenceFormatter.formatDoubleDifference(2d, null, true));
    }

    @Test
    public void formatDoubleDifferenceIntoComparedValue_equal() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "2.0"), DifferenceFormatter.formatDoubleDifference(2d, 2d, true));
    }

    @Test
    public void formatDoubleDifferenceIntoComparedValue_decrease() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Earlier, "     2.0\n(-13.0)"), DifferenceFormatter.formatDoubleDifference(2d, 15d, true));
    }

    @Test
    public void formatDoubleDifferenceIntoComparedValue_increase() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Later, "   10.0\n(+8.0)"), DifferenceFormatter.formatDoubleDifference(10d, 2d, true));
    }

    @Test
    public void formatDoubleDifferenceIntoComparedValue_shortDifference() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Earlier, "123456.0\n    (-0.7)"), DifferenceFormatter.formatDoubleDifference(123456d, 123456.7d, true));
    }

    @Test
    public void formatProductBacklogRankDifference_valueNull_referenceNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, ""), DifferenceFormatter.formatProductBacklogRankDifference(null, null));
    }
    
    @Test
    public void formatProductBacklogRankDifference_valueNull_referenceNotNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Changed, ""), DifferenceFormatter.formatProductBacklogRankDifference(null, 2));
    }


    @Test
    public void formatProductBacklogRankDifference_referenceNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "     2\n(NEW)"), DifferenceFormatter.formatProductBacklogRankDifference(2, null));
    }

    @Test
    public void formatProductBacklogRankDifference_equal() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "2"), DifferenceFormatter.formatProductBacklogRankDifference(2, 2));
    }

    @Test
    public void formatProductBacklogRankDifference_decrease() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Earlier, "     2\n(-13)"), DifferenceFormatter.formatProductBacklogRankDifference(2, 15));
    }

    @Test
    public void formatProductBacklogRankDifference_increase() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Later, "   10\n(+8)"), DifferenceFormatter.formatProductBacklogRankDifference(10, 2));
    }

    @Test
    public void formatStateDifference_valueNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, null), DifferenceFormatter.formatStateDifference(null, State.Canceled));
    }

    @Test
    public void formatStateDifference_referenceValueNull() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.New, "Canceled\n(NEW)"), DifferenceFormatter.formatStateDifference(State.Canceled, null));
    }

    @Test
    public void formatStateDifference_Equal() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Same, "Todo"), DifferenceFormatter.formatStateDifference(State.Todo, State.Todo));
    }

    @Test
    public void formatStateDifference_notEqual() throws Exception {
        assertComparedValueEquals(new ComparedValue(ProductBacklogDirection.Changed, "Todo\n(Done)"), DifferenceFormatter.formatStateDifference(State.Todo, State.Done));
    }

    private Sprint newSprint(String name, LocalDate endDate) {
        return new Sprint(name, null, endDate, null, null, null, null);
    }

    public static void assertComparedValueEquals(ComparedValue expectedComparedValue, ComparedValue actualComparedValue) {
        assertEquals(expectedComparedValue.getStyleClass(), actualComparedValue.getStyleClass());
        assertEquals(expectedComparedValue.getComparedValue(), actualComparedValue.getComparedValue());
    }
}
