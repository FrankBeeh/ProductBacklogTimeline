package de.frankbeeh.productbacklogtimeline.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductBacklogTimelineApplication extends Application {

    private static final String APPLICATION_TITLE = "Product Backlog Timeline";
    private static final String APPLICATION_FXML = "product_backlog_timeline.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(APPLICATION_FXML));
        final Parent root = (Parent) loader.load();

        final Scene scene = new Scene(root, 1024, 768);

        primaryStage.setTitle(APPLICATION_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
