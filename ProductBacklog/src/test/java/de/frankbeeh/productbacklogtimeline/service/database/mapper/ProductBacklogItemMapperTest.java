package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemData;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.service.database.DatabaseServiceTest;

public class ProductBacklogItemMapperTest extends DatabaseServiceTest {
	private static final ProductBacklogItemData FIRST_ITEM = new ProductBacklogItemData(
			"ID 1", "Title 1", "Description 1", 1d, State.Done, "Sprint 1",
			"Rank 1", "Release 1");
	private ProductBacklogItemMapper mapper;

	@Test
	public void getShortJottings() throws Exception {
		mapper.insert(FIRST_ITEM);
		final ProductBacklogItemData item = mapper.get(FIRST_ITEM.getId());
		assertItemEquals(FIRST_ITEM, item);
	}

	@Before
	public void setUp() {
		mapper = new ProductBacklogItemMapper(getConnection());
	}

	private void assertItemEquals(ProductBacklogItemData expectedItem,
			ProductBacklogItemData actualItem) {
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
