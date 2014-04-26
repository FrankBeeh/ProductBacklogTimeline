package de.frankbeeh.productbacklogtimeline.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogFromCsvImporterTest {

    private static final String HEADER = "Id;Title;Summary;Estimate\r\n";
    private ProductBacklogFromCsvImporter importer;

    @Test
    public void importEmptyProductBacklog() throws Exception {
        final ProductBacklog productBacklog = importer.importProductBacklog(new StringReader(""));
        final List<ProductBacklogItem> items = productBacklog.getItems();
        assertTrue(items.isEmpty());
    }

    @Test
    public void importMultipleProductBacklogItems() throws Exception {
        final ProductBacklog productBacklog = importer.importProductBacklog(new StringReader(HEADER + "Id 1;Titel 1;Beschreibung 1;0.5\r\nId 2;Titel 2;Beschreibung 2;1\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5), new ProductBacklogItem("Id 2", "Titel 2", "Beschreibung 2", 1d)).toString(),
                productBacklog.getItems().toString());
    }

    @Test
    public void importProductBacklogItemInDifferentRowOrder() throws Exception {
        final ProductBacklog productBacklog = importer.importProductBacklog(new StringReader("Estimate;Summary;Title;Id\r\n" + "0.5;Beschreibung 1;Titel 1;Id 1\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5)).toString(), productBacklog.getItems().toString());
    }

    @Test
    public void importProductBacklogItemEmptyValues() throws Exception {
        final ProductBacklog productBacklog = importer.importProductBacklog(new StringReader(HEADER + ";;;\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("", "", "", null)).toString(), productBacklog.getItems().toString());
    }

    @Test
    public void importProductBacklogItemMultiLineDescription() throws Exception {
        final ProductBacklog productBacklog = importer.importProductBacklog(new StringReader(HEADER + "Id 1;Titel 1;\"Zeile 1\nZeile 2\";0.5\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Zeile 1\nZeile 2", 0.5)).toString(), productBacklog.getItems().toString());
    }

    @Before
    public void setUp() {
        importer = new ProductBacklogFromCsvImporter();
    }
}
