package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.Releases;
import de.frankbeeh.productbacklogtimeline.service.criteria.PlannedReleaseIsEqual;
import de.frankbeeh.productbacklogtimeline.service.criteria.ProductBacklogItemIdIsEqual;
import de.frankbeeh.productbacklogtimeline.service.database.DataBaseServiceTest;

public class ProductTimestampMapperTest extends DataBaseServiceTest {
    private static final Releases RELEASES_1 = new Releases(Arrays.asList(new Release("Rlease 1", new PlannedReleaseIsEqual("Release 1")), new Release("Rlease 2", new PlannedReleaseIsEqual(
            "Release 2"))), null);
    private static final Releases RELEASES_2 = new Releases(Arrays.asList(new Release("Rlease 1", new ProductBacklogItemIdIsEqual("PBI 1")), new Release("Rlease 2", new PlannedReleaseIsEqual(
            "Release 2"))), null);
    static final LocalDateTime RELEASE_FORECAST_ID_1 = LocalDateTime.of(2001, Month.JANUARY, 1, 1, 1);
    static final LocalDateTime RELEASE_FORECAST_ID_2 = LocalDateTime.of(2002, Month.FEBRUARY, 2, 2, 2);
    private static final ProductTimestamp PRODUCT_TIMESTAMP_1 = new ProductTimestamp(RELEASE_FORECAST_ID_1, "Name 1", ProductBacklogMapperTest.PRODUCT_BACKLOG_1,
            VelocityForecastMapperTest.VELOCITY_FORECAST_1, RELEASES_1);
    private static final ProductTimestamp PRODUCT_TIMESTAMP_2 = new ProductTimestamp(RELEASE_FORECAST_ID_2, "Name 2", ProductBacklogMapperTest.PRODUCT_BACKLOG_2,
            VelocityForecastMapperTest.VELOCITY_FORECAST_2, RELEASES_2);
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
        assertEquals(Arrays.asList(RELEASE_FORECAST_ID_1, RELEASE_FORECAST_ID_2), mapper.getAllIds());
    }

    @Before
    public void setUp() {
        mapper = new ProductTimestampMapper(getConnection());
    }

    private static void assertProductTimestampEquals(ProductTimestamp expectedProductTimestamp, ProductTimestamp actualProductTimestamp) {
        assertEquals(expectedProductTimestamp.getDateTime(), actualProductTimestamp.getDateTime());
        assertEquals(expectedProductTimestamp.getName(), actualProductTimestamp.getName());
        ProductBacklogMapperTest.assertProductBacklogEquals(expectedProductTimestamp.getProductBacklog(), actualProductTimestamp.getProductBacklog());
        VelocityForecastMapperTest.assertVelocityForecastEquals(expectedProductTimestamp.getVelocityForecast(), actualProductTimestamp.getVelocityForecast());
        assertReleasesAreEquals(expectedProductTimestamp.getReleases(), actualProductTimestamp.getReleases());
    }

    private static void assertReleasesAreEquals(Releases expectedReleases, Releases actualReleases) {
        final List<Release> expected = expectedReleases.getReleases();
        final List<Release> actual = actualReleases.getReleases();
        assertEquals(expected.size(), actual.size());
        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index).getName(), actual.get(index).getName());
            assertEquals(expected.get(index).getCriteria().toString(), actual.get(index).getCriteria().toString());
        }
    }
}
