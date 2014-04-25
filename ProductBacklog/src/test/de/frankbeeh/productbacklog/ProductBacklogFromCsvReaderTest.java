package de.frankbeeh.productbacklog;

import static junit.framework.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

public class ProductBacklogFromCsvReaderTest {

    private static final String HEADER = "Id;Title;Summary;Estimate\r\n";
    private ProductBacklogFromCsvReader csvReader;

    @Test
    public void readEmptyProductBacklog() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(""));
        assertEquals(0, productBacklog.size());
    }

    @Test
    public void readMultipleProductBacklogItems() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(HEADER + "Id 1;Titel 1;Beschreibung 1;0.5\r\nId 2;Titel 2;Beschreibung 2;1\r\n"));
        assertEquals(2, productBacklog.size());
        assertProductBacklogItemEquals(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5), productBacklog.getItem(0));
        assertProductBacklogItemEquals(new ProductBacklogItem("Id 2", "Titel 2", "Beschreibung 2", 1d), productBacklog.getItem(1));
    }

    @Test
    public void readProductBacklogItemInDifferentRowOrder() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader("Estimate;Summary;Title;Id\r\n" + "0.5;Beschreibung 1;Titel 1;Id 1\r\n"));
        assertEquals(1, productBacklog.size());
        assertProductBacklogItemEquals(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5), productBacklog.getItem(0));
    }

    @Test
    public void readProductBacklogItemEmptyValues() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(HEADER + ";;;\r\n"));
        assertEquals(1, productBacklog.size());
        assertProductBacklogItemEquals(new ProductBacklogItem("", "", "", null), productBacklog.getItem(0));
    }

    @Test
    public void readProductBacklogItemMultiLineDescription() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(HEADER + "Id 1;Titel 1;\"Zeile 1\nZeile 2\";0.5\r\n"));
        assertEquals(1, productBacklog.size());
        assertProductBacklogItemEquals(new ProductBacklogItem("Id 1", "Titel 1", "Zeile 1\nZeile 2", 0.5), productBacklog.getItem(0));
    }

    @Before
    public void setUp() {
        csvReader = new ProductBacklogFromCsvReader();
    }

    private void assertProductBacklogItemEquals(ProductBacklogItem expectedItem, final ProductBacklogItem actualItem) {
        assertEquals(expectedItem.toString(), actualItem.toString());
    }

}
