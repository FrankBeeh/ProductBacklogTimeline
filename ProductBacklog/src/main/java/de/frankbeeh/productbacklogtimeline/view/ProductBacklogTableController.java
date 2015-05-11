package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.domain.ComparedValue;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogComparison;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

public class ProductBacklogTableController {
    private static final class CompletionForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<ProductBacklogItemComparison, ComparedValue>, ObservableValue<ComparedValue>> {
        private final String progressForecastName;

        public CompletionForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<ComparedValue> call(final CellDataFeatures<ProductBacklogItemComparison, ComparedValue> cellDataFeatures) {
            return new ObservableValueBase<ComparedValue>() {
                @Override
                public ComparedValue getValue() {
                    return cellDataFeatures.getValue().getComparedCompletionForecast(progressForecastName);
                }
            };
        }
    }

    @FXML
    private TableView<ProductBacklogItemComparison> productBacklogTable;
    @FXML
    private TableColumn<ProductBacklogItemComparison, ComparedValue> completionForecastByMinVelColumn;
    @FXML
    private TableColumn<ProductBacklogItemComparison, ComparedValue> completionForecastByAvgVelColumn;
    @FXML
    private TableColumn<ProductBacklogItemComparison, ComparedValue> completionForecastByMaxVelColumn;

    private final ObservableList<ProductBacklogItemComparison> model = FXCollections.<ProductBacklogItemComparison> observableArrayList();

    @FXML
    private void initialize() {
        completionForecastByAvgVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        completionForecastByMinVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        completionForecastByMaxVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
        productBacklogTable.setItems(model);
    }

    public void initModel(ProductBacklogComparison productBacklogComparison) {
        model.removeAll(model);
        model.addAll(productBacklogComparison.getComparisons());
    }
}
