package de.frankbeeh.productbacklogtimeline.service.diff;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.data.ChangeEstimateOfProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.DeleteProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.InsertProductBacklogItemAfterId;
import de.frankbeeh.productbacklogtimeline.data.MoveProductBacklogItemAfterId;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogChange;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogDiff {
    private final ProductBacklogItemsMoveStrategy productBacklogItemsMoveStrategy = new ProductBacklogItemsMoveStrategy();

    public List<ProductBacklogChange> computeChanges(ProductBacklog fromProductBacklog, ProductBacklog toProductBacklog) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        changes.addAll(findDeletes(fromProductBacklog, toProductBacklog));
        changes.addAll(findInserts(fromProductBacklog, toProductBacklog));
        final List<ProductBacklogItem> changedFromProductBacklogItems = applyChanges(fromProductBacklog.getItems(), changes);
        final List<ProductBacklogItem> toProductBacklogItems = toProductBacklog.getItems();
        changes.addAll(findMoves(changedFromProductBacklogItems, toProductBacklogItems));
        changes.addAll(findChangedEstimates(changedFromProductBacklogItems, toProductBacklogItems));
        return changes;
    }

    private List<ProductBacklogChange> findMoves(List<ProductBacklogItem> fromProductBacklogItems, List<ProductBacklogItem> toProductBacklogItems) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        String idOfNextItemToMove;
        while ((idOfNextItemToMove = productBacklogItemsMoveStrategy.findIdOfNextItemToMove(fromProductBacklogItems, toProductBacklogItems)) != null) {
            final String toPredecessorId = productBacklogItemsMoveStrategy.findPredecessorId(idOfNextItemToMove, toProductBacklogItems);
            final MoveProductBacklogItemAfterId move = new MoveProductBacklogItemAfterId(idOfNextItemToMove, toPredecessorId);
            changes.add(move);
            move.applyTo(fromProductBacklogItems);
        }
        return changes;
    }

    @VisibleForTesting
    List<ProductBacklogChange> findChangedEstimates(List<ProductBacklogItem> fromProductBacklogItems, List<ProductBacklogItem> toProductBacklogItems) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        for (int index = 0; index < fromProductBacklogItems.size(); index++) {
            final ProductBacklogItem toProductBacklogItem = toProductBacklogItems.get(index);
            final ProductBacklogItem fromProductBacklogItem = fromProductBacklogItems.get(index);
            if (!fromProductBacklogItem.getId().equals(toProductBacklogItem.getId())) {
                throw new IllegalArgumentException("IDs are different for items at position " + (index + 1) + ": '" + fromProductBacklogItem.getId() + "' vs. '" + toProductBacklogItem.getId() + "'!");
            }
            if (notEqual(fromProductBacklogItem.getEstimate(), toProductBacklogItem.getEstimate())) {
                changes.add(new ChangeEstimateOfProductBacklogItem(toProductBacklogItem.getId(), toProductBacklogItem.getEstimate()));
            }
        }
        return changes;
    }

    private boolean notEqual(Object fromValue, Object toValue) {
        if (fromValue == null) {
            return toValue != null;
        }
        return !fromValue.equals(toValue);
    }

    private List<ProductBacklogChange> findInserts(ProductBacklog fromProductBacklog, ProductBacklog toProductBacklog) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        String previousProductBacklogItemId = null;
        for (final ProductBacklogItem productBacklogItem : toProductBacklog.getItems()) {
            if (!fromProductBacklog.containsId(productBacklogItem.getId())) {
                changes.add(new InsertProductBacklogItemAfterId(previousProductBacklogItemId, productBacklogItem));
            }
            previousProductBacklogItemId = productBacklogItem.getId();
        }
        return changes;
    }

    private List<ProductBacklogChange> findDeletes(ProductBacklog fromProductBacklog, ProductBacklog toProductBacklog) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        for (final ProductBacklogItem productBacklogItem : fromProductBacklog.getItems()) {
            final String id = productBacklogItem.getId();
            if (!toProductBacklog.containsId(id)) {
                changes.add(new DeleteProductBacklogItem(id));
            }
        }
        return changes;
    }

    private List<ProductBacklogItem> applyChanges(List<ProductBacklogItem> items, List<ProductBacklogChange> changes) {
        final List<ProductBacklogItem> linkedList = new LinkedList<ProductBacklogItem>(items);
        for (final ProductBacklogChange change : changes) {
            change.applyTo(linkedList);
        }
        return linkedList;
    }
}
