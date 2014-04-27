package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.data.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.data.Sprints;

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
    private TableColumn<ProductBacklogItem, String> idColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> titleColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> descriptionColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> estimateColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> stateColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> accumulatedEstimateColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> completionForecastByMinVelColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> completionForecastByAvgVelColumn;
    @FXML
    private TableColumn<ProductBacklogItem, String> completionForecastByMaxVelColumn;

    private ObservableList<ProductBacklogItem> model;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("id"));
        estimateColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("estimate"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("description"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("state"));
        accumulatedEstimateColumn.setCellValueFactory(new PropertyValueFactory<ProductBacklogItem, String>("accumulatedEstimate"));
        completionForecastByAvgVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(Sprints.AVERAGE_VELOCITY_FORECAST));
        completionForecastByMinVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(Sprints.MINIMUM_VELOCITY_FORECAST));
        completionForecastByMaxVelColumn.setCellValueFactory(new CompletionForecastPropertyValueFactory(Sprints.MAXIMUM_VELOCITY_FORECAST));
    }

    public void initModel(ProductBacklog productBacklog) {
        createModel(productBacklog);
        this.productBacklogTable.setItems(model);
    }

    private void createModel(ProductBacklog productBacklog) {
        model = FXCollections.<ProductBacklogItem> observableArrayList();
        model.addAll(productBacklog.getItems());
    }
}
