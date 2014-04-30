package de.frankbeeh.productbacklogtimeline.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class ControllerFactory {
    private static final String RELEASES_TABLE_FXML = "releasesTable.fxml";

    public ReleaseTableController createReleaseTableController() throws IOException {
        final URL resource = getClass().getResource(RELEASES_TABLE_FXML);
        final FXMLLoader loader = new FXMLLoader(resource);
        loader.load();
        return loader.<ReleaseTableController> getController();
    }
}
