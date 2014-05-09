package de.frankbeeh.productbacklogtimeline.data;

import java.util.List;
import java.util.ListIterator;

public class MoveProductBacklogItemAfterId extends ProductBacklogChange {
    private final String idOfSourceItem;

    public MoveProductBacklogItemAfterId(String idOfSourceItem, String idOfIDestinationItem) {
        super(idOfIDestinationItem);
        this.idOfSourceItem = idOfSourceItem;
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        final ListIterator<ProductBacklogItem> iteratorToSourceItem = iterateToId(idOfSourceItem, productBacklogItems);
        final ProductBacklogItem sourceItem = iteratorToSourceItem.previous();
        iteratorToSourceItem.remove();
        final ListIterator<ProductBacklogItem> iterator = iterateToReferenceId(productBacklogItems);
        iterator.add(sourceItem);
    }
}
