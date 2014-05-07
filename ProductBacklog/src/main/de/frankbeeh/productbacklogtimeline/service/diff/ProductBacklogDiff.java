package de.frankbeeh.productbacklogtimeline.service.diff;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.data.ChangeEstimate;
import de.frankbeeh.productbacklogtimeline.data.DeleteProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.InsertProductBacklogItemAfterId;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogChange;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;

public class ProductBacklogDiff {
    public List<ProductBacklogChange> computeChanges(ProductBacklog fromProductBacklog, ProductBacklog toProductBacklog) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        changes.addAll(findInserts(fromProductBacklog, toProductBacklog));
        changes.addAll(findDeletes(fromProductBacklog, toProductBacklog));
        final List<ProductBacklogItem> applyChanges = applyChanges(fromProductBacklog.getItems(), changes);
        changes.addAll(findChangedEstimates(applyChanges, toProductBacklog.getItems()));
        return changes;
    }

    private List<ProductBacklogItem> applyChanges(List<ProductBacklogItem> items, List<ProductBacklogChange> changes) {
        final List<ProductBacklogItem> linkedList = new LinkedList<ProductBacklogItem>(items);
        for (final ProductBacklogChange change : changes) {
            change.applyTo(linkedList);
        }
        return linkedList;
    }

    private List<ProductBacklogChange> findChangedEstimates(List<ProductBacklogItem> fromProductBacklogItems, List<ProductBacklogItem> toProductBacklogItems) {
        final List<ProductBacklogChange> changes = new ArrayList<ProductBacklogChange>();
        for (int index = 0; index < fromProductBacklogItems.size(); index++) {
            final ProductBacklogItem toBacklogItem = toProductBacklogItems.get(index);
            if (!fromProductBacklogItems.get(index).getEstimate().equals(toBacklogItem.getEstimate())) {
                changes.add(new ChangeEstimate(toBacklogItem.getId(), toBacklogItem.getEstimate()));
            }
        }
        return changes;
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
}
