package de.frankbeeh.productbacklogtimeline.service.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.change.MoveProductBacklogItemsAfterId;
import de.frankbeeh.productbacklogtimeline.domain.change.ProductBacklogChange;

public class FindProductBacklogItemsMoveStrategy {

    public List<ProductBacklogChange> findMoves(LinkedList<ProductBacklogItem> sourceProductBacklogItems, LinkedList<ProductBacklogItem> targetProductBacklogItems) {
        final List<Block> blocks = splitIntoBlocks(sourceProductBacklogItems, targetProductBacklogItems);
        final DoubleLinkedBlocks doubleLinkedBlocks = createDoubleLinkedBlocks(blocks);
        final Map<String, DoubleLinkedBlocks> mapFromLastIdToBlock = createMapFromLastIdToBlock(doubleLinkedBlocks);
        final SortedSet<DoubleLinkedBlocks> sortedBlocks = sortBlocksBySize(doubleLinkedBlocks);
        return findMovesInternally(sortedBlocks, mapFromLastIdToBlock);
    }

    @VisibleForTesting
    List<Block> splitIntoBlocks(LinkedList<ProductBacklogItem> sourceProductBacklogItems, LinkedList<ProductBacklogItem> targetProductBacklogItems) {
        final List<Block> blocks = new ArrayList<Block>();
        final ListIterator<ProductBacklogItem> sourceIterator = sourceProductBacklogItems.listIterator();
        while (sourceIterator.hasNext()) {
            final ProductBacklogItem fromProductBacklogItem = sourceIterator.next();
            final String firstId = fromProductBacklogItem.getId();
            final ListIterator<ProductBacklogItem> targetIterator = ProductBacklogChange.iterateToId(firstId, targetProductBacklogItems);
            final String precedingIdInTarget = getPrecedingIdInTarget(targetIterator);
            final int length = determineLengthOfBlock(sourceIterator, targetIterator);
            final String lastId = getLastIdInSource(sourceIterator);
            final Block block = new Block(firstId, length, lastId, precedingIdInTarget);
            blocks.add(block);
        }
        return blocks;
    }

    private List<ProductBacklogChange> findMovesInternally(final SortedSet<DoubleLinkedBlocks> sortedBlocks, final Map<String, DoubleLinkedBlocks> mapFromLastIdToBlock) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        for (final DoubleLinkedBlocks sortedBlock : sortedBlocks) {
            final Block block = sortedBlock.getBlock();
            final DoubleLinkedBlocks precedingBlock = mapFromLastIdToBlock.get(block.getPrecedingIdInTarget());
            if (sortedBlock != precedingBlock.getNext()) {
                changes.add(new MoveProductBacklogItemsAfterId(block.getStartId(), precedingBlock.getBlock().getLastId(), block.getLenght()));
                relink(sortedBlock, precedingBlock);
            }
        }
        return changes;
    }

    @VisibleForTesting
    DoubleLinkedBlocks createDoubleLinkedBlocks(List<Block> blocks) {
        final DoubleLinkedBlocks firstBlock = new DoubleLinkedBlocks(new Block(null, 0, null, null));
        DoubleLinkedBlocks previousBlock = firstBlock;
        for (final Block block : blocks) {
            final DoubleLinkedBlocks doubleLinkedBlocks = new DoubleLinkedBlocks(block);
            previousBlock.setNext(doubleLinkedBlocks);
            doubleLinkedBlocks.setPrevious(previousBlock);
            previousBlock = doubleLinkedBlocks;
        }
        return firstBlock;
    }

    @VisibleForTesting
    Map<String, DoubleLinkedBlocks> createMapFromLastIdToBlock(DoubleLinkedBlocks doubleLinkedBlocks) {
        final Map<String, DoubleLinkedBlocks> map = new HashMap<String, DoubleLinkedBlocks>();
        DoubleLinkedBlocks doubleLinkedBlock = doubleLinkedBlocks;
        while (doubleLinkedBlock != null) {
            final Block block = doubleLinkedBlock.getBlock();
            map.put(block.getLastId(), doubleLinkedBlock);
            doubleLinkedBlock = doubleLinkedBlock.getNext();
        }
        return map;
    }

    @VisibleForTesting
    SortedSet<DoubleLinkedBlocks> sortBlocksBySize(DoubleLinkedBlocks doubleLinkedBlocks) {
        final SortedSet<DoubleLinkedBlocks> sortedSet = new TreeSet<DoubleLinkedBlocks>(new BlockComparator());
        DoubleLinkedBlocks doubleLinkedBlock = doubleLinkedBlocks.getNext();
        while (doubleLinkedBlock != null) {
            sortedSet.add(doubleLinkedBlock);
            doubleLinkedBlock = doubleLinkedBlock.getNext();
        }
        return sortedSet;
    }

    private void relink(DoubleLinkedBlocks block, DoubleLinkedBlocks precedingBlock) {
        final DoubleLinkedBlocks nextBlock = block.getNext();
        final DoubleLinkedBlocks previousBlock = block.getPrevious();
        final DoubleLinkedBlocks nextPrecedingBlock = precedingBlock.getNext();
        setNext(block, nextPrecedingBlock);
        setNext(precedingBlock, block);
        setNext(previousBlock, nextBlock);
        setPrevious(block, precedingBlock);
        setPrevious(nextBlock, previousBlock);
        setPrevious(nextPrecedingBlock, block);
    }

    private void setPrevious(final DoubleLinkedBlocks block, final DoubleLinkedBlocks previousBlock) {
        if (block != null) {
            block.setPrevious(previousBlock);
        }
    }

    private void setNext(DoubleLinkedBlocks block, DoubleLinkedBlocks nextBlock) {
        if (block != null) {
            block.setNext(nextBlock);
        }
    }

    private String getLastIdInSource(final ListIterator<ProductBacklogItem> sourceIterator) {
        final String lastId = sourceIterator.previous().getId();
        sourceIterator.next();
        return lastId;
    }

    private String getPrecedingIdInTarget(ListIterator<ProductBacklogItem> targetIterator) {
        String precedingId = null;
        targetIterator.previous();
        if (targetIterator.hasPrevious()) {
            precedingId = targetIterator.previous().getId();
            targetIterator.next();
        }
        targetIterator.next();
        return precedingId;
    }

    private int determineLengthOfBlock(final ListIterator<ProductBacklogItem> sourceIterator, final ListIterator<ProductBacklogItem> targetIterator) {
        int length = 1;
        while (sourceIterator.hasNext() && targetIterator.hasNext()) {
            if (!sourceIterator.next().getId().equals(targetIterator.next().getId())) {
                sourceIterator.previous();
                break;
            }
            length++;
        }
        return length;
    }
}
