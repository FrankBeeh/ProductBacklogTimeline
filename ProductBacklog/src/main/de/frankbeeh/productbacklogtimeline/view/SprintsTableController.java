package de.frankbeeh.productbacklogtimeline.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeEffortForecastByMinimumVelocity;

public class SprintsTableController {
    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.0");

    private final class AlignmentCellFactory implements Callback<TableColumn<SprintData, String>, TableCell<SprintData, String>> {
        private final Pos alignment;

        public AlignmentCellFactory(Pos alignment) {
            this.alignment = alignment;
        }

        @Override
        public TableCell<SprintData, String> call(TableColumn<SprintData, String> arg0) {
            final TableCell<SprintData, String> tableCell = new TableCell<SprintData, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : getString());
                    setGraphic(null);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };

            tableCell.setAlignment(alignment);
            return tableCell;
        }
    }

    private static final class EfforForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, String>, ObservableValue<String>> {
        private final String historyForecastName;

        public EfforForecastPropertyValueFactory(String historyForecastName) {
            this.historyForecastName = historyForecastName;
        }

        @Override
        public ObservableValue<String> call(CellDataFeatures<SprintData, String> cellDataFeatures) {
            final Double effortForecastBasedOnHistory = cellDataFeatures.getValue().getEffortForecastBasedOnHistory(historyForecastName);
            return new SimpleStringProperty(NUMBER_FORMAT.format(effortForecastBasedOnHistory.doubleValue()));
        }
    }

    @FXML
    private TableView<SprintData> sprintsTable;
    @FXML
    private TableColumn<SprintData, String> nameColumn;
    @FXML
    private TableColumn<SprintData, String> startDateColumn;
    @FXML
    private TableColumn<SprintData, String> endDateColumn;
    @FXML
    private TableColumn<SprintData, String> capacityForecastColumn;
    @FXML
    private TableColumn<SprintData, String> effortForecastColumn;
    @FXML
    private TableColumn<SprintData, String> capacityDoneColumn;
    @FXML
    private TableColumn<SprintData, String> effortDoneColumn;
    @FXML
    private TableColumn<SprintData, String> forecastByAverageVelocityColumn;
    @FXML
    private TableColumn<SprintData, String> forecastByMinimumVelocityColumn;
    @FXML
    private TableColumn<SprintData, String> forecastByMaximumVelocityColumn;

    private ObservableList<SprintData> model;

    @FXML
    private void initialize() {
        final AlignmentCellFactory alignmentCellFactory = new AlignmentCellFactory(Pos.CENTER_RIGHT);
        nameColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("endDate"));
        capacityForecastColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("capacityForecast"));
        effortForecastColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("effortForecast"));
        capacityDoneColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("capacityDone"));
        effortDoneColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("effortDone"));
        forecastByAverageVelocityColumn.setCellValueFactory(new EfforForecastPropertyValueFactory(ComputeEffortForecastByAverageVelocity.HISTORY_FORECAST_NAME));
        forecastByAverageVelocityColumn.setCellFactory(alignmentCellFactory);
        forecastByMinimumVelocityColumn.setCellValueFactory(new EfforForecastPropertyValueFactory(ComputeEffortForecastByMinimumVelocity.HISTORY_FORECAST_NAME));
        forecastByMinimumVelocityColumn.setCellFactory(alignmentCellFactory);
        forecastByMaximumVelocityColumn.setCellValueFactory(new EfforForecastPropertyValueFactory(ComputeEffortForecastByMaximumVelocity.HISTORY_FORECAST_NAME));
        forecastByMaximumVelocityColumn.setCellFactory(alignmentCellFactory);
    }

    public void initModel(Sprints sprints) {
        createModel(sprints);
        this.sprintsTable.setItems(model);
    }

    private void createModel(Sprints sprints) {
        model = FXCollections.<SprintData> observableArrayList();
        model.addAll(sprints.getSprints());
    }

}
