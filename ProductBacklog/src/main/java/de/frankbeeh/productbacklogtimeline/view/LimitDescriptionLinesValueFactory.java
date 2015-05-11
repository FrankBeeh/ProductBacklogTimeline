package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.ComparedValue;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;

/**
 * Responsibility:
 * <ul>
 * <li>Limits the number of lines shown for the description to {@link LimitDescriptionLinesValueFactory#MAX_LINE_COUNT}.
 * </ul>
 */
public class LimitDescriptionLinesValueFactory implements Callback<CellDataFeatures<ProductBacklogItemComparison, ComparedValue>, ObservableValue<ComparedValue>> {
    private static final int MAX_LINE_COUNT = 2;

    private final class ObservableValueBaseExtension extends ObservableValueBase<ComparedValue> {
        private final ComparedValue comparedValue;

        private ObservableValueBaseExtension(ComparedValue comparedValue) {
            this.comparedValue = comparedValue;
        }

        @Override
        public ComparedValue getValue() {
            return comparedValue;
        }
    }


    @Override
    public ObservableValue<ComparedValue> call(CellDataFeatures<ProductBacklogItemComparison, ComparedValue> cellDataFeatures) {
        final ComparedValue comparedValue = cellDataFeatures.getValue().getComparedDescription();
        int fromIndex = 0;
        String resultingValue = comparedValue.getComparedValue();
        for (int count = 0; count < MAX_LINE_COUNT; count++) {
            fromIndex = resultingValue.indexOf("\n", fromIndex + 1);
            if (fromIndex == -1) {
                break;
            }
        }
        if (fromIndex != -1) {
            resultingValue = resultingValue.substring(0, fromIndex) + "\n...";
        }
        return new ObservableValueBaseExtension(new ComparedValue(comparedValue.getDirection(), resultingValue));
    }
}
