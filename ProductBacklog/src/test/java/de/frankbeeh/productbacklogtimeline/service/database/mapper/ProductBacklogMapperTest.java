package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.service.database.DataBaseServiceTest;

public class ProductBacklogMapperTest extends DataBaseServiceTest {
    static final ProductBacklog PRODUCT_BACKLOG_1 = new ProductBacklog(Arrays.asList(new ProductBacklogItem("ID 1", "Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1",
            "PlannedRelease 1"), new ProductBacklogItem("ID 2", "Title 2", "Description 2", 2d, State.Todo, "Sprint 2", "Rank 2", "PlannedRelease 2")));
    static final ProductBacklog PRODUCT_BACKLOG_2 = new ProductBacklog(Arrays.asList(new ProductBacklogItem("ID 1", "Changed Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1",
            "PlannedRelease 1"), new ProductBacklogItem("ID 2", "Title 2", "Description 2", 2d, State.Todo, "Sprint 2", "Rank 2", "PlannedRelease 2")));
    private static final LocalDateTime RELEASE_FORECAST_ID_1 = LocalDateTime.now();
    private static final LocalDateTime RELEASE_FORECAST_ID_2 = RELEASE_FORECAST_ID_1.minusYears(1);
    private ProductBacklogMapper mapper;

    @Test
    public void insertAndGetOneProductBacklog() throws Exception {
        mapper.insert(RELEASE_FORECAST_ID_1, PRODUCT_BACKLOG_1);
        assertProductBacklogEquals(PRODUCT_BACKLOG_1, mapper.get(RELEASE_FORECAST_ID_1));
    }

    @Test
    public void insertAndGetTwoProductBacklogs() throws Exception {
        mapper.insert(RELEASE_FORECAST_ID_1, PRODUCT_BACKLOG_1);
        mapper.insert(RELEASE_FORECAST_ID_2, PRODUCT_BACKLOG_2);
        assertProductBacklogEquals(PRODUCT_BACKLOG_1, mapper.get(RELEASE_FORECAST_ID_1));
        assertProductBacklogEquals(PRODUCT_BACKLOG_2, mapper.get(RELEASE_FORECAST_ID_2));
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
            ProductBacklogItemMapperTest.assertProductBacklogItemEquals(expectedItems.get(index), actualItems.get(index));
        }
    }
}
