package de.frankbeeh.productbacklogtimeline.domain.change;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

public abstract class ProductBacklogChange {
    private final String referenceId;

    public ProductBacklogChange(String referenceId) {
        this.referenceId = referenceId;
    }

    public abstract void applyTo(List<ProductBacklogItem> productBacklogItems);

    /**
     * @return An iterator where <code>iterator.previous().getId() = {@link ProductBacklogChange#referenceId}</code>.
     */
    protected ListIterator<ProductBacklogItem> iterateToReferenceId(List<ProductBacklogItem> productBacklogItems) {
        return iterateToId(referenceId, productBacklogItems);
    }

    public static ListIterator<ProductBacklogItem> iterateToId(String idToFind, List<ProductBacklogItem> productBacklogItems) {
        final ListIterator<ProductBacklogItem> iterator = productBacklogItems.listIterator();
        if (idToFind == null) {
            return iterator;
        }
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(idToFind)) {
                return iterator;
            }
        }
        throw new IllegalArgumentException("Id '" + idToFind + "' not found!");
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
