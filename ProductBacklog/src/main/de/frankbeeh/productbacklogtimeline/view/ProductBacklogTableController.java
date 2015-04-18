package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparison;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogComparisonItem;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

public class ProductBacklogTableController {
    private static final class CompletionForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<ProductBacklogComparisonItem, String>, ObservableValue<String>> {
        private final String progressForecastName;

        public CompletionForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<String> call(CellDataFeatures<ProductBacklogComparisonItem, String> cellDataFeatures) {
            return new SimpleStringProperty(cellDataFeatures.getValue().getCompletionForecastDescription(progressForecastName));
        }
    }

    @FXML
    private TableView<ProductBacklogComparisonItem> productBacklogTable;
    @FXML
    private TableColumn<ProductBacklogComparisonItem, String> completionForecastByMinVelColumn;
    @FXML
    private TableColumn<ProductBacklogComparisonItem, String> completionForecastByAvgVelColumn;
    @FXML
    private TableColumn<ProductBacklogComparisonItem, String> completionForecastByMaxVelColumn;

    private final ObservableList<ProductBacklogComparisonItem> model = FXCollections.<ProductBacklogComparisonItem> observableArrayList();

    @FXML
    private void initialize() {
        completionForecastByAvgVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        completionForecastByMinVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        completionForecastByMaxVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
        productBacklogTable.setItems(model);
    }

    public void initModel(ProductBacklogComparison productBacklogComparison) {
        model.removeAll(model);
        model.addAll(productBacklogComparison.getItems());
    }

    public void updateView() {
        productBacklogTable.getColumns().get(0).setVisible(false);
        productBacklogTable.getColumns().get(0).setVisible(true);
    }
}
