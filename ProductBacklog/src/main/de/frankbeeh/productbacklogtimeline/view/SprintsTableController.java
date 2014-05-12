package de.frankbeeh.productbacklogtimeline.view;

import java.text.ParseException;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.Sprints;
import de.frankbeeh.productbacklogtimeline.service.FormatUtility;

public class SprintsTableController {
    private static final class ProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public ProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintData, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    private static final class AccumulatedProgressForecastPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<SprintData, Double>, ObservableValue<Double>> {
        private final String progressForecastName;

        public AccumulatedProgressForecastPropertyValueFactory(String progressForecastName) {
            this.progressForecastName = progressForecastName;
        }

        @Override
        public ObservableValue<Double> call(final CellDataFeatures<SprintData, Double> cellDataFeatures) {
            return new ObservableValueBase<Double>() {
                @Override
                public Double getValue() {
                    return cellDataFeatures.getValue().getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
                }
            };
        }
    }

    @FXML
    private TableView<SprintData> sprintsTable;
    @FXML
    private TableColumn<SprintData, String> startDateColumn;
    @FXML
    private TableColumn<SprintData, String> nameColumn;
    @FXML
    private TableColumn<SprintData, String> endDateColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByMinVelColumn;
    @FXML
    private TableColumn<SprintData, Double> forecastPerSprintByMaxVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByAvgVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByMinVelColumn;
    @FXML
    private TableColumn<SprintData, Double> accumulatedForecastByMaxVelColumn;

    private ObservableList<SprintData> model;

    @FXML
    private void initialize() {
        startDateColumn.setCellFactory(TextFieldTableCell.<SprintData> forTableColumn());
        startDateColumn.setCellValueFactory(cellDataFeatures -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return FormatUtility.formatDate(cellDataFeatures.getValue().getStartDate());
            }
        });
        startDateColumn.setOnEditCommit(newValue -> {
            try {
                newValue.getTableView().getItems().get(newValue.getTablePosition().getRow()).setStartDate(FormatUtility.parseDate(newValue.getNewValue()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        endDateColumn.setCellFactory(TextFieldTableCell.<SprintData> forTableColumn());
        endDateColumn.setOnEditCommit(newValue -> {
            try {
                newValue.getTableView().getItems().get(newValue.getTablePosition().getRow()).setStartDate(FormatUtility.parseDate(newValue.getNewValue()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        endDateColumn.setCellValueFactory(cellDataFeatures -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return FormatUtility.formatDate(cellDataFeatures.getValue().getEndDate());
            }
        });
        nameColumn.setCellFactory(TextFieldTableCell.<SprintData> forTableColumn());
        setCellValueFactoryForForecast(forecastPerSprintByAvgVelColumn, Sprints.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMinVelColumn, Sprints.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForForecast(forecastPerSprintByMaxVelColumn, Sprints.MAXIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByAvgVelColumn, Sprints.AVERAGE_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMinVelColumn, Sprints.MINIMUM_VELOCITY_FORECAST);
        setCellValueFactoryForAccumulatedForecast(accumulatedForecastByMaxVelColumn, Sprints.MAXIMUM_VELOCITY_FORECAST);
    }

    public void initModel(Sprints sprints) {
        createModel(sprints);
        this.sprintsTable.setItems(model);
    }

    private void createModel(Sprints sprints) {
        model = FXCollections.<SprintData> observableArrayList();
        model.addAll(sprints.getSprints());
    }

    private void setCellValueFactoryForForecast(TableColumn<SprintData, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new ProgressForecastPropertyValueFactory(historyForecastName));
    }

    private void setCellValueFactoryForAccumulatedForecast(TableColumn<SprintData, Double> tableColumn, String historyForecastName) {
        tableColumn.setCellValueFactory(new AccumulatedProgressForecastPropertyValueFactory(historyForecastName));
    }
}
