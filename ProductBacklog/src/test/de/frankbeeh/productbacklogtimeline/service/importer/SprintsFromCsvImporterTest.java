package de.frankbeeh.productbacklogtimeline.service.importer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class SprintsFromCsvImporterTest {
    private static final String HEADER = "Sprint Name;Start Date;End Date;Capacity Forecast;Effort Forecast;Capacity Done;Effort Done\r\n";

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
        final List<SprintData> expectedSprints = Arrays.asList(new SprintData("Sprint 1", FormatUtility.parseDate("01.02.2003"), FormatUtility.parseDate("02.03.2004"), Double.parseDouble("10.5"),
                Double.parseDouble("3.5"), Double.parseDouble("8.5"), Double.parseDouble("8")), new SprintData("Sprint 2", FormatUtility.parseDate("03.04.2005"),
                FormatUtility.parseDate("04.05.2006"), Double.parseDouble("10"), Double.parseDouble("2"), null, null));

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
