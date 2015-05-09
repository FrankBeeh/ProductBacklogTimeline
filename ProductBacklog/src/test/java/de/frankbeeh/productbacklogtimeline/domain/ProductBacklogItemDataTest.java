package de.frankbeeh.productbacklogtimeline.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductBacklogItemDataTest {

    @Test
    public void hashId() {
        assertEquals(newProductBacklogItemDateWithId("ID 1").getHash(), newProductBacklogItemDateWithId("ID 1").getHash());
        assertNotEquals(newProductBacklogItemDateWithId("ID 1").getHash(), newProductBacklogItemDateWithId("ID 2").getHash());
    }

    @Test
    public void hashTitle() {
        assertEquals(newProductBacklogItemDataWithTitle("Title 1").getHash(), newProductBacklogItemDataWithTitle("Title 1").getHash());
        assertNotEquals(newProductBacklogItemDataWithTitle("Title 1").getHash(), newProductBacklogItemDataWithTitle("Title 2").getHash());
    }

    @Test
    public void hashDescription() {
        assertEquals(newProductBacklogItemDataWithDescription("Description 1").getHash(), newProductBacklogItemDataWithDescription("Description 1").getHash());
        assertNotEquals(newProductBacklogItemDataWithDescription("Description 1").getHash(), newProductBacklogItemDataWithDescription("Description 2").getHash());
    }

    @Test
    public void hashEstimate() {
        assertEquals(newProductBacklogItemDataWithEstimate(1d).getHash(), newProductBacklogItemDataWithEstimate(1d).getHash());
        assertNotEquals(newProductBacklogItemDataWithEstimate(1d).getHash(), newProductBacklogItemDataWithEstimate(2d).getHash());
    }

    @Test
    public void hashState() {
        assertEquals(newProductBacklogItemDataWithState(State.Done).getHash(), newProductBacklogItemDataWithState(State.Done).getHash());
        assertNotEquals(newProductBacklogItemDataWithState(State.Done).getHash(), newProductBacklogItemDataWithState(State.Canceled).getHash());
    }

    @Test
    public void hashSprint() {
        assertEquals(newProductBacklogItemDataWithSprint("Sprint 1").getHash(), newProductBacklogItemDataWithSprint("Sprint 1").getHash());
        assertNotEquals(newProductBacklogItemDataWithSprint("Sprint 1").getHash(), newProductBacklogItemDataWithSprint("Sprint 2").getHash());
    }

    @Test
    public void hashRank() {
        assertEquals(newProductBacklogItemDataWithRank("Rank 1").getHash(), newProductBacklogItemDataWithRank("Rank 1").getHash());
        assertNotEquals(newProductBacklogItemDataWithRank("Rank 1").getHash(), newProductBacklogItemDataWithRank("Rank 2").getHash());
    }

    @Test
    public void hashPlannedRelease() {
        assertEquals(newProductBacklogItemDataWithPlannedRelease("Release 1").getHash(), newProductBacklogItemDataWithPlannedRelease("Release 1").getHash());
        assertNotEquals(newProductBacklogItemDataWithPlannedRelease("Release 1").getHash(), newProductBacklogItemDataWithPlannedRelease("Release 2").getHash());
    }
    
    @Test
    public void hashSameValueOfDifferentProperties() {
        assertNotEquals(newProductBacklogItemDataWithTitle("Text").getHash(), newProductBacklogItemDataWithDescription("Text").getHash());
        assertNotEquals(newProductBacklogItemDataWithSprint("Text").getHash(), newProductBacklogItemDataWithRank("Text").getHash());
        assertNotEquals(newProductBacklogItemDataWithRank("Text").getHash(), newProductBacklogItemDataWithPlannedRelease("Text").getHash());
    }

    private ProductBacklogItemData newProductBacklogItemDateWithId(String id) {
        return new ProductBacklogItemData(id, null, null, null, (State) null, null, null, null);
    }

    private ProductBacklogItemData newProductBacklogItemDataWithTitle(String title) {
        return new ProductBacklogItemData(null, title, null, null, (State) null, null, null, null);
    }

    private ProductBacklogItemData newProductBacklogItemDataWithDescription(String description) {
        return new ProductBacklogItemData(null, null, description, null, (State) null, null, null, null);
    }

    private ProductBacklogItemData newProductBacklogItemDataWithEstimate(Double estimate) {
        return new ProductBacklogItemData(null, null, null, estimate, (State) null, null, null, null);
    }

    private ProductBacklogItemData newProductBacklogItemDataWithState(State state) {
        return new ProductBacklogItemData(null, null, null, null, state, null, null, null);
    }

    private ProductBacklogItemData newProductBacklogItemDataWithSprint(String sprint) {
        return new ProductBacklogItemData(null, null, null, null, (State) null, sprint, null, null);
    }

    private ProductBacklogItemData newProductBacklogItemDataWithRank(String rank) {
        return new ProductBacklogItemData(null, null, null, null, (State) null, null, rank, null);
    }

    private ProductBacklogItemData newProductBacklogItemDataWithPlannedRelease(String release) {
        return new ProductBacklogItemData(null, null, null, null, (State) null, null, null, release);
    }
}
