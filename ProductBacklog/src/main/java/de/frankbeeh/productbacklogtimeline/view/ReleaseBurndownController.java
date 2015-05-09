package de.frankbeeh.productbacklogtimeline.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

public class ReleaseBurndownController {
    private static final String PRODUCT_BACKLOG = "Product Backlog";
    private static final String FORECAST = "Forecast ";
    @FXML
    private StackedBarChart<String, Double> releaseBurndown;

    @FXML
    private void initialize() {
    }

    public void initModel(ProductTimestamp productTimestamp) {
        releaseBurndown.getData().removeAll(releaseBurndown.getData());
        addProductBacklogItems(productTimestamp);
        addVelocityForecasts(productTimestamp);
    }

    private void addVelocityForecasts(ProductTimestamp productTimestamp) {
        final Series<String, Double> series = new XYChart.Series<String, Double>();
        final VelocityForecast velocityForecast = productTimestamp.getVelocityForecast();
        for (Sprint sprintData : velocityForecast.getSprints()) {
            for (String forecastType : VelocityForecast.COMPLETION_FORECASTS) {
                Double value = sprintData.getEffortDone();
                if (value == null) {
                    value = sprintData.getProgressForecastBasedOnHistory(forecastType);
                    if (value == null) {
                        value = 0d;
                    }
                }
                final Data<String, Double> data = new XYChart.Data<String, Double>(getForecastName(forecastType), value);
                series.getData().add(data);
            }
        }
        releaseBurndown.getData().add(series);
        addTooltipsForVelocityForecasts(velocityForecast);
    }

    private void addProductBacklogItems(ProductTimestamp productTimestamp) {
        final Series<String, Double> series = new XYChart.Series<String, Double>();
        final ProductBacklog productBacklog = productTimestamp.getProductBacklog();
        for (ProductBacklogItem item : productBacklog.getItems()) {
            final Data<String, Double> data = new XYChart.Data<String, Double>(PRODUCT_BACKLOG, item.getCleanedEstimate());
            series.getData().add(data);
        }
        releaseBurndown.getData().add(series);
        addTooltipsForProductBacklogItems(productBacklog);
    }

    private void addTooltipsForProductBacklogItems(ProductBacklog productBacklog) {
        final List<ProductBacklogItem> items = productBacklog.getItems();
        int index = 0;
        for (Series<String, Double> series : releaseBurndown.getData()) {
            for (Data<String, Double> data : series.getData()) {
                if (PRODUCT_BACKLOG.equals(data.getXValue())) {
                    final ProductBacklogItem productBacklogItem = items.get(index++);
                    final Node node = data.getNode();
                    final State state = productBacklogItem.getState();
                    node.getStyleClass().add(state.toString());
                    Tooltip.install(node, new Tooltip(productBacklogItem.getId() + ": " + productBacklogItem.getTitle() + " - " + state + " - " + productBacklogItem.getAccumulatedEstimate()));
                }
            }
        }
    }

    private void addTooltipsForVelocityForecasts(VelocityForecast velocityForecast) {
        final List<Sprint> sprints = velocityForecast.getSprints();
        for (String forecastType : VelocityForecast.COMPLETION_FORECASTS) {
            int index = 0;
            for (Series<String, Double> series : releaseBurndown.getData()) {
                for (Data<String, Double> data : series.getData()) {
                    if (data.getXValue().startsWith(getForecastName(forecastType))) {
                        final Sprint sprintData = sprints.get(index++);
                        final State state = sprintData.getState();
                        final Node node = data.getNode();
                        node.getStyleClass().add(state.toString());
                        Tooltip.install(node, new Tooltip(sprintData.getName() + " - " + state + " - " + sprintData.getAccumulatedEffortDoneOrProgressForcast(forecastType)));
                    }
                }
            }
        }
    }

    private String getForecastName(String forecastType) {
        return FORECAST + forecastType;
    }

}
