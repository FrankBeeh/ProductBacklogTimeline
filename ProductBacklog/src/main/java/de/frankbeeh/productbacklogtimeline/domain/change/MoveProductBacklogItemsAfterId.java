package de.frankbeeh.productbacklogtimeline.domain.change;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public class MoveProductBacklogItemsAfterId extends ProductBacklogChange {
    private final String idOfSourceItem;
    private final int numberOfItemsToMove;

    public MoveProductBacklogItemsAfterId(String idOfSourceItem, String idOfIDestinationItem, int numberOfItemsToMove) {
        super(idOfIDestinationItem);
        this.idOfSourceItem = idOfSourceItem;
        this.numberOfItemsToMove = numberOfItemsToMove;
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        final ListIterator<ProductBacklogItem> iteratorToSourceItem = iterateToId(idOfSourceItem, productBacklogItems);
        final List<ProductBacklogItem> productBacklogItemsToMove = new ArrayList<ProductBacklogItem>();
        final ProductBacklogItem sourceItem = iteratorToSourceItem.previous();
        productBacklogItemsToMove.add(sourceItem);
        iteratorToSourceItem.remove();
        for (int index = 1; index < numberOfItemsToMove; index++) {
            productBacklogItemsToMove.add(iteratorToSourceItem.next());
            iteratorToSourceItem.remove();
        }
        final ListIterator<ProductBacklogItem> iterator = iterateToReferenceId(productBacklogItems);
        for (final ProductBacklogItem productBacklogItem : productBacklogItemsToMove) {
            iterator.add(productBacklogItem);
        }
    }
}
