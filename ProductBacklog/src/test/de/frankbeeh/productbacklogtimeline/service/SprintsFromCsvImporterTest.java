package de.frankbeeh.productbacklogtimeline.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

public class SprintsFromCsvImporterTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final String HEADER = "SprintName;StartDate;EndDate;PlannedCapacity;PlannedEffort;ActualCapacity;EffortDone\r\n";

    private DataFromCsvImporter<Sprints> importer;

    @Test
    public void importSprintsEmpty() throws Exception {
        final Sprints sprints = importer.importData(new StringReader(""));
        assertTrue(sprints.getSprints().isEmpty());
    }

    @Test
    public void importSprintsOnlyHeader() throws Exception {
        final Sprints sprints = importer.importData(new StringReader(HEADER));
        assertTrue(sprints.getSprints().isEmpty());
    }

    @Test
    public void importMultipleSprints() throws Exception {
        final List<SprintData> expectedSprints = Arrays.asList(
                new SprintData("Sprint 1", DATE_FORMAT.parse("01.02.2003"), DATE_FORMAT.parse("02.03.2004"), Double.parseDouble("10.5"), Double.parseDouble("3.5"), Double.parseDouble("8.5"),
                        Double.parseDouble("8")), new SprintData("Sprint 2", DATE_FORMAT.parse("03.04.2005"), DATE_FORMAT.parse("04.05.2006"), Double.parseDouble("10"), Double.parseDouble("2"), null,
                        null));

        final Sprints sprints = importer.importData(new StringReader(HEADER + "Sprint 1;01.02.2003;02.03.2004;10.5;3.5;8.5;8\r\n" + "Sprint 2;03.04.2005;04.05.2006;10;2;;\r\n"));
        assertEquals(expectedSprints.toString(), sprints.getSprints().toString());
    }

    @Test
    public void importEmptySprints() throws Exception {
        final Sprints sprints = importer.importData(new StringReader(HEADER + ";;;;;;\r\n"));
        assertEquals(Arrays.asList(new SprintData("", null, null, null, null, null, null)).toString(), sprints.getSprints().toString());
    }

    @Before
    public void setUp() {
        importer = new SprintsFromCsvImporter();
    }
}
