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
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByAverageVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMaximumVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByMinimumVelocity;

public class SprintsTableController {
    private static final AlignmentCellFactory<Double> ALIGN_DOUBLE_RIGHT_CELL_FACTORY = new AlignmentCellFactory<Double>(Pos.CENTER_RIGHT);
    private static final AlignmentCellFactory<String> ALIGN_FORECAST_RIGHT_CELL_FACTORY = new AlignmentCellFactory<String>(Pos.CENTER_RIGHT);
    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.0");

    private static final class AlignmentCellFactory<T> implements Callback<TableColumn<SprintData, T>, TableCell<SprintData, T>> {
        private final Pos alignment;

        public AlignmentCellFactory(Pos alignment) {
            this.alignment = alignment;
        }

        @Override
        public TableCell<SprintData, T> call(TableColumn<SprintData, T> arg0) {
            final TableCell<SprintData, T> tableCell = new TableCell<SprintData, T>() {
                @Override
                public void updateItem(T item, boolean empty) {
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

    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, String>, ObservableValue<String>> {
        private final String historyForecastName;

        public ProgressForecastPropertyValueFactory(String historyForecastName) {
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
    private TableColumn<SprintData, Double> capacityForecastColumn;
    @FXML
    private TableColumn<SprintData, Double> effortForecastColumn;
    @FXML
    private TableColumn<SprintData, Double> capacityDoneColumn;
    @FXML
    private TableColumn<SprintData, Double> effortDoneColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedEffortDoneColumn;
    @FXML
    private TableColumn<SprintData, String> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, String> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintData, String> forecastPerSprintByMaxVelColumn;

    private ObservableList<SprintData> model;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<SprintData, String>("endDate"));
        setCellAndCellValueFactory(capacityForecastColumn, "capacityForecast");
        setCellAndCellValueFactory(effortForecastColumn, "effortForecast");
        setCellAndCellValueFactory(capacityDoneColumn, "capacityDone");
        setCellAndCellValueFactory(effortDoneColumn, "effortDone");
        setCellAndCellValueFactory(accumulatedEffortDoneColumn, "accumulatedEffortDone");
        setCellAndCellValueFactoryForForecast(forecastPerSprintByAvgVelColumn, ComputeProgressForecastByAverageVelocity.HISTORY_FORECAST_NAME);
        setCellAndCellValueFactoryForForecast(forecastPerSprintByMinVelColumn, ComputeProgressForecastByMinimumVelocity.HISTORY_FORECAST_NAME);
        setCellAndCellValueFactoryForForecast(forecastPerSprintByMaxVelColumn, ComputeProgressForecastByMaximumVelocity.HISTORY_FORECAST_NAME);
    }

    public void setCellAndCellValueFactoryForForecast(TableColumn<SprintData, String> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
        tableColumn.setCellFactory(ALIGN_FORECAST_RIGHT_CELL_FACTORY);
    }

    public void setCellAndCellValueFactory(TableColumn<SprintData, Double> tableColumn, String propertyName) {
        tableColumn.setCellValueFactory(new PropertyValueFactory<SprintData, Double>(propertyName));
        tableColumn.setCellFactory(ALIGN_DOUBLE_RIGHT_CELL_FACTORY);
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
