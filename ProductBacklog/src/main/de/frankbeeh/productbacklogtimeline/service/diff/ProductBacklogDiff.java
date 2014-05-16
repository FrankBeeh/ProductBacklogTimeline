package de.frankbeeh.productbacklogtimeline.service.diff;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.data.ChangeEstimateOfProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.DeleteProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.InsertProductBacklogItemAfterId;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogChange;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogDiff {
    private final FindProductBacklogItemsMoveStrategy findProductBacklogItemsMoveStrategy = new FindProductBacklogItemsMoveStrategy();

    public List<ProductBacklogChange> computeChanges(ProductBacklog sourceProductBacklog, ProductBacklog targetProductBacklog) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        changes.addAll(findDeletes(sourceProductBacklog, targetProductBacklog));
        changes.addAll(findInserts(sourceProductBacklog, targetProductBacklog));
        final LinkedList<ProductBacklogItem> changedFromProductBacklogItems = applyChanges(sourceProductBacklog.getItems(), changes);
        final LinkedList<ProductBacklogItem> toProductBacklogItems = new LinkedList<ProductBacklogItem>(targetProductBacklog.getItems());
        final List<ProductBacklogChange> moves = findMoves(changedFromProductBacklogItems, toProductBacklogItems);
        changes.addAll(moves);
        changes.addAll(findChangedEstimates(applyChanges(changedFromProductBacklogItems, moves), toProductBacklogItems));
        return changes;
    }

    private List<ProductBacklogChange> findMoves(LinkedList<ProductBacklogItem> sourceProductBacklogItems, LinkedList<ProductBacklogItem> targetProductBacklogItems) {
        return findProductBacklogItemsMoveStrategy.findMoves(sourceProductBacklogItems, targetProductBacklogItems);
    }

    @VisibleForTesting
    List<ProductBacklogChange> findChangedEstimates(List<ProductBacklogItem> sourceProductBacklogItems, List<ProductBacklogItem> targetProductBacklogItems) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        for (int index = 0; index < sourceProductBacklogItems.size(); index++) {
            final ProductBacklogItem toProductBacklogItem = targetProductBacklogItems.get(index);
            final ProductBacklogItem fromProductBacklogItem = sourceProductBacklogItems.get(index);
            if (!fromProductBacklogItem.getId().equals(toProductBacklogItem.getId())) {
                throw new IllegalArgumentException("IDs are different for items at position " + (index + 1) + ": '" + fromProductBacklogItem.getId() + "' vs. '" + toProductBacklogItem.getId() + "'!");
            }
            if (notEqual(fromProductBacklogItem.getEstimate(), toProductBacklogItem.getEstimate())) {
                changes.add(new ChangeEstimateOfProductBacklogItem(toProductBacklogItem.getId(), toProductBacklogItem.getEstimate()));
            }
        }
        return changes;
    }

    private boolean notEqual(Object sourceValue, Object targetValue) {
        if (sourceValue == null) {
            return targetValue != null;
        }
        return !sourceValue.equals(targetValue);
    }

    private List<ProductBacklogChange> findInserts(ProductBacklog sourceProductBacklog, ProductBacklog targetProductBacklog) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        String previousProductBacklogItemId = null;
        for (final ProductBacklogItem productBacklogItem : targetProductBacklog.getItems()) {
            if (!sourceProductBacklog.containsId(productBacklogItem.getId())) {
                changes.add(new InsertProductBacklogItemAfterId(previousProductBacklogItemId, productBacklogItem));
            }
            previousProductBacklogItemId = productBacklogItem.getId();
        }
        return changes;
    }

    private List<ProductBacklogChange> findDeletes(ProductBacklog sourceProductBacklog, ProductBacklog targetProductBacklog) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        for (final ProductBacklogItem productBacklogItem : sourceProductBacklog.getItems()) {
            final String id = productBacklogItem.getId();
            if (!targetProductBacklog.containsId(id)) {
                changes.add(new DeleteProductBacklogItem(id));
            }
        }
        return changes;
    }

    private LinkedList<ProductBacklogItem> applyChanges(List<ProductBacklogItem> items, List<ProductBacklogChange> changes) {
        final LinkedList<ProductBacklogItem> linkedList = new LinkedList<ProductBacklogItem>(items);
        for (final ProductBacklogChange change : changes) {
            change.applyTo(linkedList);
        }
        return linkedList;
    }
}
