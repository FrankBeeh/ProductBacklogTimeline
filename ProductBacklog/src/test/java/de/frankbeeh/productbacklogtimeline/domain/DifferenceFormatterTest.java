package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class DifferenceFormatterTest {
    private static final String SPRINT_NAME_1 = "Sprint name 1";
    private static final String SPRINT_NAME_2 = "Sprint name 2";

    @Test
    public void getDescription_withEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final Sprint sprint = new Sprint(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n" + endDate, DifferenceFormatter.formatSprintDifference(sprint, null));
    }

    @Test
    public void getDescription_noEndDate() throws Exception {
        final Sprint sprint = newSprintDataWithName(SPRINT_NAME_1);
        assertEquals(SPRINT_NAME_1, DifferenceFormatter.formatSprintDifference(sprint, null));
    }

    @Test
    public void getDescription_sameReferenceSprint() throws Exception {
        final String endDate = "01.02.2003";
        final Sprint sprint = new Sprint(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n" + endDate, DifferenceFormatter.formatSprintDifference(sprint, sprint));
    }

    @Test
    public void getDescription_laterReferenceEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final String referenceEndDate = "02.02.2003";
        final Sprint sprint = new Sprint(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        final Sprint referenceSprint = new Sprint(SPRINT_NAME_2, null, FormatUtility.parseLocalDate(referenceEndDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + endDate + "\n(-1d)", DifferenceFormatter.formatSprintDifference(sprint, referenceSprint));
    }

    @Test
    public void getDescription_earlierReferenceEndDate() throws Exception {
        final String endDate = "01.02.2003";
        final String referenceEndDate = "01.01.2003";
        final Sprint sprint = new Sprint(SPRINT_NAME_1, null, FormatUtility.parseLocalDate(endDate), null, null, null, null);
        final Sprint referenceSprint = new Sprint(SPRINT_NAME_2, null, FormatUtility.parseLocalDate(referenceEndDate), null, null, null, null);
        assertEquals(SPRINT_NAME_1 + "\n(" + SPRINT_NAME_2 + ")\n" + endDate + "\n(+31d)", DifferenceFormatter.formatSprintDifference(sprint, referenceSprint));
    }

    private Sprint newSprintDataWithName(String name) {
        return new Sprint(name, null, null, null, null, null, null);
    }
}
