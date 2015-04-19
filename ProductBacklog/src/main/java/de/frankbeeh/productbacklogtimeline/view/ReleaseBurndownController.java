package de.frankbeeh.productbacklogtimeline.view;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import de.frankbeeh.productbacklogtimeline.data.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.data.SprintData;
import de.frankbeeh.productbacklogtimeline.data.VelocityForecast;

public class ReleaseBurndownController {
    @FXML
    private BarChart<String, Double> releaseBurndown;

    @FXML
    private void initialize() {

    }

    public void initModel(ReleaseForecast releaseForecast) {
        releaseBurndown.getData().clear();
        releaseBurndown.getData().add(createAccumulatedEffortDoneSeries(releaseForecast));
        releaseBurndown.getData().add(createForecastSeries(releaseForecast));
        final Series<String, Double> totalEffortSeries = new XYChart.Series<String, Double>();
        totalEffortSeries.getData().add(new XYChart.Data<String, Double>("TODO",releaseForecast.getProductBacklog().getTotalEffort()));
        releaseBurndown.getData().add(totalEffortSeries);
    }

    private Series<String, Double> createForecastSeries(ReleaseForecast releaseForecast) {
        final Series<String, Double> forecastSeries = new XYChart.Series<String, Double>();
        forecastSeries.setName("Forecast");
        for (SprintData sprintData : releaseForecast.getVelocityForecast().getSprints()) {
            final Double forecast = sprintData.getAccumulatedProgressForecastBasedOnHistory(VelocityForecast.AVERAGE_VELOCITY_FORECAST);
            if (forecast != null) {
                forecastSeries.getData().add(new XYChart.Data<String, Double>(sprintData.getName(), forecast));
            }
        }
        return forecastSeries;
    }

    private Series<String, Double> createAccumulatedEffortDoneSeries(ReleaseForecast releaseForecast) {
        final Series<String, Double> accumulatedEffortSeries = new XYChart.Series<String, Double>();
        accumulatedEffortSeries.setName("Effort Done");
        for (SprintData sprintData : releaseForecast.getVelocityForecast().getSprints()) {
            final Double accumulatedEffort = sprintData.getAccumulatedEffortDone();
            if (accumulatedEffort != null) {
                accumulatedEffortSeries.getData().add(new XYChart.Data<String, Double>(sprintData.getName(), accumulatedEffort));
            }
        }
        return accumulatedEffortSeries;
    }
}
