package de.frankbeeh.productbacklogtimeline;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProductBacklogFromCsvReaderTest {

    private static final String HEADER = "Id;Title;Summary;Estimate\r\n";
    private ProductBacklogFromCsvReader csvReader;

    @Test
    public void readEmptyProductBacklog() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(""));
        final List<ProductBacklogItem> items = productBacklog.getItems();
        assertTrue(items.isEmpty());
    }

    @Test
    public void readMultipleProductBacklogItems() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(HEADER + "Id 1;Titel 1;Beschreibung 1;0.5\r\nId 2;Titel 2;Beschreibung 2;1\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5), new ProductBacklogItem("Id 2", "Titel 2", "Beschreibung 2", 1d)).toString(),
                productBacklog.getItems().toString());
    }

    @Test
    public void readProductBacklogItemInDifferentRowOrder() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader("Estimate;Summary;Title;Id\r\n" + "0.5;Beschreibung 1;Titel 1;Id 1\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Beschreibung 1", 0.5)).toString(), productBacklog.getItems().toString());
    }

    @Test
    public void readProductBacklogItemEmptyValues() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(HEADER + ";;;\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("", "", "", null)).toString(), productBacklog.getItems().toString());
    }

    @Test
    public void readProductBacklogItemMultiLineDescription() throws Exception {
        final ProductBacklog productBacklog = csvReader.readProductBacklog(new StringReader(HEADER + "Id 1;Titel 1;\"Zeile 1\nZeile 2\";0.5\r\n"));
        assertEquals(Arrays.asList(new ProductBacklogItem("Id 1", "Titel 1", "Zeile 1\nZeile 2", 0.5)).toString(), productBacklog.getItems().toString());
    }

    @Before
    public void setUp() {
        csvReader = new ProductBacklogFromCsvReader();
    }
}
