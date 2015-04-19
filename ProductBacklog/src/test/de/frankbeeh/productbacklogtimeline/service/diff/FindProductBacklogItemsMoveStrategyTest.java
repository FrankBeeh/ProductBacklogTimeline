package de.frankbeeh.productbacklogtimeline.service.diff;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.MoveProductBacklogItemsAfterId;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogChange;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class FindProductBacklogItemsMoveStrategyTest {

    private static final Block ROOT_BLOCK = new Block(null, 0, null, null);
    private static final String A1 = "A1";
    private static final String A2 = "A2";
    private static final String A3 = "A3";
    private static final String B4 = "B4";
    private static final String C5 = "C5";
    private static final String C6 = "C6";
    private static final String D7 = "D7";
    private static final String E8 = "E8";
    private static final String E9 = "E9";
    private static final String F10 = "F10";
    private static final String F11 = "F11";
    private static final ProductBacklogItem PBI_A1 = createProductBacklogItem(A1);
    private static final ProductBacklogItem PBI_A2 = createProductBacklogItem(A2);
    private static final ProductBacklogItem PBI_A3 = createProductBacklogItem(A3);
    private static final ProductBacklogItem PBI_B4 = createProductBacklogItem(B4);
    private static final ProductBacklogItem PBI_C5 = createProductBacklogItem(C5);
    private static final ProductBacklogItem PBI_C6 = createProductBacklogItem(C6);
    private static final ProductBacklogItem PBI_D7 = createProductBacklogItem(D7);
    private static final ProductBacklogItem PBI_E8 = createProductBacklogItem(E8);
    private static final ProductBacklogItem PBI_E9 = createProductBacklogItem(E9);
    private static final ProductBacklogItem PBI_F10 = createProductBacklogItem(F10);
    private static final ProductBacklogItem PBI_F11 = createProductBacklogItem(F11);
    private static final ProductBacklogItem TO_PBI_A1 = createProductBacklogItem(A1);
    private static final ProductBacklogItem TO_PBI_A2 = createProductBacklogItem(A2);
    private static final ProductBacklogItem TO_PBI_A3 = createProductBacklogItem(A3);
    private static final ProductBacklogItem TO_PBI_B4 = createProductBacklogItem(B4);
    private static final ProductBacklogItem TO_PBI_C5 = createProductBacklogItem(C5);
    private static final ProductBacklogItem TO_PBI_C6 = createProductBacklogItem(C6);
    private static final ProductBacklogItem TO_PBI_D7 = createProductBacklogItem(D7);
    private static final ProductBacklogItem TO_PBI_E8 = createProductBacklogItem(E8);
    private static final ProductBacklogItem TO_PBI_E9 = createProductBacklogItem(E9);
    private static final ProductBacklogItem TO_PBI_F10 = createProductBacklogItem(F10);
    private static final ProductBacklogItem TO_PBI_F11 = createProductBacklogItem(F11);

    private FindProductBacklogItemsMoveStrategy strategy;
    private LinkedList<ProductBacklogItem> exampleSourceProductBacklog;
    private LinkedList<ProductBacklogItem> exampleTargetProductBacklog;
    private List<Block> exampleBlocks;
    private DoubleLinkedBlocks exampleDoubleLinkedBlocks;

    @Test
    public void findMoves_empty() throws Exception {
        assertTrue(strategy.findMoves(createList(), createList()).isEmpty());
    }

    @Test
    public void findMoves_example() throws Exception {
        final List<ProductBacklogChange> moves = strategy.findMoves(exampleSourceProductBacklog, exampleTargetProductBacklog);
        assertEquals(
                Arrays.asList(new MoveProductBacklogItemsAfterId(B4, null, 1), new MoveProductBacklogItemsAfterId(D7, B4, 1).toString(), new MoveProductBacklogItemsAfterId(E8, F11, 2)).toString(),
                moves.toString());
    }

    @Test
    public void splitIntoBlocks_empty() throws Exception {
        assertTrue(strategy.splitIntoBlocks(createList(), createList()).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitIntoBlocks_idNotFound() throws Exception {
        strategy.splitIntoBlocks(createList(PBI_A1), createList());
    }

    @Test
    public void splitIntoBlocks_oneItem() throws Exception {
        final List<Block> blocks = strategy.splitIntoBlocks(createList(PBI_A1), createList(TO_PBI_A1));
        assertEquals(Arrays.asList(new Block(A1, 1, A1, null)).toString(), blocks.toString());
    }

    @Test
    public void splitIntoBlocks_twoItems() throws Exception {
        final List<Block> blocks = strategy.splitIntoBlocks(createList(PBI_A1, PBI_A2), createList(TO_PBI_A1, TO_PBI_A2));
        assertEquals(Arrays.asList(new Block(A1, 2, A2, null)).toString(), blocks.toString());
    }

    @Test
    public void splitIntoBlocks_threeBlocks() throws Exception {
        final List<Block> blocks = strategy.splitIntoBlocks(createList(PBI_A1, PBI_A2, PBI_A3, PBI_B4, PBI_C5, PBI_C6), createList(TO_PBI_C5, TO_PBI_C6, TO_PBI_B4, TO_PBI_A1, TO_PBI_A2, TO_PBI_A3));
        assertEquals(Arrays.asList(new Block(A1, 3, A3, B4), new Block(B4, 1, B4, C6), new Block(C5, 2, C6, null)).toString(), blocks.toString());
    }

    @Test
    public void splitIntoBlocks_example() throws Exception {
        final List<Block> blocks = strategy.splitIntoBlocks(exampleSourceProductBacklog, exampleTargetProductBacklog);
        assertEquals(exampleBlocks.toString(), blocks.toString());
    }

    @Test
    public void createDoubleLinkedBlocks_empty() throws Exception {
        assertDoubleLinkedBlockEquals(Arrays.asList(ROOT_BLOCK), strategy.createDoubleLinkedBlocks(new ArrayList<Block>()));
    }

    @Test
    public void createDoubleLinkedBlocks_oneBlock() throws Exception {
        final Block block1 = new Block(A1, 1, A3, B4);
        assertDoubleLinkedBlockEquals(Arrays.asList(ROOT_BLOCK, block1), strategy.createDoubleLinkedBlocks(Arrays.asList(block1)));
    }

    @Test
    public void createDoubleLinkedBlocks_example() throws Exception {
        assertDoubleLinkedBlockEquals(Arrays.asList(ROOT_BLOCK, new Block(A1, 3, A3, D7), new Block(B4, 1, B4, null), new Block(C5, 2, C6, A3), new Block(D7, 1, D7, B4), new Block(E8, 2, E9, F11),
                new Block(F10, 2, F11, C6)), strategy.createDoubleLinkedBlocks(exampleBlocks));
    }

    @Test
    public void createMapFromLastIdToBlock_rootOnly() throws Exception {
        final DoubleLinkedBlocks rootBlock = new DoubleLinkedBlocks(ROOT_BLOCK);
        final Map<String, DoubleLinkedBlocks> map = strategy.createMapFromLastIdToBlock(rootBlock);
        assertEquals(1, map.size());
        assertEquals(rootBlock, map.get(null));
    }

    @Test
    public void createMapFromLastIdToBlock_example() throws Exception {
        final Map<String, DoubleLinkedBlocks> map = strategy.createMapFromLastIdToBlock(exampleDoubleLinkedBlocks);
        assertEquals(exampleBlocks.size() + 1, map.size());
        for (final Entry<String, DoubleLinkedBlocks> entry : map.entrySet()) {
            final DoubleLinkedBlocks value = entry.getValue();
            final Block block = value.getBlock();
            assertEquals(block.getLastId(), entry.getKey());
        }
    }

    @Test
    public void sortBlocksBySize_empty() throws Exception {
        assertTrue(strategy.sortBlocksBySize(new DoubleLinkedBlocks(ROOT_BLOCK)).isEmpty());
    }

    @Test
    public void sortBlocksBySize_example() throws Exception {
        final SortedSet<DoubleLinkedBlocks> sortedBlocks = strategy.sortBlocksBySize(exampleDoubleLinkedBlocks);
        assertEquals(exampleBlocks.size(), sortedBlocks.size());
        final List<String> expectedStartIds = Arrays.asList(B4, D7, C5, E8, F10, A1);
        final Iterator<DoubleLinkedBlocks> sortedBlocksIterator = sortedBlocks.iterator();
        for (final String expectedStartId : expectedStartIds) {
            assertEquals(expectedStartId, sortedBlocksIterator.next().getBlock().getStartId());
        }
    }

    @Before
    public void setUp() {
        strategy = new FindProductBacklogItemsMoveStrategy();
        exampleSourceProductBacklog = createList(PBI_A1, PBI_A2, PBI_A3, PBI_B4, PBI_C5, PBI_C6, PBI_D7, PBI_E8, PBI_E9, PBI_F10, PBI_F11);
        exampleTargetProductBacklog = createList(TO_PBI_B4, TO_PBI_D7, TO_PBI_A1, TO_PBI_A2, TO_PBI_A3, TO_PBI_C5, TO_PBI_C6, TO_PBI_F10, TO_PBI_F11, TO_PBI_E8, TO_PBI_E9);
        exampleBlocks = Arrays.asList(new Block(A1, 3, A3, D7), new Block(B4, 1, B4, null), new Block(C5, 2, C6, A3), new Block(D7, 1, D7, B4), new Block(E8, 2, E9, F11), new Block(F10, 2, F11, C6));
        exampleDoubleLinkedBlocks = createDoubleLinkedBlocks(exampleBlocks);
    }

    private static DoubleLinkedBlocks createDoubleLinkedBlocks(List<Block> exampleBlocks) {
        final DoubleLinkedBlocks rootBlock = new DoubleLinkedBlocks(ROOT_BLOCK);
        DoubleLinkedBlocks previousBlock = rootBlock;
        for (final Block block : exampleBlocks) {
            final DoubleLinkedBlocks doubleLinkedBlock = new DoubleLinkedBlocks(block);
            previousBlock.setNext(doubleLinkedBlock);
            previousBlock = doubleLinkedBlock;
        }
        return rootBlock;
    }

    private static ProductBacklogItem createProductBacklogItem(String id) {
        return new ProductBacklogItem(id, null, null, null, null, null, null, null);
    }

    private static LinkedList<ProductBacklogItem> createList(ProductBacklogItem... productBacklogItems) {
        return new LinkedList<ProductBacklogItem>(Arrays.asList(productBacklogItems));
    }

    private static void assertDoubleLinkedBlockEquals(List<Block> expectedBlocks, DoubleLinkedBlocks actualDoubleLinkedBlocks) {
        DoubleLinkedBlocks previousExpected = null;
        DoubleLinkedBlocks actual = actualDoubleLinkedBlocks;
        for (final Block expected : expectedBlocks) {
            assertNotNull(actual);
            assertEquals(expected.toString(), actual.getBlock().toString());
            assertEquals(previousExpected, actual.getPrevious());
            previousExpected = actual;
            actual = actual.getNext();
        }
    }
}
