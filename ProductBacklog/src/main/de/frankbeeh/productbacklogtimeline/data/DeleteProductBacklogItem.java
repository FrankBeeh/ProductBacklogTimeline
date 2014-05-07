package de.frankbeeh.productbacklogtimeline.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DeleteProductBacklogItem extends ProductBacklogChange {
    public DeleteProductBacklogItem(String id) {
        super(id);
    }

    @Override
    public void applyTo(List<ProductBacklogItem> productBacklogItems) {
        iterateToId(productBacklogItems).remove();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
