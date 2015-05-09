package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.database.DataBaseServiceTest;

public class VelocityForecastMapperTest extends DataBaseServiceTest {
    static final VelocityForecast VELOCITY_FORECAST_1 = new VelocityForecast(Arrays.asList(newFirstSprintData(), newSecondSprintData()), null);
    static final VelocityForecast VELOCITY_FORECAST_2 = new VelocityForecast(Arrays.asList(newFirstSprintData_nameChanged(), newSecondSprintData()), null);
    private VelocityForecastMapper mapper;

    @Test
    public void storeAndRetrieve_twoVelocityForecasts_havingDifferentVersionOfFirstItem() throws Exception {
        mapper.insert(ProductTimestampMapperTest.RELEASE_FORECAST_ID_1, VELOCITY_FORECAST_1);
        mapper.insert(ProductTimestampMapperTest.RELEASE_FORECAST_ID_2, VELOCITY_FORECAST_2);
        assertVelocityForecastEquals(VELOCITY_FORECAST_1, mapper.get(ProductTimestampMapperTest.RELEASE_FORECAST_ID_1));
        assertVelocityForecastEquals(VELOCITY_FORECAST_2, mapper.get(ProductTimestampMapperTest.RELEASE_FORECAST_ID_2));
    }

    @Before
    public void setUp() {
        mapper = new VelocityForecastMapper(getConnection());
    }

    static void assertVelocityForecastEquals(VelocityForecast expectedVelocityForecast, VelocityForecast actualVelocityForecast) {
        assertEquals(expectedVelocityForecast.getSprints().size(), actualVelocityForecast.getSprints().size());
        final List<Sprint> actualItems = actualVelocityForecast.getSprints();
        final List<Sprint> expectedItems = expectedVelocityForecast.getSprints();
        for (int index = 0; index < expectedItems.size(); index++) {
            assertSprintDataEquals(expectedItems.get(index), actualItems.get(index));
        }
    }

    private static void assertSprintDataEquals(Sprint expectedItem, Sprint actualItem) {
        assertEquals(expectedItem.getName(), actualItem.getName());
        assertEquals(expectedItem.getStartDate(), actualItem.getStartDate());
        assertEquals(expectedItem.getEndDate(), actualItem.getEndDate());
        assertEquals(expectedItem.getCapacityForecast(), actualItem.getCapacityForecast());
        assertEquals(expectedItem.getEffortForecast(), actualItem.getEffortForecast());
        assertEquals(expectedItem.getCapacityDone(), actualItem.getCapacityDone());
        assertEquals(expectedItem.getEffortDone(), actualItem.getEffortDone());
    }

    private static Sprint newFirstSprintData() {
        return new Sprint("Name 1", LocalDate.of(2001, Month.JANUARY, 1), LocalDate.of(2001, Month.FEBRUARY, 2), 1d, 2d, 3d, 4d);
    }

    private static Sprint newSecondSprintData() {
        return new Sprint("Name 2", LocalDate.of(2002, Month.FEBRUARY, 2), LocalDate.of(2002, Month.MARCH, 3), 5d, 6d, 7d, 8d);
    }

    private static Sprint newFirstSprintData_nameChanged() {
        return new Sprint("Changed Name 1", LocalDate.of(2001, Month.JANUARY, 1), LocalDate.of(2001, Month.FEBRUARY, 2), 1d, 2d, 3d, 4d);
    }
}
