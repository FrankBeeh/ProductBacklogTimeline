package de.frankbeeh.productbacklogtimeline.service.diff;

import java.util.Comparator;

final class BlockComparator implements Comparator<DoubleLinkedBlocks> {
    @Override
    public int compare(DoubleLinkedBlocks o1, DoubleLinkedBlocks o2) {
        final Block block1 = o1.getBlock();
        final Block block2 = o2.getBlock();
        final int lengthDifference = block1.getLenght() - block2.getLenght();
        if (lengthDifference == 0) {
            return block1.getStartId().compareTo(block2.getStartId());
        }
        return lengthDifference;
    }
}