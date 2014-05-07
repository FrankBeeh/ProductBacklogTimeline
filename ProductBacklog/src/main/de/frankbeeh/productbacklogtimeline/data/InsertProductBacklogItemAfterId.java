package de.frankbeeh.productbacklogtimeline.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InsertProductBacklogItemAfterId extends ProductBacklogChange {
    private final ProductBacklogItem productBacklogItem;

    public InsertProductBacklogItemAfterId(String id, ProductBacklogItem productBacklogItem) {
        super(id);
        this.productBacklogItem = productBacklogItem;
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        iterateToId(productBacklogItems).add(productBacklogItem);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
