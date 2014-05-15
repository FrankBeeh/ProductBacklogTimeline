package de.frankbeeh.productbacklogtimeline.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class ControllerFactory {
    private static final String RELEASES_TABLE_FXML = "releasesTable.fxml";
    private static final String TIMELINE_TABLE_FXML = "timelineTable.fxml";

    public ReleaseTableController createReleaseTableController() throws IOException {
        return loadController(RELEASES_TABLE_FXML);
    }

    public TimelineTableController createTimelineTableController() throws IOException {
        return loadController(TIMELINE_TABLE_FXML);
    }

    private <T> T loadController(String fxmlFileName) throws IOException {
        final URL resource = getClass().getResource(fxmlFileName);
        final FXMLLoader loader = new FXMLLoader(resource);
        loader.load();
        return loader.<T> getController();
    }
}
