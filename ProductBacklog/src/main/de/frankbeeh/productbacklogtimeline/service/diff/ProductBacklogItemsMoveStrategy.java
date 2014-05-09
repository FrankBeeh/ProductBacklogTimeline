package de.frankbeeh.productbacklogtimeline.service.diff;

import java.util.Iterator;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogItemsMoveStrategy {

    public boolean hasSameSequence(List<ProductBacklogItem> fromProductBacklogItems, List<ProductBacklogItem> toProductBacklogItems) {
        int index = 0;
        if (fromProductBacklogItems.size() != toProductBacklogItems.size()) {
            return false;
        }
        for (final ProductBacklogItem item : fromProductBacklogItems) {
            if (!item.getId().equals(toProductBacklogItems.get(index++).getId())) {
                return false;
            }
        }
        return true;
    }

    public String findIdOfNextItemToMove(List<ProductBacklogItem> fromProductBacklogItems, List<ProductBacklogItem> toProductBacklogItems) {
        final Iterator<ProductBacklogItem> fromIterator = fromProductBacklogItems.iterator();
        final Iterator<ProductBacklogItem> toIterator = toProductBacklogItems.iterator();
        while (fromIterator.hasNext()) {
            final String fromId = fromIterator.next().getId();
            if (!fromId.equals(toIterator.next().getId())) {
                return fromId;
            }
        }
        return null;
    }

    public String findPredecessorId(String id, List<ProductBacklogItem> toProductBacklogItems) {
        String predecessorId = null;
        for (final ProductBacklogItem item : toProductBacklogItems) {
            if (item.getId().equals(id)) {
                return predecessorId;
            }
            predecessorId = item.getId();
        }
        throw new IllegalArgumentException("ID '" + id + "' not found!");
    }
}
