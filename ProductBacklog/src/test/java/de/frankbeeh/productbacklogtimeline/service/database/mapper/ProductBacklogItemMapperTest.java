package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.service.database.DatabaseServiceTest;

public class ProductBacklogItemMapperTest extends DatabaseServiceTest {
    private static final String FIRST_ID = "ID 1";
    private static final ProductBacklogItem FIRST_ITEM = new ProductBacklogItem(FIRST_ID, "Title 1", "Description 1", 1d, State.Done, "Sprint 1", "Rank 1", "Release 1");
    private static final ProductBacklogItem FIRST_ITEM_TITLE_CHANGED = new ProductBacklogItem(FIRST_ID, "Title 2", "Description 1", 1d, State.Done, "Sprint 1", "Rank 1", "Release 1");
    private ProductBacklogItemMapper mapper;

    @Test
    public void storeAndRetrieveTwoDifferentVersionsOfOneItem() throws Exception {
        mapper.insert(FIRST_ITEM);
        mapper.insert(FIRST_ITEM_TITLE_CHANGED);
        assertItemEquals(FIRST_ITEM, mapper.get(FIRST_ID, FIRST_ITEM.getHash()));
        assertItemEquals(FIRST_ITEM_TITLE_CHANGED, mapper.get(FIRST_ID, FIRST_ITEM_TITLE_CHANGED.getHash()));
    }

    @Test
    public void storeAndRetrieveTwoTimes() throws Exception {
        mapper.insert(FIRST_ITEM);
        mapper.insert(FIRST_ITEM);
        assertItemEquals(FIRST_ITEM, mapper.get(FIRST_ID, FIRST_ITEM.getHash()));
    }

    @Before
    public void setUp() {
        mapper = new ProductBacklogItemMapper(getConnection());
    }

    private void assertItemEquals(ProductBacklogItem expectedItem, ProductBacklogItem actualItem) {
        assertEquals(expectedItem.getId(), actualItem.getId());
        assertEquals(expectedItem.getTitle(), actualItem.getTitle());
        assertEquals(expectedItem.getDescription(), actualItem.getDescription());
        assertEquals(expectedItem.getEstimate(), actualItem.getEstimate());
        assertEquals(expectedItem.getState(), actualItem.getState());
        assertEquals(expectedItem.getSprint(), actualItem.getSprint());
        assertEquals(expectedItem.getRank(), actualItem.getRank());
        assertEquals(expectedItem.getPlannedRelease(), actualItem.getPlannedRelease());
    }
}
