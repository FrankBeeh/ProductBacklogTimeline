package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.service.database.DataBaseServiceTest;

public class ReleaseForecastMapperTest extends DataBaseServiceTest {
    private static final LocalDateTime LOCAL_DATE_TIME_1 = LocalDateTime.now();
    private static final LocalDateTime LOCAL_DATE_TIME_2 = LocalDateTime.now().plusYears(1);
    private static final ReleaseForecast RELEASE_FORECAST_1 = new ReleaseForecast(LOCAL_DATE_TIME_1, "Name 1", ProductBacklogMapperTest.PRODUCT_BACKLOG_1, null, null);
    private static final ReleaseForecast RELEASE_FORECAST_2 = new ReleaseForecast(LOCAL_DATE_TIME_2, "Name 2", ProductBacklogMapperTest.PRODUCT_BACKLOG_2, null, null);
    private ReleaseForecastMapper mapper;

    @Test
    public void insertAndGetTwoReleaseForecasts() throws Exception {
        mapper.insert(RELEASE_FORECAST_2);
        mapper.insert(RELEASE_FORECAST_1);
        assertReleaseForecastEquals(RELEASE_FORECAST_1, mapper.get(RELEASE_FORECAST_1.getDateTime()));
        assertReleaseForecastEquals(RELEASE_FORECAST_2, mapper.get(RELEASE_FORECAST_2.getDateTime()));
    }

    @Test
    public void getAllIds() throws Exception {
        insertAndGetTwoReleaseForecasts();
        assertEquals(Arrays.asList(LOCAL_DATE_TIME_1, LOCAL_DATE_TIME_2), mapper.getAllIds());
    }

    @Before
    public void setUp() {
        mapper = new ReleaseForecastMapper(getConnection());
    }

    private static void assertReleaseForecastEquals(ReleaseForecast expectedReleaseForecast, ReleaseForecast actualReleaseForecast) {
        assertEquals(expectedReleaseForecast.getDateTime(), actualReleaseForecast.getDateTime());
        assertEquals(expectedReleaseForecast.getName(), actualReleaseForecast.getName());
        ProductBacklogMapperTest.assertProductBacklogEquals(expectedReleaseForecast.getProductBacklog(), actualReleaseForecast.getProductBacklog());
    }
}
