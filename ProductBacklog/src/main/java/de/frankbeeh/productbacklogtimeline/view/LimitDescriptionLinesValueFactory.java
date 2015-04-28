package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogComparisonItem;

/**
 * Responsibility:
 * <ul>
 * <li>Limits the number of lines shown for the description to {@link LimitDescriptionLinesValueFactory#MAX_LINE_COUNT}.
 * </ul>
 */
public class LimitDescriptionLinesValueFactory implements Callback<CellDataFeatures<ProductBacklogComparisonItem, String>, ObservableValue<String>> {

    private static final int MAX_LINE_COUNT = 2;

    @Override
    public ObservableValue<String> call(CellDataFeatures<ProductBacklogComparisonItem, String> cellDataFeatures) {
        final String description = cellDataFeatures.getValue().getComparedDescription();
        int fromIndex = 0;
        for (int count = 0; count < MAX_LINE_COUNT; count++) {
            fromIndex = description.indexOf("\n", fromIndex + 1);
            if (fromIndex == -1) {
                break;
            }
        }
        if (fromIndex == -1) {
            return new SimpleStringProperty(description);
        } else {
            return new SimpleStringProperty(description.substring(0, fromIndex) + "\n...");
        }
    }
}
