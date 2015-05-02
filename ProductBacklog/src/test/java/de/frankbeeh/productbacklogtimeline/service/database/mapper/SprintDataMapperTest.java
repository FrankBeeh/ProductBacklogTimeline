package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.SprintData;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;
import de.frankbeeh.productbacklogtimeline.service.database.DataBaseServiceTest;

public class SprintDataMapperTest extends DataBaseServiceTest {
    private static final LocalDate END_DATE_1 = FormatUtility.parseLocalDate("01.01.2001");
    private static final SprintData FIRST_ITEM = new SprintData("Name 1", LocalDate.now(), END_DATE_1, 1d, 2d, 3d, 4d);
    private static final SprintData FIRST_ITEM_NAME_CHANGED = new SprintData("Name 2", LocalDate.now(), END_DATE_1, 1d, 2d, 3d, 4d);
    private SprintDataMapper mapper;

    @Test
    public void storeAndRetrieveTwoDifferentVersionsOfOneItem() throws Exception {
        mapper.insert(FIRST_ITEM);
        mapper.insert(FIRST_ITEM_NAME_CHANGED);
        assertSprintDataEquals(FIRST_ITEM, mapper.get(END_DATE_1, FIRST_ITEM.getHash()));
        assertSprintDataEquals(FIRST_ITEM_NAME_CHANGED, mapper.get(END_DATE_1, FIRST_ITEM_NAME_CHANGED.getHash()));
    }
    
    @Test
    public void storeAndRetrieveTwoTimes() throws Exception {
        mapper.insert(FIRST_ITEM);
        mapper.insert(FIRST_ITEM);
        assertSprintDataEquals(FIRST_ITEM, mapper.get(END_DATE_1, FIRST_ITEM.getHash()));
    }

    @Before
    public void setUp() {
        mapper = new SprintDataMapper(getConnection());
    }

    static void assertSprintDataEquals(SprintData expectedItem, SprintData actualItem) {
        assertEquals(expectedItem.getName(), actualItem.getName());
        assertEquals(expectedItem.getStartDate(), actualItem.getStartDate());
        assertEquals(expectedItem.getEndDate(), actualItem.getEndDate());
        assertEquals(expectedItem.getCapacityForecast(), actualItem.getCapacityForecast());
        assertEquals(expectedItem.getEffortForecast(), actualItem.getEffortForecast());
        assertEquals(expectedItem.getCapacityDone(), actualItem.getCapacityDone());
        assertEquals(expectedItem.getEffortDone(), actualItem.getEffortDone());
    }
}
