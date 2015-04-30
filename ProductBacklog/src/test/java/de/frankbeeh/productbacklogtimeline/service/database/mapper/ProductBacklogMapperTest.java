package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.service.database.DatabaseServiceTest;

public class ProductBacklogMapperTest extends DatabaseServiceTest {
    private static final int RELEASE_FORECAST_ID_2 = 2;
    private static final int RELEASE_FORECAST_ID_1 = 1;
    private ProductBacklogMapper mapper;

    @Test
    public void insertAndGetOneProductBacklog() throws Exception {
        final ProductBacklog productBacklog = new ProductBacklog(Arrays.asList(new ProductBacklogItem("ID 1", "Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1", "PlannedRelease 1"),
                new ProductBacklogItem("ID 2", "Title 2", "Description 2", 2d, State.Todo, "Sprint 2", "Rank 2", "PlannedRelease 2")));
        mapper.insert(RELEASE_FORECAST_ID_1, productBacklog);
        assertItemEquals(productBacklog, mapper.get(RELEASE_FORECAST_ID_1));
    }

    @Test
    public void insertAndGetTwoProductBacklogs() throws Exception {
        final ProductBacklog productBacklog1 = new ProductBacklog(Arrays.asList(new ProductBacklogItem("ID 1", "Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1", "PlannedRelease 1"),
                new ProductBacklogItem("ID 2", "Title 2", "Description 2", 2d, State.Todo, "Sprint 2", "Rank 2", "PlannedRelease 2")));
        final ProductBacklog productBacklog2 = new ProductBacklog(Arrays.asList(new ProductBacklogItem("ID 1", "Changed Title 1", "Description 1", 1d, State.Todo, "Sprint 1", "Rank 1", "PlannedRelease 1"),
                new ProductBacklogItem("ID 2", "Title 2", "Description 2", 2d, State.Todo, "Sprint 2", "Rank 2", "PlannedRelease 2")));
        mapper.insert(RELEASE_FORECAST_ID_1, productBacklog1);
        mapper.insert(RELEASE_FORECAST_ID_2, productBacklog2);
        assertItemEquals(productBacklog1, mapper.get(RELEASE_FORECAST_ID_1));
        assertItemEquals(productBacklog2, mapper.get(RELEASE_FORECAST_ID_2));
    }

    @Before
    public void setUp() {
        mapper = new ProductBacklogMapper(getConnection());
    }

    private void assertItemEquals(ProductBacklog expectedProductBacklog, ProductBacklog actualProductBacklog) {
        assertNotNull(actualProductBacklog);
        assertEquals(expectedProductBacklog.size(), actualProductBacklog.size());
    }
}
