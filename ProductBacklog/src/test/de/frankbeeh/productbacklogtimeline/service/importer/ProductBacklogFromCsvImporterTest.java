package de.frankbeeh.productbacklogtimeline.service.importer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.State;

public class ProductBacklogFromCsvImporterTest {

    private static final String HEADER = "Key;Summary;Description;Story Points;Resolution;Sprint;Rank\r\n";
    private DataFromCsvImporter<ProductBacklog> importer;

    @Test
    public void importEmptyProductBacklog() throws Exception {
        final ProductBacklog productBacklog = importer.importData(new StringReader(""));
        final List<ProductBacklogItem> items = productBacklog.getItems();
        assertTrue(items.isEmpty());
    }

    @Test
    public void importMultipleProductBacklogItems() throws Exception {
        final ProductBacklog productBacklog = importer.importData(new StringReader(HEADER + "Id 1;Titel 1;Beschreibung 1;0.5;Fixed;Sprint 1;09\r\n"
                + "Id 2;Titel 2;Beschreibung 2;1;Unresolved;Sprint 2;10\r\n"));
        assertEquals(
                Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5, State.Done, "Sprint 1", "09"),
                        new ProductBacklogItem("Id 2", "Titel 2", "Beschreibung 2", 1d, State.Todo, "Sprint 2", "10")).toString(), productBacklog.getItems().toString());
    }

    @Test
    public void importProductBacklogItemInDifferentRowOrder() throws Exception {
        final ProductBacklog productBacklog = importer.importData(new StringReader("Rank;Sprint;Resolution;Story Points;Description;Summary;Key\r\n"
                + "10;Sprint 1;Won't Fix;0.5;Beschreibung 1;Titel 1;Id 1\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5, State.Canceled, "Sprint 1", "10")).toString(), productBacklog.getItems().toString());
    }

    @Test
    public void importProductBacklogItemEmptyValues() throws Exception {
        final ProductBacklog productBacklog = importer.importData(new StringReader(HEADER + ";;;;;;\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("", "", "", null, null, "", "")).toString(), productBacklog.getItems().toString());
    }

    @Test
    public void importProductBacklogItemMultiLineDescription() throws Exception {
        final ProductBacklog productBacklog = importer.importData(new StringReader(HEADER + "Id 1;Titel 1;\"Zeile 1\nZeile 2\";0.5;Unresolved;Sprint 1;10\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Zeile 1\nZeile 2", 0.5, State.Todo, "Sprint 1", "10")).toString(), productBacklog.getItems().toString());
    }

    @Before
    public void setUp() {
        importer = new ProductBacklogFromCsvImporter();
    }
}
