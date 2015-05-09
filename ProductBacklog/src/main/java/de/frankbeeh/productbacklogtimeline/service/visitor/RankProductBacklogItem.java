package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

/**
 * Responsibility:
 * <ul>
 * <li>Compute rank of a {@link ProductBacklogItem} in the {@link ProductBacklog}.
 * </ul>
 */
public class RankProductBacklogItem implements ProductBacklogItemVisitor {
    private int rank;

    public RankProductBacklogItem() {
        reset();
    }

    public void reset() {
        rank = 1;
    }

    public void visit(ProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast) {
        productBacklogItem.setProductBacklogRank(rank++);
    }
}
