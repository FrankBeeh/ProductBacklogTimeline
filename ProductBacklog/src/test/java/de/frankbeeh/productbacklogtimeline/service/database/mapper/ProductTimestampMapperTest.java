package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.service.database.DataBaseServiceTest;

public class ProductTimestampMapperTest extends DataBaseServiceTest {
    private static final LocalDateTime LOCAL_DATE_TIME_1 = LocalDateTime.now();
    private static final LocalDateTime LOCAL_DATE_TIME_2 = LocalDateTime.now().plusYears(1);
    private static final ProductTimestamp PRODUCT_TIMESTAMP_1 = new ProductTimestamp(LOCAL_DATE_TIME_1, "Name 1", ProductBacklogMapperTest.PRODUCT_BACKLOG_1, null, null);
    private static final ProductTimestamp PRODUCT_TIMESTAMP_2 = new ProductTimestamp(LOCAL_DATE_TIME_2, "Name 2", ProductBacklogMapperTest.PRODUCT_BACKLOG_2, null, null);
    private ProductTimestampMapper mapper;

    @Test
    public void insertAndGetTwoProductTimestamp() throws Exception {
        mapper.insert(PRODUCT_TIMESTAMP_2);
        mapper.insert(PRODUCT_TIMESTAMP_1);
        assertProductTimestampEquals(PRODUCT_TIMESTAMP_1, mapper.get(PRODUCT_TIMESTAMP_1.getDateTime()));
        assertProductTimestampEquals(PRODUCT_TIMESTAMP_2, mapper.get(PRODUCT_TIMESTAMP_2.getDateTime()));
    }

    @Test
    public void getAllIds() throws Exception {
        insertAndGetTwoProductTimestamp();
        assertEquals(Arrays.asList(LOCAL_DATE_TIME_1, LOCAL_DATE_TIME_2), mapper.getAllIds());
    }

    @Before
    public void setUp() {
        mapper = new ProductTimestampMapper(getConnection());
    }

    private static void assertProductTimestampEquals(ProductTimestamp expectedProductTimestamp, ProductTimestamp actualProductTimestamp) {
        assertEquals(expectedProductTimestamp.getDateTime(), actualProductTimestamp.getDateTime());
        assertEquals(expectedProductTimestamp.getName(), actualProductTimestamp.getName());
        ProductBacklogMapperTest.assertProductBacklogEquals(expectedProductTimestamp.getProductBacklog(), actualProductTimestamp.getProductBacklog());
    }
}
