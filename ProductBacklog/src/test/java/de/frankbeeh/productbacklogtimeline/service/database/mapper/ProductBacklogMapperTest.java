package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.service.database.DataBaseServiceTest;

public class ProductBacklogMapperTest extends DataBaseServiceTest {
    static final ProductBacklog PRODUCT_BACKLOG_1 = newProductBacklog(newFirstProductBacklogItem(), newSecondProductBacklogItem());
    static final ProductBacklog PRODUCT_BACKLOG_2 = newProductBacklog(newFirstProductBacklogItem_titleChanged(), newSecondProductBacklogItem());
    private ProductBacklogMapper mapper;

    @Test
    public void storeAndRetrieve_twoPRoductBacklogs_havingDifferentVersionsOfFirstItem() throws Exception {
        mapper.insert(ProductTimestampMapperTest.RELEASE_FORECAST_ID_1, PRODUCT_BACKLOG_1);
        mapper.insert(ProductTimestampMapperTest.RELEASE_FORECAST_ID_2, PRODUCT_BACKLOG_2);
        assertProductBacklogEquals(PRODUCT_BACKLOG_1, mapper.get(ProductTimestampMapperTest.RELEASE_FORECAST_ID_1));
        assertProductBacklogEquals(PRODUCT_BACKLOG_2, mapper.get(ProductTimestampMapperTest.RELEASE_FORECAST_ID_2));
    }

    @Before
    public void setUp() {
        mapper = new ProductBacklogMapper(getConnection());
    }

    static void assertProductBacklogEquals(ProductBacklog expectedProductBacklog, ProductBacklog actualProductBacklog) {
        assertNotNull(actualProductBacklog);
        assertEquals(expectedProductBacklog.size(), actualProductBacklog.size());
        final List<ProductBacklogItem> actualItems = actualProductBacklog.getItems();
        final List<ProductBacklogItem> expectedItems = expectedProductBacklog.getItems();
        for (int index = 0; index < expectedItems.size(); index++) {
            assertProductBacklogItemEquals(expectedItems.get(index), actualItems.get(index));
        }
    }

    private static void assertProductBacklogItemEquals(ProductBacklogItem expectedItem, ProductBacklogItem actualItem) {
        assertEquals(expectedItem.getId(), actualItem.getId());
        assertEquals(expectedItem.getTitle(), actualItem.getTitle());
        assertEquals(expectedItem.getDescription(), actualItem.getDescription());
        assertEquals(expectedItem.getEstimate(), actualItem.getEstimate());
        assertEquals(expectedItem.getState(), actualItem.getState());
        assertEquals(expectedItem.getJiraSprint(), actualItem.getJiraSprint());
        assertEquals(expectedItem.getJiraRank(), actualItem.getJiraRank());
        assertEquals(expectedItem.getPlannedRelease(), actualItem.getPlannedRelease());
    }

    private static ProductBacklog newProductBacklog(ProductBacklogItem... items) {
        return new ProductBacklog(Arrays.asList(items));
    }

    private static ProductBacklogItem newSecondProductBacklogItem() {
        return new ProductBacklogItem("ID 2", "Title 2", "Description 2", 2d, State.Todo, "Sprint 2", "Rank 2", "PlannedRelease 2");
    }

    private static ProductBacklogItem newFirstProductBacklogItem() {
        return new ProductBacklogItem("ID 1", "Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1", "PlannedRelease 1");
    }

    private static ProductBacklogItem newFirstProductBacklogItem_titleChanged() {
        return new ProductBacklogItem("ID 1", "Changed Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1", "PlannedRelease 1");
    }
}
