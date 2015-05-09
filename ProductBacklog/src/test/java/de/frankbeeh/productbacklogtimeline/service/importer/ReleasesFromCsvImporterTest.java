package de.frankbeeh.productbacklogtimeline.service.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.Releases;
import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;
import de.frankbeeh.productbacklogtimeline.service.criteria.ProductBacklogItemIdIsEqual;

public class ReleasesFromCsvImporterTest {
    private static final String RELEASE_NAME_1 = "Release 1";
    private static final String RELEASE_NAME_2 = "Release 2";
    private static final String CRITERIA_1 = "plannedRelease=Release 1";
    private static final String CRITERIA_2 = "idOfPBI=ID 2";
    private static final String HEADER = "Release;Criteria\r\n";
    private DataFromCsvImporter<Releases> importer;

    @Test
    public void importEmptyReleases() throws Exception {
        final Releases releases = importer.importData(new StringReader(""));
        final List<Release> listOfReleases = releases.getReleases();
        assertTrue(listOfReleases.isEmpty());
    }

    @Test
    public void importMultipleReleases() throws Exception {
        final Releases releases = importer.importData(new StringReader(HEADER + RELEASE_NAME_1 + ";" + CRITERIA_1 + ";\r\n" + RELEASE_NAME_2 + ";" + CRITERIA_2 + ";\r\n"));
        assertEquals(Arrays.asList(new Release(RELEASE_NAME_1, new PlannedReleaseIsEqual(RELEASE_NAME_1)), new Release(RELEASE_NAME_2, new ProductBacklogItemIdIsEqual("ID 2"))).toString(),
                releases.getReleases().toString());
    }

    @Test
    public void importEmptyCriteria() throws Exception {
        try {
            importer.importData(new StringReader(HEADER + RELEASE_NAME_1 + ";;\r\n"));
            fail("IllegalArgumentException expected!");
        } catch (IllegalArgumentException exception) {
            assertEquals("Unknown criteria ''!", exception.getMessage());
        }
    }

    @Before
    public void setUp() {
        importer = new ReleasesFromCsvImporter();
    }
}
