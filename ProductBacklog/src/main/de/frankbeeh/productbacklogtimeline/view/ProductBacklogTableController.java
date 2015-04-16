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
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

public class ProductBacklogTableController {
    private static final class CompletionForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<ProductBacklogItem, String>, ObservableValue<String>> {
        private final String progressForecastName;

        public CompletionForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<String> call(CellDataFeatures<ProductBacklogItem, String> cellDataFeatures) {
            return new SimpleStringProperty(cellDataFeatures.getValue().getCompletionForecast(progressForecastName));
        }
    }

    @FXML
    private TableView<ProductBacklogItem> productBacklogTable;
    @FXML
    private TableColumn<ProductBacklogItem, String> completionForecastByMinVelColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> completionForecastByAvgVelColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> completionForecastByMaxVelColumn;

    private final ObservableList<ProductBacklogItem> model = FXCollections.<ProductBacklogItem> observableArrayList();

    @FXML
    private void initialize() {
        completionForecastByAvgVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.AVERAGE_VELOCITY_FORECAST));
        completionForecastByMinVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MINIMUM_VELOCITY_FORECAST));
        completionForecastByMaxVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(VelocityForecast.MAXIMUM_VELOCITY_FORECAST));
        productBacklogTable.setItems(model);
    }

    public void initModel(ProductBacklog productBacklog) {
        model.removeAll(model);
        model.addAll(productBacklog.getItems());
    }

    public void updateView() {
        productBacklogTable.getColumns().get(0).setVisible(false);
        productBacklogTable.getColumns().get(0).setVisible(true);
    }
}
