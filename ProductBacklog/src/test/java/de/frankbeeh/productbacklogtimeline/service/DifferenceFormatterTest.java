package de.frankbeeh.productbacklogtimeline.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;

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
        assertNull(DifferenceFormatter.formatTextualDifference(null, STRING_VALUE_1));
    }

    @Test
    public void formatTextualDifference_referenceValueNull() throws Exception {
        assertEquals(STRING_VALUE_1, DifferenceFormatter.formatTextualDifference(STRING_VALUE_1, null));
    }

    @Test
    public void formatTextualDifference_equal() throws Exception {
        assertEquals(STRING_VALUE_1, DifferenceFormatter.formatTextualDifference(STRING_VALUE_1, STRING_VALUE_1));
    }

    @Test
    public void formatTextualDifference_notEqual() throws Exception {
        assertEquals(STRING_VALUE_1 + "\n(" + STRING_VALUE_2 + ")", DifferenceFormatter.formatTextualDifference(STRING_VALUE_1, STRING_VALUE_2));
    }

    @Test
    public void formatSprintDifference_sprintNull() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        assertNull(DifferenceFormatter.formatSprintDifference(null, sprint));
    }
    
    @Test
    public void formatSprintDifference_withEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        assertEquals(SPRINT_NAME_1 + "\n" + FORMATED_END_DATE_1, DifferenceFormatter.formatSprintDifference(sprint, null));
    }

    @Test
    public void formatSprintDifference_noEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, null);
        assertEquals(SPRINT_NAME_1, DifferenceFormatter.formatSprintDifference(sprint, null));
    }

    @Test
    public void formatSprintDifference_sameReferenceSprint() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        assertEquals(SPRINT_NAME_1 + "\n" + FORMATED_END_DATE_1, DifferenceFormatter.formatSprintDifference(sprint, sprint));
    }

    @Test
    public void formatSprintDifference_laterReferenceEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        final Sprint referenceSprint = newSprint(SPRINT_NAME_2, LATER_END_DATE);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + FORMATED_END_DATE_1 + "\n(-397d)", DifferenceFormatter.formatSprintDifference(sprint, referenceSprint));
    }

    @Test
    public void formatSprintDifference_earlierReferenceEndDate() throws Exception {
        final Sprint sprint = newSprint(SPRINT_NAME_1, END_DATE);
        final Sprint referenceSprint = newSprint(SPRINT_NAME_2, EARLIER_END_DATE);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + FORMATED_END_DATE_1 + "\n(+20d)", DifferenceFormatter.formatSprintDifference(sprint, referenceSprint));
    }
    
    @Test
    public void formatLocalDateDifference_referenceValueNull() throws Exception {
        assertEquals(FORMATED_END_DATE_1, DifferenceFormatter.formatLocalDateDifference(END_DATE, null));
    }
    
    @Test
    public void formatDoubleDifference_valueNull() throws Exception {
        assertNull(DifferenceFormatter.formatDoubleDifference(null, 2d));
    }

    @Test
    public void formatDoubleDifference_referenceValueNull() throws Exception {
        assertEquals("   2.0\n(NEW)", DifferenceFormatter.formatDoubleDifference(2d, null));
    }

    @Test
    public void formatDoubleDifference_equal() throws Exception {
        assertEquals("2.0", DifferenceFormatter.formatDoubleDifference(2d, 2d));
    }

    @Test
    public void formatDoubleDifference_decrease() throws Exception {
        assertEquals("     2.0\n(-13.0)", DifferenceFormatter.formatDoubleDifference(2d, 15d));
    }

    @Test
    public void formatDoubleDifference_increase() throws Exception {
        assertEquals("   10.0\n(+8.0)", DifferenceFormatter.formatDoubleDifference(10d, 2d));
    }

    @Test
    public void formatDoubleDifference_shortDifference() throws Exception {
        assertEquals("123456.0\n    (-0.7)", DifferenceFormatter.formatDoubleDifference(123456d, 123456.7d));
    }

    @Test
    public void getComparedProductBacklogRank_referenceNull() throws Exception {
        assertEquals("     2\n(NEW)", DifferenceFormatter.formatProductBacklogRankDifference(2, null));
    }

    @Test
    public void getComparedProductBacklogRank_equal() throws Exception {
        assertEquals("2", DifferenceFormatter.formatProductBacklogRankDifference(2, 2));
    }

    @Test
    public void getComparedProductBacklogRank_decrease() throws Exception {
        assertEquals("     2\n(-13)", DifferenceFormatter.formatProductBacklogRankDifference(2, 15));
    }

    @Test
    public void getComparedProductBacklogRank_increase() throws Exception {
        assertEquals("   10\n(+8)", DifferenceFormatter.formatProductBacklogRankDifference(10, 2));
    }
    
    private Sprint newSprint(String name, LocalDate endDate) {
        return new Sprint(name, null, endDate, null, null, null, null);
    }
}
